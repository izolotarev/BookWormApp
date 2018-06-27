package izolotarev.bookwormapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCategoryActivity extends AppCompatActivity {
    EditText categoryIdEditText;
    EditText categoryNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
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

    public void uploadCategory(View view) {
         categoryIdEditText = (EditText) findViewById(R.id.categoryIdEditText);
         categoryNameEditText = (EditText) findViewById(R.id.categoryNameEditText);
         String id = categoryIdEditText.getText().toString();
         String name = categoryNameEditText.getText().toString();

         ApiConfig.loadCategory(this, id, name);
    }

}
