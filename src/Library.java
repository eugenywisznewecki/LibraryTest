/**
 * Created by Ink on 06.04.2017.
 * Singleton
 */

import Data.Category;
import Data.LibraryObject;
import Data.Pupil;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Ink on 21.01.2018.
 */
//the library class will be singleton
//TODO UI file version
public class Library implements Serializable {


    private static Library wholeLibrary;

    private Map<LibraryObject, Integer> libraryObjects;     // все объекты в библиотеке
    private Map<LibraryObject, Integer> rents;              //вообще объекты у людей и их количество
    private HashSet<Pupil> pupilSet;  //все ученики


    private Library() {
        rents = new HashMap<>();
        libraryObjects = new HashMap<>();
    }

    private Library(Map<LibraryObject, Integer> libraryObjects, HashSet<Pupil> pupilRentsList) {
        super();
        this.libraryObjects = libraryObjects;
        this.pupilSet = pupilRentsList;

        rents = new HashMap<>();
    }

    public static Library getWholeLibrary() {
        if (wholeLibrary == null)
            wholeLibrary = new Library();
        return wholeLibrary;
    }

    public static void setWholeLibrary(Library library) {
        wholeLibrary = library;

    }

    public void setWholeLibraryObjects(Map<LibraryObject, Integer> libraryObjects) {
        wholeLibrary.libraryObjects = libraryObjects;
    }


    public HashSet<Pupil> getPupilsList() {
        return pupilSet;
    }

    public void setPupils(HashSet<Pupil> pupilRentsMap) {
        this.pupilSet = pupilRentsMap;
    }


    /**
     * Method to describe RENTS behaviour,
     *
     * @param libraryObject - unit of library object
     * @return true, если такой объект есть в библиотеке
     */
    public boolean rent(LibraryObject libraryObject, Pupil pupil) {
        Integer count = find(libraryObject);
        if (count != null && count != 0) {
            count--;
            libraryObjects.put(libraryObject, count);

            if (libraryObject.getCategory().equals(Category.BOOK)) {
                pupil.setNumOfBooksGet(pupil.getNumOfBooksGet() + 1);

            }

            if (rents.containsKey(libraryObject)) {        // проверка - не был лі такой уже взят
                rents.put(libraryObject, rents.get(libraryObject) + 1);
            } else rents.put(libraryObject, 1);
            return true;
        } else {
            return false;
        }
    }


    public boolean returnObject(LibraryObject libraryObject, Pupil pupil) {
        Integer count = find(libraryObject);
        if (count != null) {
            count++;
            libraryObjects.put(libraryObject, count);

            // в задании только КНИГИ, поэтому сорт по категории
            if (libraryObject.getCategory().equals(Category.BOOK)) {
                pupil.setNumOfBooksGet(pupil.getNumOfBooksGet() - 1);
                pupil.setNumOfBooksReturned(pupil.getNumOfBooksReturned() + 1);
            }

            if (rents.containsKey(libraryObject) && rents.get(libraryObject) > 1) {        // проверка - не был лі такой объект уже взят
                rents.put(libraryObject, rents.get(libraryObject) - 1);
            } else rents.put(libraryObject, 0);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Поиск единицы в библиотеке
     *
     * @param libraryObject единица объекта
     * @return null, если такой единицы нет в библиотеке; или число таких единиц,
     */
    @Nullable
    public Integer find(LibraryObject libraryObject) {
        return libraryObjects.get(libraryObject);                   // NB кол-во по названию по условию.
    }

    //task one
    /**
     * Метод вывода на консоль отчёта об объетах в библиотеке
     * за минусом тех, что взяты
     */
    public void printLibraryObjects() {
        System.out.println(" СЕЙЧАС в библиотеке\nКатегория, название,  количество");
        for (Map.Entry<LibraryObject, Integer> entry : libraryObjects.entrySet()) {
            LibraryObject libraryObject = entry.getKey();
            System.out.println(libraryObject.getCategory() + ", " + libraryObject.getTitle() + ", " + entry.getValue());
        }
        System.out.println("-----------------------------------------------------");
    }


    //task - prints pupils, who have read more than 1 book
    public void printTaskOneByBooks() {
        System.out.println(" Прочитали больше одной книги (включительно)\n по колву книг");

        TreeSet<Pupil> pupilTreeSet = new TreeSet<Pupil>(new Comparator<Pupil>() {
            @Override
            public int compare(Pupil o1, Pupil o2) {
                return o1.getNumOfBooksReturned().compareTo(o2.getNumOfBooksReturned());
            }
        });

        pupilTreeSet.addAll(pupilSet);

        System.out.println("Имя, количество прочитанных книг");
        for (Pupil pupil : pupilTreeSet) {
            if (pupil.getNumOfBooksReturned() >= 1)
                System.out.println(pupil.geName() + " : " + pupil.getNumOfBooksReturned());
            else {
                //log
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    //task - prints pupils, who have read less than 2 books by date
    public void printLessTwoReadByDate() {
        System.out.println(" Прочитали меньше двух книги (включительно)\n по дате рождения");

        //по имени
        TreeSet<Pupil> pupilTreeSet = new TreeSet<Pupil>(new Comparator<Pupil>() {
            @Override
            public int compare(Pupil o1, Pupil o2) {

                if (o1.getDate().year > o2.getDate().year)
                    return 1;
                else if (o1.getDate().year < o2.getDate().year)
                    return -1;
                else {
                    if (o1.getDate().month > o2.getDate().month)
                        return 1;
                    else if (o1.getDate().month < o2.getDate().month)
                        return -1;
                    else {
                        if (o1.getDate().day > o2.getDate().day)
                            return 1;
                        else if (o1.getDate().day < o2.getDate().day)
                            return -1;
                    }
                }
                return 0;
            }
        });

        pupilTreeSet.addAll(pupilSet);

        System.out.println("Имя, количество прочитанных книг");
        for (Pupil pupil : pupilTreeSet) {
            if (pupil.getNumOfBooksReturned() <= 2)
                System.out.println(pupil.geName() + " : " + pupil.getDate().toString() + " " + pupil.getNumOfBooksReturned());
            else {
                //log
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    //task - prints pupils, who have read less than 2 books by number of books
    public void printLessTwoReadByBooks() {
        System.out.println(" Прочитали меньше двух книги (включительно)\n");

        //по имени
        TreeSet<Pupil> pupilTreeSet = new TreeSet<Pupil>(Comparator.comparing(Pupil::getNumOfBooksReturned));

        pupilTreeSet.addAll(pupilSet);

        System.out.println("Имя, количество прочитанных книг");
        for (Pupil pupil : pupilTreeSet) {
            if (pupil.getNumOfBooksReturned() <= 2)
                System.out.println(pupil.geName() + " : " + pupil.getNumOfBooksReturned());
            else {
                //log
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    /**
     * Метод вывода на консоль отчёта об объектах у людей
     */
    public void printRents() {
        System.out.println("объеты у учеников\nКатегория, название количество СЕЙЧАС у учеников");

        for (Map.Entry<LibraryObject, Integer> entry : rents.entrySet()) {
            LibraryObject libraryObject = entry.getKey();
            System.out.println(libraryObject.getCategory() + ", " + libraryObject.getTitle() + ", " + entry.getValue());
        }
        System.out.println("-----------------------------------------------------");
    }

    public Map<LibraryObject, Integer> getLibraryObjects() {
        return libraryObjects;
    }

    public Map<LibraryObject, Integer> getRentsObjects() {
        return rents;
    }


}
