import java.io.*;


public class Operator {

    private final static String FILENAME = "data.dat";


    public static boolean writerLibrary(Library library) throws IOException {

        if (library == null)
            return false;

        File dataFile = new File(FILENAME);
        if (!dataFile.exists())
            dataFile.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(FILENAME)) {               //try с усл.
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(library);
            oos.flush();
            System.out.println("Записано");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Файл на прочитан");
        }
        return false;
    }


    public static Library readWholeLibraryFromFile() throws IOException, NullPointerException {

        File dataFile = new File(FILENAME);
        if (!dataFile.exists())
            return null;

        try (FileInputStream fis = new FileInputStream(FILENAME)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            Library library = (Library) ois.readObject();
            if (library != null) {
                //Library.setWholeLibrary(library);
                System.out.println("log: has read");
                return library;
            } else return null;
        } catch (ClassNotFoundException e) {
            System.out.println("log: class not found");
            e.printStackTrace();
        }

        System.out.println("log: crashed");
        return null;
    }

}
