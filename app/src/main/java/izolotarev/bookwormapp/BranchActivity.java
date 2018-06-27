package izolotarev.bookwormapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import izolotarev.bookwormapp.model.Branch;

public class BranchActivity extends AppCompatActivity {

    ArrayList<Branch> branchesArray;
    GridView gridView;
    BranchGridAdapter adapter;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);




//        using db
//        BookWormDb db = new BookWormDb(this);
//        db.open();
//        branches = new ArrayList<Branch>();
//
//        Cursor branchesCursor = db.getAllBranches();
//        while (branchesCursor.moveToNext()){
//            int branchId = branchesCursor.getInt(0);
//            String branchName = branchesCursor.getString(1);
//            branches.add(new Branch(branchId, branchName));
//        }
//        db.close();


        //using webservice

        gridView = (GridView) findViewById(R.id.branchGridView);
        activity = this;

        ApiConfig.fetchBranches(this, new ApiConfig.BranchCallback() {
            @Override
            public void onFinish(ArrayList<Branch> branches) {
                branchesArray = branches;
                adapter = new BranchGridAdapter(BranchActivity.this, branchesArray);
                gridView.setAdapter(adapter);
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Branch branch = branchesArray.get(position);

                Intent intent = new Intent(activity, BookCategoriesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("branchId", branch.getBranchId() );
                bundle.putString("branchName", branch.getName());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        //hide AddBook activity for users. Available only for Branch and Marketing managers
        //branchmanager, marketingmanager - email and password - branchmanager, marketingmanager
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String role = bundle.getString("role");
            if (role.equals("user")){
                MenuItem item = menu.findItem(R.id.register_book);
                item.setVisible(false);
            }
        }
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
                Intent registerBookIntent = new Intent(this, RegisterBookActivity.class);
                startActivity(registerBookIntent);
                break;
            case R.id.cart:
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

}

