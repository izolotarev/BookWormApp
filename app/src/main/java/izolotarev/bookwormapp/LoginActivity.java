package izolotarev.bookwormapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import izolotarev.bookwormapp.model.Book;
import izolotarev.bookwormapp.model.Branch;
import izolotarev.bookwormapp.model.Category;

public class LoginActivity extends AppCompatActivity {
    //BookWormDb db;
    EditText emailLoginEditText, passwordLoginEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLoginEditText = (EditText) findViewById(R.id.emailLoginEditText);
        passwordLoginEditText = (EditText) findViewById(R.id.passwordLoginEditText);

        //db = new BookWormDb(this);
        //seedDatabase();
    }

    public void login(View view) {
        String email = emailLoginEditText.getText().toString().trim();
        String password = passwordLoginEditText.getText().toString().trim();

        String encryptedPassword = null;
        try {
            encryptedPassword = SignUpActivity.hashPassword(password);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        loginUser(email, encryptedPassword);
    }

    public void goToSignUpActivity(View view) {
        Intent branchIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(branchIntent);
    }

    private void loginUser(final String email, final String password ){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Login response: " + response.toString());

                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error){
                        JSONObject user = jsonObject.getJSONObject("users");
                        //getting items
                        int userId = user.getInt("userId");
                        String role = user.getString("role");

                        Intent intent = new Intent(LoginActivity.this, BranchActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("userId", userId );
                        bundle.putString("role", role );
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        String errorMsg = jsonObject.getString("error_msg");
                        toast(errorMsg);
                    }
                } catch (JSONException ex){
                    ex.printStackTrace();
                    toast(ex.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast("Volley error. " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        String tag = "req_login";
        //adding request to the request queue
        VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private static final String TAG = VolleySingleton.class.getSimpleName();
    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void seedDatabase(BookWormDb db){
        db.open();
        db.insertBranch(new Branch(1, "Auckland", ""));
        db.insertBranch(new Branch(2, "Wellington", ""));
        db.insertBranch(new Branch(3, "Christchurch", ""));
        db.insertBranch(new Branch(4, "Dunedin", ""));

        db.insertCategory(new Category(1, "Fiction"));
        db.insertCategory(new Category(2, "Poetry"));

        db.insertBook(new Book(1,"Carmilla", "Le Fanu, Joseph Sheridan", "01.11.2003" , 1 ));
        db.insertBook(new Book(2,"Lyrics of Earth", "Lampman, Archibald", "01.06.2004" , 2 ));
        db.insertBook(new Book(3,"A Spinner in the Sun", "Reed, Myrtle", "01.06.2004" , 1 ));

        db.insertBookBranch(1, 1, 10, 5.50);
        db.insertBookBranch(1, 2, 7, 4.50);
        db.insertBookBranch(1, 3, 7, 4.50);

        db.close();
    }

}
