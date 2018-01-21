package Data;

import java.io.Serializable;

/**
 * Created by Ink on 21.01.2018.
 */
//tooo lazy to work with Java.utils DATE

public class MyDate implements Serializable {
    public int day, month, year;

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return  + this.year + "/ " + this.month + "/" + this.day;
    }
}