package izolotarev.bookwormapp.util_classes;

/**
 * Created by Игорь on 08.06.2018.
 */

public class DropdownItem {
    private String itemName;
    private int id;

    public DropdownItem(String itemName, int id) {
        this.itemName = itemName;
        this.id = id;
    }

    @Override
    public String toString() {
        return itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
