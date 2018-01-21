import Data.Category;
import Data.LibraryObject;
import Data.MyDate;
import Data.Pupil;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Ink on 21.01.2018.
 */

public class Main {
    public static void main(String[] args) {
        Library library = Library.getWholeLibrary();

        try {
            Library.setWholeLibrary(Operator.readWholeLibraryFromFile());
            System.out.println("File was read");
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("file not read");

        }

        // SIMPLY MANUAL WORKING
        //books
        LibraryObject object1 = new LibraryObject(Category.JOURNAL, "PlayBOY");
        LibraryObject object2 = new LibraryObject(Category.BOOK, "Bible");
        LibraryObject object3 = new LibraryObject(Category.NEWSPAPER, "WashingtonPost");
        LibraryObject object4 = new LibraryObject(Category.BOOK, "C#");
        LibraryObject object5 = new LibraryObject(Category.BOOK, "Swift");
        LibraryObject object6 = new LibraryObject(Category.BOOK, "Kotlin in action");
        LibraryObject object7 = new LibraryObject(Category.BOOK, "Leiva Android Kotlin");

        // test manually - initial LIBRARY with all objects
        Map<LibraryObject, Integer> libraryObjects = new HashMap<>();
        libraryObjects.put(object1, 10);
        libraryObjects.put(object2, 15);
        libraryObjects.put(object3, 25);
        libraryObjects.put(object4, 30);
        libraryObjects.put(object5, 40);
        libraryObjects.put(object6, 50);
        libraryObjects.put(object7, 60);
        library.setWholeLibraryObjects(libraryObjects);


        //create pupils
        HashSet<Pupil> pupils = new HashSet<>();
        Pupil pupil1 = new Pupil("Wisznewecki Eugeny", new MyDate(30, 6, 1990));
        Pupil pupil2 = new Pupil("Podhainy Alexander", new MyDate(15, 2, 1981));
        Pupil pupil3 = new Pupil("Meleshko Sdasd", new MyDate(12, 4, 1995));

        pupils.add(pupil1);
        pupils.add(pupil2);
        pupils.add(pupil3);

        library.setPupils(pupils);


        Library.getWholeLibrary().printLibraryObjects();     //Printing without rentings ALL BOOKS!
        Library.getWholeLibrary().printRents();

        //rents objects from library
        library.rent(object1, pupil1);
        library.rent(object5, pupil1);
        library.rent(object6, pupil1);

        library.rent(object2, pupil2);
        library.rent(object5, pupil2);

        library.rent(object6, pupil3);
        library.rent(object3, pupil3);



        Library.getWholeLibrary().printLibraryObjects();     //Printing without rentings ALL BOOKS!
        Library.getWholeLibrary().printRents();


        //return object
        library.returnObject(object1, pupil1 );
        library.returnObject(object6, pupil1 );

        library.returnObject(object6, pupil3 );

        library.returnObject(object2, pupil2 );
        library.returnObject(object2, pupil2 );



        Library.getWholeLibrary().printLibraryObjects();         //Printing with rents, minus goods in rent
        Library.getWholeLibrary().printRents();


        Library.getWholeLibrary().printLessTwoReadByBooks();

        Library.getWholeLibrary().printLessTwoReadByDate();

        Library.getWholeLibrary().printTaskOneByBooks();

        try{
            Operator.writerLibrary(Library.getWholeLibrary());
        }
        catch (IOException e){
                e.printStackTrace();
                System.out.print("Запись не прошла");
        }
    }
}
