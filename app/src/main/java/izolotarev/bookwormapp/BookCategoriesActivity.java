package izolotarev.bookwormapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import izolotarev.bookwormapp.model.Category;
import izolotarev.bookwormapp.model.CategoryJoinBranch;

public class BookCategoriesActivity extends AppCompatActivity {

    ArrayList<Category> categories;
    GridView gridView;
    BookCategoriesGridAdapter adapter;
    public static Activity activity;
    private int branchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_categories);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        activity = this;
        Bundle bundle = getIntent().getExtras();
        branchId = bundle.getInt("branchId");
        gridView = (GridView) findViewById(R.id.gridView);

        //Toast.makeText(this, String.valueOf(branchId), Toast.LENGTH_LONG).show();

        ApiConfig.fetchCategoriesJoinBranch(BookCategoriesActivity.this, new ApiConfig.CategoryForBranchCallback() {
            @Override
            public void onFinish(ArrayList<CategoryJoinBranch> categoriesForBranch) {
                categories = new ArrayList<>();
                for (CategoryJoinBranch categoryForBranch : categoriesForBranch){
                    if (categoryForBranch.getBranchId() == branchId){
                        categories.add(new Category(categoryForBranch.getCategoryId(), categoryForBranch.getName()));
                    }
                }
                adapter = new BookCategoriesGridAdapter(BookCategoriesActivity.this, categories);
                gridView.setAdapter(adapter);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Category category = categories.get(position);

                Intent intent = new Intent(activity, BookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("branchId", branchId );
                bundle.putInt("categoryId", category.getCategoryId());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

//        using sqlite
//        db.open();
//        Cursor categoriesCursor = db.getCategoriesForBranch(branch.getBranchId());
//        if (categoriesCursor != null) {
//            if (categoriesCursor.getCount() < 1) {
//                Toast.makeText(getApplicationContext(), "Sorry, no available books at this moment", Toast.LENGTH_LONG).show();
//            } else {
//                while (categoriesCursor.moveToNext()){
//                    int categoryId = categoriesCursor.getInt(0);
//                    String categoryName = categoriesCursor.getString(1);
//                    Category category = new Category(categoryId, categoryName);
//                    category.setBranch(branch);
//                    categories.add(category);
//                }
//            }
//        }
//        db.close();

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
            default:

        }
        return super.onOptionsItemSelected(item);
    }
}
