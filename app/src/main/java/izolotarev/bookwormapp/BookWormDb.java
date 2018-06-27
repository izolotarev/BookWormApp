package izolotarev.bookwormapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import izolotarev.bookwormapp.model.Book;
import izolotarev.bookwormapp.model.Branch;
import izolotarev.bookwormapp.model.Category;

/**
 * Created by Игорь on 26.05.2018.
 */

public class BookWormDb extends SQLiteOpenHelper {
    //tables
    private static String databaseName = "bookworm.db";
    private static int databaseEdition = 11;
    private static String tableBook = "tbl_book";
    private static String tableBranch = "tbl_branch";
    private static String tableBranchBook = "tbl_branch_book";
    private static String tableCategory = "tbl_category";
    private static String tableUser = "tbl_user";
    private static String tableRole = "tbl_role";
    private static String tableUserFavBranch = "tbl_user_fav_branch";
    private static String tableOrder = "tbl_order";
    private static String tableOrderDetail = "tbl_order_detail";

    private String categoryIdColName = "category_id";
    private String bookIdColName = "book_id";
    private String branchIdColName = "branch_id";

    //views
    private static String viewCategoriesForBranch = "vw_categories_for_branch";
    private static String viewBooksForCategoryForBranch = "vw_books_for_category_for_branch";

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public BookWormDb(Context context){
        super(context, databaseName, null, databaseEdition);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table " + tableBook + "(book_id integer PRIMARY KEY," +
                    "title varchar(255)," +
                    "author varchar(255)," +
                    "category_id integer," +
                    "release_date date," +
                    "FOREIGN KEY(category_id) REFERENCES tbl_category(category_id) ON UPDATE CASCADE);"
            );

            Toast.makeText(context, "Book table was created", Toast.LENGTH_LONG).show();

            db.execSQL("create table " + tableBranch + "(branch_id integer PRIMARY KEY," +
                            "name varchar(255))");


                    Toast.makeText(context, "Branch table was created",Toast.LENGTH_LONG).show();

            db.execSQL("create table " + tableBranchBook + "(id integer PRIMARY KEY AUTOINCREMENT," +
                    "branch_id integer," +
                    "book_id integer," +
                    "quantity integer," +
                    "price real," +
                    "FOREIGN KEY(branch_id) REFERENCES tbl_branch(branch_id) ON UPDATE CASCADE," +
                    "FOREIGN KEY(book_id) REFERENCES tbl_book(book_id) ON UPDATE CASCADE);"
            );


            Toast.makeText(context, "Branch - Book table was created",Toast.LENGTH_LONG).show();

            db.execSQL("create table " + tableCategory + "(category_id integer PRIMARY KEY," +
                            "name varchar(255))");


                    Toast.makeText(context, "Category table was created",Toast.LENGTH_LONG).show();

            db.execSQL("create table " + tableUser + "(user_id integer PRIMARY KEY," +
                    "name varchar(255)," +
                    "password varchar(255)," +
                    "balance real," +
                    "loyalty_points integer," +
                    "role_id integer," +
                    "FOREIGN KEY(role_id) REFERENCES tbl_role(role_id) ON UPDATE CASCADE)");

            Toast.makeText(context, "Role table was created",Toast.LENGTH_LONG).show();

            db.execSQL("create table " + tableRole + "(role_id integer PRIMARY KEY," +
                    "name varchar(255))");

            Toast.makeText(context, "User table was created",Toast.LENGTH_LONG).show();


            db.execSQL("create table " + tableUserFavBranch + "(id integer PRIMARY KEY," +
                    "user_id integer," +
                    "branch_id integer," +
                    "FOREIGN KEY(user_id) REFERENCES tbl_user(user_id) ON UPDATE CASCADE," +	"FOREIGN KEY(branch_id) REFERENCES tbl_branch(branch_id) ON UPDATE CASCADE);"
            );


            Toast.makeText(context, "User – Favourite branch table was created",Toast.LENGTH_LONG).show();

            db.execSQL("create table " + tableOrder + "(order_id integer PRIMARY KEY," +
                    "user_id integer," +
                    "order_price real," +
                    "FOREIGN KEY(user_id) REFERENCES tbl_user(user_id) ON UPDATE CASCADE);"
            );


            Toast.makeText(context, "Order table was created",Toast.LENGTH_LONG).show();

            db.execSQL("create table " + tableOrderDetail + "(order_detail_id integer PRIMARY KEY," +
                    "order_id integer," +
                    "branch_id integer," +
                    "book_id integer," +
                    "quantity integer," +
                    "price real," +
                    "FOREIGN KEY(branch_id) REFERENCES tbl_branch(branch_id) ON UPDATE CASCADE," +
                    "FOREIGN KEY(book_id) REFERENCES tbl_book(book_id) ON UPDATE CASCADE," +
                    "FOREIGN KEY(order_id) REFERENCES tbl_order(order_id) ON UPDATE CASCADE);"
            );


            Toast.makeText(context, "OrderDetail table was created",Toast.LENGTH_LONG).show();

            //Views

            db.execSQL("create view " + viewCategoriesForBranch + " AS " +
                    "SELECT DISTINCT " +
                    "tbl_category.category_id, tbl_category.name, tbl_branch_book.branch_id  " +
                    "FROM " +
                    "tbl_branch_book " +
                    "INNER JOIN " +
                    "tbl_book ON tbl_branch_book.book_id = tbl_book.book_id " +
                    "INNER JOIN " +
                    "tbl_category ON tbl_book.category_id = tbl_category.category_id");

            Toast.makeText(context, "Category for Branch view was created",Toast.LENGTH_LONG).show();

            db.execSQL("create view " + viewBooksForCategoryForBranch + " AS " +
                    "SELECT DISTINCT " +
                    "tbl_branch_book.branch_id, tbl_category.category_id, tbl_branch_book.book_id, tbl_book.title, tbl_book.author, tbl_book.release_date " +
                    "FROM " +
                    "tbl_branch_book " +
                    "INNER JOIN " +
                    "tbl_book ON tbl_branch_book.book_id = tbl_book.book_id " +
                    "INNER JOIN " +
                    "tbl_category ON tbl_book.category_id = tbl_category.category_id ");

            Toast.makeText(context, "Books for category for Branch view was created",Toast.LENGTH_LONG).show();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tableBook);
        Toast.makeText(context, "Book table was deleted", Toast.LENGTH_LONG).show();
        db.execSQL("drop table if exists " + tableBranch);
        Toast.makeText(context, "Branch table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop table if exists " + tableBranchBook);
        Toast.makeText(context, "BranchBook table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop table if exists " + tableCategory);
        Toast.makeText(context, "Category table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop table if exists " + tableUser);
        Toast.makeText(context, "User table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop table if exists " + tableRole);
        Toast.makeText(context, "Role table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop table if exists " + tableUserFavBranch);
        Toast.makeText(context, "User fav branch table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop table if exists " + tableOrder);
        Toast.makeText(context, "Order table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop table if exists " + tableOrderDetail);
        Toast.makeText(context, "Order detail table was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop view if exists " + viewCategoriesForBranch);
        Toast.makeText(context, "Category for branch view was deleted", Toast.LENGTH_LONG).show();

        db.execSQL("drop view if exists " + viewBooksForCategoryForBranch);
        Toast.makeText(context, "Books for category for branch view was deleted", Toast.LENGTH_LONG).show();

        onCreate(db);
    }

    public void open(){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public boolean insertBook(Book book) {
        ContentValues cv = new ContentValues();
        cv.put("book_id", book.getBookId());
        cv.put("title", book.getTitle());
        cv.put("author", book.getAuthor());
        cv.put("release_date", book.getReleaseDate().toString());
        cv.put("category_id", book.getCategoryId());

        long status = sqLiteDatabase.insert(tableBook, null, cv);

        if (status != -1){
            return true;
        }
        return false;
    }

    public boolean insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put("category_id", category.getCategoryId());
        cv.put("name", category.getName());

        long status = sqLiteDatabase.insert(tableCategory, null, cv);

        if (status != -1){
            return true;
        }
        return false;
    }

    public int updateCategory(Category category, String name){
        ContentValues cv = new ContentValues();
        cv.put("name", name);

        return sqLiteDatabase.update(tableCategory, cv, categoryIdColName + "=?", new String[]{String.valueOf(category.getCategoryId())});
    }

    public int deleteCategory(Category category){
        return sqLiteDatabase.delete(tableCategory, categoryIdColName + "=?", new String[]{String.valueOf(category.getCategoryId())});
    }

    public int deleteBook(Book book){
        return sqLiteDatabase.delete(tableBook, bookIdColName + "=?", new String[]{String.valueOf(book.getBookId())});
    }

    public int updateBook(int bookId, String title, String author, String releaseDate, int categoryId){
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("author", author);
        cv.put("release_date", releaseDate);
        cv.put("category_id", categoryId);

        return sqLiteDatabase.update(tableBook, cv, categoryIdColName + "=?", new String[]{String.valueOf(bookId)});
    }

    public boolean insertBranch(Branch branch) {
        ContentValues cv = new ContentValues();
        cv.put("branch_id", branch.getBranchId());
        cv.put("name", branch.getName());

        long status = sqLiteDatabase.insert(tableBranch, null, cv);

        if (status != -1){
            return true;
        }
        return false;
    }

    public Cursor getAllBranches(){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableBranch, null);
        return cursor;
    }

    public Cursor getAllBooks(){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableBook, null);
        return cursor;
    }

    public Cursor getCategoriesForBranch(int id){
        Cursor cursor = sqLiteDatabase.query(viewCategoriesForBranch, null, branchIdColName + "=?", new String[]{String.valueOf(id)},null,null,null);
        return cursor;
    }

    public Cursor getBooksForCategoryForBranch(int branchId, int categoryId){
        Cursor cursor = sqLiteDatabase.query(viewBooksForCategoryForBranch, null, branchIdColName + "=? AND " + categoryIdColName + "=?", new String[]{String.valueOf(branchId), String.valueOf(categoryId)},null,null,null);
        return cursor;
    }

    public boolean insertBookBranch(int branchId, int bookId, int quantity, double price) {
        ContentValues cv = new ContentValues();
        cv.put("branch_id", branchId);
        cv.put("book_id", bookId);
        cv.put("quantity", quantity);
        cv.put("price", price);

        long status = sqLiteDatabase.insert(tableBranchBook, null, cv);

        if (status != -1){
            return true;
        }
        return false;
    }



}

