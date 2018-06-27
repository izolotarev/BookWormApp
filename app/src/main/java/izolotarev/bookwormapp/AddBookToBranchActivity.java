package izolotarev.bookwormapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import izolotarev.bookwormapp.model.Book;
import izolotarev.bookwormapp.model.Branch;
import izolotarev.bookwormapp.util_classes.DropdownItem;

public class AddBookToBranchActivity extends AppCompatActivity {

    Spinner selectBookSpinner;
    Spinner selectBranchSpinner;
    EditText priceEditText;
    EditText quantityEditText;
    ArrayList<DropdownItem> branchesDropdownValues;
    ArrayAdapter<DropdownItem> branchesDropdownArrayAdapter;
    ArrayList<DropdownItem> booksDropdownValues;
    ArrayAdapter<DropdownItem> booksDropdownArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_to_branch);

        selectBookSpinner = (Spinner)findViewById(R.id.selectBookSpinner);
        selectBranchSpinner = (Spinner)findViewById(R.id.selectBranchSpinner);
        priceEditText = (EditText)findViewById(R.id.priceEditText);
        quantityEditText = (EditText)findViewById(R.id.quantityEditText);

        booksDropdownValues = new ArrayList<>();
        branchesDropdownValues = new ArrayList<>();

        ApiConfig.fetchBranches(this, new ApiConfig.BranchCallback() {
            @Override
            public void onFinish(ArrayList<Branch> branches) {
                for (Branch branch: branches) {
                    DropdownItem dropdownItem = new DropdownItem(branch.getName(), branch.getBranchId());
                    branchesDropdownValues.add(dropdownItem);
                }
                branchesDropdownArrayAdapter = new ArrayAdapter<DropdownItem>(AddBookToBranchActivity.this, android.R.layout.simple_list_item_1, branchesDropdownValues);
                selectBranchSpinner.setAdapter(branchesDropdownArrayAdapter);
            }
        });

        ApiConfig.fetchBooks(this, new ApiConfig.BookCallback() {
            @Override
            public void onFinish(ArrayList<Book> books) {
                for (Book book: books) {
                    DropdownItem dropdownItem = new DropdownItem(book.getTitle(), book.getBookId());
                    booksDropdownValues.add(dropdownItem);
                }
                booksDropdownArrayAdapter = new ArrayAdapter<DropdownItem>(AddBookToBranchActivity.this, android.R.layout.simple_list_item_1, booksDropdownValues);
                selectBookSpinner.setAdapter(booksDropdownArrayAdapter);
            }
        });

    }

    public void addBookToBranch(View view) {
        DropdownItem bookDropdownItem = (DropdownItem) selectBookSpinner.getSelectedItem();
        DropdownItem branchDropdownItem = (DropdownItem) selectBranchSpinner.getSelectedItem();
        String bookId = null;
        String branchId = null;
        if (bookDropdownItem != null && branchDropdownItem != null){
            bookId = String.valueOf(bookDropdownItem.getId());
            branchId = String.valueOf(branchDropdownItem.getId());
        }
        String price = priceEditText.getText().toString();
        String quantity = quantityEditText.getText().toString();

        ApiConfig.assignBookToBranch(this, branchId, bookId, quantity, price);
    }
}
