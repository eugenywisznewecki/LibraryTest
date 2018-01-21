package Data;

import java.io.Serializable;

/**
 * Created by Ink on 21.01.2018.
 */

public class LibraryObject implements Serializable {
    private Category category;
    private String title;
    //private int price;

    public LibraryObject(Category category, String title) {
        this.category = category;
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryObject that = (LibraryObject) o;

        if (category != that.category) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
