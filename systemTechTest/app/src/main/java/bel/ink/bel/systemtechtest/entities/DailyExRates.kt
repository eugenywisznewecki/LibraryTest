package bel.ink.bel.systemtechtest.entities

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "DailyExRates")
data class DailyExRates @JvmOverloads constructor(

    @field:Attribute(name = "Date", required = false)
    @param:Attribute(name = "Date", required = false)
    val date: String = "",

    @field:ElementList(name = "Currency ", required = false, inline = true)
    @param:ElementList(name = "Currency ", required = false, inline = true)
    val currencies: List<Currency>? = null
)