package izolotarev.bookwormapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import izolotarev.bookwormapp.model.Category;
import izolotarev.bookwormapp.util_classes.DropdownItem;

public class RegisterBookActivity extends AppCompatActivity {

    EditText addBookIdEditText;
    EditText addBookTitleEditText;
    EditText addBookAuthorEditText;
    EditText addBookReleaseDateEditText;
    Spinner addBookCategorySpinner;
    ArrayList<DropdownItem> categoriesDropdownValues;
    ArrayAdapter<DropdownItem> categoriesDropdownArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_book);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setDisplayShowHomeEnabled(true);

        addBookIdEditText = (EditText) findViewById(R.id.addBookIdEditText);
        addBookTitleEditText = (EditText) findViewById(R.id.addBookTitleEditText);
        addBookAuthorEditText = (EditText) findViewById(R.id.addBookAuthorEditText);
        addBookReleaseDateEditText = (EditText) findViewById(R.id.addBookReleaseDateEditText);
        addBookCategorySpinner = (Spinner) findViewById(R.id.addBookCategorySpinner);

        categoriesDropdownValues = new ArrayList<DropdownItem>();

        ApiConfig.fetchCategories(this, new ApiConfig.CategoryCallback() {
            @Override
            public void onFinish(ArrayList<Category> categories) {
                for (Category category: categories) {
                    DropdownItem dropdownItem = new DropdownItem(category.getName(), category.getCategoryId());
                    categoriesDropdownValues.add(dropdownItem);
                }
                categoriesDropdownArrayAdapter = new ArrayAdapter<DropdownItem>(RegisterBookActivity.this, android.R.layout.simple_list_item_1, categoriesDropdownValues);
                addBookCategorySpinner.setAdapter(categoriesDropdownArrayAdapter);
            }
        });
    }

    public void registerBook(View view) {
        String bookId = addBookIdEditText.getText().toString();
        String title = addBookTitleEditText.getText().toString();
        String author = addBookAuthorEditText.getText().toString();
        String releaseDate = addBookReleaseDateEditText.getText().toString();
        DropdownItem dropdownItem = (DropdownItem) addBookCategorySpinner.getSelectedItem();
        String categoryId = null;
        if (dropdownItem != null){
            categoryId = String.valueOf(dropdownItem.getId());
        }
        ApiConfig.loadBook(this, bookId, title, author, releaseDate, categoryId);

    }

}
