package izolotarev.bookwormapp.model;

/**
 * Created by Игорь on 10.06.2018.
 */

public class StockItem {
    private Book book;
    private int branchId;
    private double price;
    private int quantity;

    public StockItem(Book book, int branchId, double price, int quantity) {
        this.book = book;
        this.branchId = branchId;
        this.price = price;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
