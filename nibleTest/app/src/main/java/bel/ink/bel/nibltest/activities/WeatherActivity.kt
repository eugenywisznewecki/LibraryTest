package bel.ink.bel.nibltest.activities

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import bel.ink.bel.nibltest.R
import bel.ink.bel.nibltest.adapters.WeatherListAdapter
import bel.ink.bel.nibltest.utils.NetChecker
import bel.ink.bel.nibltest.utils.REQUEST_FINE_LOC
import bel.ink.bel.nibltest.viewmodels.WeatherViewModel
import bel.ink.bel.nibltest.weather.ForecastList
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import kotlinx.android.synthetic.main.activity_weather.*
import org.jetbrains.anko.toast
import timber.log.Timber
import java.io.IOException
import java.util.*

class WeatherActivity : AppCompatActivity() {

	private lateinit var viewModel: WeatherViewModel
	private lateinit var currentLocation: Location

	private val fusedLocationClient: FusedLocationProviderClient by lazy {
		LocationServices.getFusedLocationProviderClient(applicationContext)
	}
	private val locationRequest: LocationRequest by lazy {
		LocationRequest()
				.setInterval(30000L)
				.setFastestInterval(5000L)
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
	}
	private val locationCallback: LocationCallback by lazy {
		object : LocationCallback() {
			override fun onLocationResult(locationResult: LocationResult) {
				for (location in locationResult.locations) {
					updateLocation(location)
					Log.d("TAG", "onLocationResult(")
				}
			}
		}
	}
	private val locationManager by lazy { this.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
	private val locationListener by lazy {
		object : android.location.LocationListener {
			override fun onLocationChanged(location: Location) {
				updateLocation(location)
				Log.d("TAG", "onLocationResult(")
			}

			override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
				Log.d("TAG", "onStatusChanged(")
			}

			override fun onProviderEnabled(provider: String?) {
				Log.d("TAG", "onProviderEnabled(")
			}

			override fun onProviderDisabled(provider: String?) {
				Log.d("TAG", "onProviderDisabled(")
			}
		}
	}
	private val tabCallback: ActionBar.TabListener by lazy {
		object : ActionBar.TabListener {
			override fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction?) {
			}

			override fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction?) {
			}

			override fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction?) {

				when (tab.position) {
					0 -> {

					}
					1 -> {
						viewModel.route(this@WeatherActivity)
						finish()
					}
				}
			}
		}
	}
	private var isRequestingLocationUpdates: Boolean = true


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_weather)
		listStartActivity1.layoutManager = LinearLayoutManager(this)
		viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
		val forecastList = viewModel.getLive()
		forecastList?.observe(this, Observer { forecastList ->
			onForecastsLoaded(forecastList)
		})


		if (!NetChecker(applicationContext).checInternet()) {
			toast("No internet")
		}

		getLastKnowsLocation()
		checkLocationSystemDeviseSettings()
		if (isRequestingLocationUpdates) {
			fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
		}
		setUpBar()
	}

	override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
		outState?.putBoolean("isUpdate", isRequestingLocationUpdates)
		super.onSaveInstanceState(outState, outPersistentState)
	}

	override fun onStop() {
		super.onStop()
		fusedLocationClient.removeLocationUpdates(locationCallback)
		locationManager.removeUpdates(locationListener)
	}

	override fun onResume() {
		super.onResume()
		requestPermissions()
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		when (requestCode) {
			REQUEST_FINE_LOC -> {
				if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Timber.d("permission ok")
					getLastKnowsLocation()
					setUpLocationManager()
				} else {
					Timber.d("permission not ok")
					toast("GPS PERMISSION REQUIRED!")
					alert()
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private fun setUpBar() {
		val bar = supportActionBar
		bar?.let {
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS)
			bar.addTab(bar.newTab().setText("Forecast").setTabListener(tabCallback))
			bar.addTab(bar.newTab().setText("History").setTabListener(tabCallback))
			bar.selectTab(bar.getTabAt(0))
			bar.setDisplayShowTitleEnabled(false)
			bar.setDisplayShowHomeEnabled(false)

		}
	}

	private fun geocodeLocationToAdress(location: Location): Map<String, String>? {
		val geocoder = Geocoder(this, Locale.getDefault())
		var addresses: List<Address> = emptyList()
		try {
			addresses = geocoder.getFromLocation(
					location.latitude,
					location.longitude,
					1)
		} catch (ioException: IOException) {
			Timber.d(ioException.toString())
		} catch (illegalArgumentException: IllegalArgumentException) {
			Timber.d(" Latitude = $location.latitude , Logitude =  $location.longitude $illegalArgumentException")
		}
		if (addresses.isEmpty()) {
			Timber.e("adress is emty")
		} else {
			val address = addresses[0]
			addressView.text = "${address.countryName} ${address.locality} ${address.thoroughfare}  ${address.featureName}"

			val mapAdress = mapOf("country" to address.countryName,
					"locatity" to address.locality,
					"street" to address.thoroughfare,
					"house" to address.featureName
			)
			return mapAdress
		}
		return null
	}

	private fun updateLocation(location: Location) {
		val address = geocodeLocationToAdress(location)
		progressRegisterWeather.visibility = View.INVISIBLE
		address?.let {
			viewModel.setCoordinatesDay(5, lat = location.latitude, lonq = location.longitude, mapAddress = address)
		}
		coordinatesTextView.text = "${resources.getString(R.string.weatherByGPS)} ${location.latitude.toString()}- ${location.longitude.toString()} "
		currentLocation = location
	}

	private fun onForecastsLoaded(forecastList: ForecastList?) {
		if (forecastList != null) {
			listStartActivity1.adapter = WeatherListAdapter(forecastList)
			listStartActivity1.adapter.notifyDataSetChanged()
			listStartActivity1.visibility = View.VISIBLE
		} else {
			listStartActivity1.visibility = View.GONE
		}
	}

	private fun checkLocationSystemDeviseSettings() {

		val locationSettingsBuilder = LocationSettingsRequest.Builder()
				.addLocationRequest(locationRequest)
				.build()

		val client = LocationServices.getSettingsClient(this)
		val taskLocationResponse = client.checkLocationSettings(locationSettingsBuilder)

		taskLocationResponse.addOnSuccessListener {
			coordinatesTextView.text = "Wait, looking for location..."
			progressRegisterWeather.visibility = View.VISIBLE
		}

		taskLocationResponse.addOnFailureListener(this, object : OnFailureListener {
			override fun onFailure(exc: Exception) {
				when ((exc as ApiException).statusCode) {
					CommonStatusCodes.RESOLUTION_REQUIRED -> {
						val resolvableApiException = exc as ResolvableApiException
						toast("resolution requeired!")
						resolvableApiException.startResolutionForResult(this@WeatherActivity, 0)
						coordinatesTextView.text = "enable GPS is needed"
					}

					LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
						toast("ERROR - SETTINGS_CHANGE_UNAVAILABLE")
					}

					LocationSettingsStatusCodes.SUCCESS -> {
						setUpLocationManager()

					}
				}
			}
		})
	}

	private fun getLastKnowsLocation() {
		fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
			location?.let {
				currentLocation = location
				updateLocation(location)
			}
		}
	}

	private fun setUpLocationManager() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10F, locationListener)
	}


	private fun requestPermissions() {
		if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
				== PackageManager.PERMISSION_GRANTED) {
			toast("gps ok")
			getLastKnowsLocation()
			setUpLocationManager()

		} else {
			ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION
			), REQUEST_FINE_LOC)
		}
	}

	private fun alert() {
		val listener = DialogInterface.OnClickListener { dialog, id -> finish() }

		val builder = AlertDialog.Builder(this)
		builder.setTitle("Multitracker sample")
				.setMessage("NO PERMISSION!")
				.setPositiveButton("OK", listener)
				.show()
	}
}





