package izolotarev.bookwormapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import izolotarev.bookwormapp.model.Book;
import izolotarev.bookwormapp.model.StockItem;
import izolotarev.bookwormapp.model.Branch;
import izolotarev.bookwormapp.model.Category;
import izolotarev.bookwormapp.model.CategoryJoinBranch;

/**
 * Created by Игорь on 31.05.2018.
 */

public class ApiConfig {
    public static final String BASE_IP = "https://app-1527725476.000webhostapp.com/";
    public static final String LOGIN_URL = BASE_IP + "login.php";
    public static final String REGISTER_URL = BASE_IP + "register.php";
    public static final String BRANCHES_URL = BASE_IP + "getBranches.php";
    public static final String ADDCATEGORY_URL = BASE_IP + "addCategory.php";
    public static final String GETCATEGORIES_URL = BASE_IP + "getCategories.php";
    public static final String REGISTERBOOK_URL = BASE_IP + "registerBook.php";
    public static final String GETBOOKS_URL = BASE_IP + "getBooks.php";
    public static final String ADDBOOKTOBRANCH_URL = BASE_IP + "addBookToBranch.php";
    public static final String GETCATEGORIESINBRANCH_URL = BASE_IP + "getCategoriesForBranch.php";
    public static final String GETSTOCK_URL = BASE_IP + "getStock.php";

    public static void fetchBranches(final Context context, final BranchCallback callback) {
        final ProgressDialog loading = ProgressDialog.show(context, "","Loading...",false,false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiConfig.BRANCHES_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                Log.d(TAG, "Register response: " + response.toString());
                int count = 0;
                ArrayList<Branch> branches = new ArrayList<Branch>();
                try {
                    while (count < response.length()){
                        JSONObject jsonObject = response.getJSONObject(count);
                        Branch branch = new Branch(jsonObject.getInt("branch_id"), jsonObject.getString("name") , jsonObject.getString("image_url"));
                        branches.add(branch);
                        count++;
                    }
                    callback.onFinish(branches);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Volley error. " + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        VolleySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void loadCategory(final Context context, final String id, final String name){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.ADDCATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Register response: " + response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error){
                        //getting items
                        String message = jsonObject.getString("message");
                        toast(message, context);
                    } else {
                        String errorMsg = jsonObject.getString("error_msg");
                        toast(errorMsg,context);
                    }
                } catch (JSONException ex){
                    ex.printStackTrace();
                    toast(ex.getMessage(), context);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast("Volley error. " + error.getMessage(), context);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("name", name);

                return params;
            }
        };
        //adding request to the request queue - AndroidLoginController
        VolleySingleton.getmInstance(context).addToRequestQueue(stringRequest);
    }

    public static void fetchCategories(final Context context, final CategoryCallback callback) {
        final ProgressDialog loading = ProgressDialog.show(context, "","Loading...",false,false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiConfig.GETCATEGORIES_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                int count = 0;
                ArrayList<Category> categories = new ArrayList<>();
                try {
                    while (count < response.length()){
                        JSONObject jsonObject = response.getJSONObject(count);
                        Category category = new Category(jsonObject.getInt("category_id"), jsonObject.getString("name"));
                        categories.add(category);
                        count++;
                    }
                    callback.onFinish(categories);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Volley error. " + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        VolleySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void loadBook(final Context context, final String bookId, final String title, final String author, final String releaseDate, final String categoryId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.REGISTERBOOK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Register response: " + response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error){
                        //getting items
                        String message = jsonObject.getString("message");
                        toast(message, context);

                    } else {
                        String errorMsg = jsonObject.getString("error_msg");
                        toast(errorMsg,context);
                    }
                } catch (JSONException ex){
                    ex.printStackTrace();
                    toast(ex.getMessage(), context);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast("Volley error. " + error.getMessage(), context);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bookId", bookId);
                params.put("title", title);
                params.put("author", author);
                params.put("releaseDate", releaseDate);
                params.put("categoryId", categoryId);

                return params;
            }
        };
        //adding request to the request queue - AndroidLoginController
        VolleySingleton.getmInstance(context).addToRequestQueue(stringRequest);
    }

    public static void fetchBooks(final Context context, final BookCallback callback) {
        final ProgressDialog loading = ProgressDialog.show(context, "","Loading...",false,false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiConfig.GETBOOKS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                Log.d(TAG, "Register response: " + response.toString());
                int count = 0;
                ArrayList<Book> books = new ArrayList<Book>();
                try {
                    while (count < response.length()){
                        JSONObject jsonObject = response.getJSONObject(count);
                        Book book = new Book(jsonObject.getInt("book_id"),
                                             jsonObject.getString("title"),
                                             jsonObject.getString("author"),
                                             jsonObject.getString("release_date"),
                                             jsonObject.getInt("category_id"));
                        books.add(book);
                        count++;
                    }
                    callback.onFinish(books);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Volley error. " + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        VolleySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void assignBookToBranch(final Context context, final String branchId, final String bookId, final String quantity, final String price){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiConfig.ADDBOOKTOBRANCH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "Register response: " + response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error){
                        //getting items
                        String message = jsonObject.getString("message");
                        toast(message, context);

                    } else {
                        String errorMsg = jsonObject.getString("error_msg");
                        toast(errorMsg,context);
                    }
                } catch (JSONException ex){
                    ex.printStackTrace();
                    toast(ex.getMessage(), context);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toast("Volley error. " + error.getMessage(), context);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bookId", bookId);
                params.put("branchId", branchId);
                params.put("quantity", quantity);
                params.put("price", price);

                return params;
            }
        };
        //adding request to the request queue - AndroidLoginController
        VolleySingleton.getmInstance(context).addToRequestQueue(stringRequest);
    }

    public static void fetchCategoriesJoinBranch(final Context context, final CategoryForBranchCallback callback) {
        final ProgressDialog loading = ProgressDialog.show(context, "","Loading...",false,false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiConfig.GETCATEGORIESINBRANCH_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                int count = 0;
                ArrayList<CategoryJoinBranch> categoriesForBranch = new ArrayList<>();
                try {
                    while (count < response.length()){
                        JSONObject jsonObject = response.getJSONObject(count);
                        CategoryJoinBranch categoryForBranch = new CategoryJoinBranch(jsonObject.getInt("category_id"), jsonObject.getString("name"), jsonObject.getInt("branch_id"));
                        categoriesForBranch.add(categoryForBranch);
                        count++;
                    }
                    callback.onFinish(categoriesForBranch);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Volley error. " + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        VolleySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void fetchStockItems(final Context context, final StockCallback callback) {
        final ProgressDialog loading = ProgressDialog.show(context, "","Loading...",false,false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiConfig.GETSTOCK_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                int count = 0;
                ArrayList<StockItem> stock = new ArrayList<>();
                try {
                    while (count < response.length()){
                        JSONObject jsonObject = response.getJSONObject(count);
                        Book book = new Book(jsonObject.getInt("book_id"),
                                             jsonObject.getString("title"),
                                             jsonObject.getString("author"),
                                             jsonObject.getString("release_date"),
                                             jsonObject.getInt("category_id")
                                );

                        StockItem stockItem = new StockItem(book,
                                                            jsonObject.getInt("branch_id"),
                                                            jsonObject.getDouble("price"),
                                                            jsonObject.getInt("quantity"));
                        stock.add(stockItem);
                        count++;
                    }
                    callback.onFinish(stock);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Volley error. " + error.getMessage(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        VolleySingleton.getmInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void toast(String s, Context context){
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    private static final String TAG = VolleySingleton.class.getSimpleName();

    public interface BranchCallback {
        void onFinish(ArrayList<Branch> branches);
    }

    public interface CategoryCallback {
        void onFinish(ArrayList<Category> categories);
    }

    public interface CategoryForBranchCallback {
        void onFinish(ArrayList<CategoryJoinBranch> categoriesForBranch);
    }

    public interface StockCallback {
        void onFinish(ArrayList<StockItem> stock);
    }

    public interface BookCallback {
        void onFinish(ArrayList<Book> books);
    }

}
