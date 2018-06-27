package izolotarev.bookwormapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import izolotarev.bookwormapp.model.Book;
import izolotarev.bookwormapp.model.StockItem;

public class BookActivity extends AppCompatActivity {
    ArrayList<Book> books;
    GridView gridView;
    BookGridAdapter adapter;
    public static Activity activity;
    public int branchId;
    public int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        branchId = bundle.getInt("branchId");
        categoryId = bundle.getInt("categoryId");

//      using sqlite
//        books = new ArrayList<>();
//
//        BookWormDb db = new BookWormDb(this);
//        db.open();
//        Cursor booksCursor = db.getBooksForCategoryForBranch(branchId, categoryId);
//        if (booksCursor != null) {
//            if (booksCursor.getCount() < 1) {
//                Toast.makeText(getApplicationContext(), "Sorry, no available books at this moment", Toast.LENGTH_LONG).show();
//            } else {
//                while (booksCursor.moveToNext()){
//                    int bookId = booksCursor.getInt(2);
//                    String title = booksCursor.getString(3);
//                    String author = booksCursor.getString(4);
//                    String releaseDate = booksCursor.getString(5);
//                    Book book = new Book(bookId,title,author,releaseDate,categoryId);
//                    books.add(book);
//                }
//            }
//        }
//        db.close();

        gridView = (GridView) findViewById(R.id.bookGridView);
        activity = this;

        ApiConfig.fetchStockItems(this, new ApiConfig.StockCallback() {
            @Override
            public void onFinish(ArrayList<StockItem> stock) {
                //filter
                ArrayList<StockItem> filteredStock = new ArrayList<>();

                for (StockItem stockItem : stock){
                    if (branchId == stockItem.getBranchId() && categoryId == stockItem.getBook().getCategoryId()){
                        filteredStock.add(stockItem);
                    }
                }
                adapter = new BookGridAdapter(BookActivity.this, filteredStock);
                gridView.setAdapter(adapter);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String s = "" + position;

                Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    //facebook share
    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("izolotarev.bookwormapp",
                                PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.register_book:
                Intent addbookIntent = new Intent(this, RegisterBookActivity.class);
                startActivity(addbookIntent);
                break;
            case R.id.cart:
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}
