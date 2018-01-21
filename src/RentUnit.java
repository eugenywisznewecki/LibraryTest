/*
import Data.LibraryObject;
import Data.Pupil;

import java.io.Serializable;


public class RentUnit implements Serializable {
    private LibraryObject[] units;                  //взятые объекты
    private int count;                              //cчетчик взятых товаров

    public RentUnit() {
        this.units = new LibraryObject[3];
        count = 0;
    }


    public boolean rent(LibraryObject libraryObject, Library library, Pupil pupil) {
        if (count < 3) {
            if (library.rent(libraryObject, pupil)) {
                units[count] = libraryObject;
                count++;
                return true;
            } else return false;
        } else return false;
    }
}
*/
