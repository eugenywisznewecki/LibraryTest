package Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ink on 21.01.2018.
 */

public class Pupil implements Serializable, Comparable<Pupil> {
    private String name;
    private MyDate dateOfBirth;

    private Integer numOfBooksGet;
    private Integer numOfBooksReturned;

    public Pupil(String name, MyDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.numOfBooksGet = 0;
        this.numOfBooksReturned = 0;

    }

    public Pupil(String name, MyDate dateOfBirth, Integer numOfBooks, Integer numOfBooksReturned) {
        this.name = name;
        this.numOfBooksGet = numOfBooks;
        this.dateOfBirth = dateOfBirth;
        this.numOfBooksReturned = numOfBooksReturned;
    }

    public String geName() {
        return name;
    }

    public MyDate getDate() {

        return dateOfBirth;
    }



    public Integer getNumOfBooksGet() {
        return numOfBooksGet;
    }

    public void setNumOfBooksGet(Integer numOfBooksGet) {
        this.numOfBooksGet = numOfBooksGet;
    }

    public Integer getNumOfBooksReturned() {
        return numOfBooksReturned;
    }

    public void setNumOfBooksReturned(Integer numOfBooksReturned) {
        this.numOfBooksReturned = numOfBooksReturned;
    }

    public Integer getCountReadBooks(){
        return this.numOfBooksReturned;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pupil that = (Pupil) o;

        if (name != that.name) return false;
        return dateOfBirth != null ? dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Pupil o) {
        if (o.numOfBooksReturned > numOfBooksReturned)
                return 1;
        if (o.numOfBooksReturned < numOfBooksReturned)
            return -1;

        return 0;
    }
}
