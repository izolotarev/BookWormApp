package izolotarev.bookwormapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import izolotarev.bookwormapp.model.Branch;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameEditText, emailEditText, passwordEditText, cardNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        cardNumberEditText = (EditText) findViewById(R.id.cardNumberEditText);


    }

    public void signUp(View view) {
        String userName = userNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String cardNumber = cardNumberEditText.getText().toString().trim();

        String encryptedPassword = null;
        try {
            encryptedPassword = hashPassword(password);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        signUpUser(userName, email, encryptedPassword, cardNumber);
    }

    private static final String TAG = VolleySingleton.class.getSimpleName();

    private void signUpUser(final String username, final String email, final String password, final String cardNumber ){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Register response: " + response.toString());

                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error){
                        JSONObject user = jsonObject.getJSONObject("users");
                        //getting items
                        String userName = user.getString("username");
                        String password = user.getString("password");
                        toast("Congratulations! " + username + " is registered!");

                        Intent intent = new Intent(SignUpActivity.this, BranchActivity.class);
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
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("cardNumber", cardNumber);
                params.put("role", "user");
                params.put("loyaltyPoints", "0");
                params.put("balance", "0");

                return params;
            }
        };
        //adding request to the request queue
        VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);
    }

    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes(), 0, password.length());

        return new BigInteger(1, messageDigest.digest()).toString(16);
    }


}
