package izolotarev.bookwormapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Random;

import izolotarev.bookwormapp.model.Category;

/**
 * Created by Игорь on 26.04.2018.
 */

public class BookCategoriesGridAdapter extends BaseAdapter {
    ArrayList<Category> categories;
    public static Activity activity;
    private ImageLoader imageLoader;

    public BookCategoriesGridAdapter(Activity activity, ArrayList<Category> categories){
        this.categories = categories;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(activity);
            view = inflater.inflate(R.layout.cat_item_layout, null);
        }
        imageLoader = VolleySingleton.getmInstance(activity).getImageLoader();

        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.categoryImageView);
        TextView textView = (TextView)view.findViewById(R.id.categoryTextView);

        int randomInt = getRandomNumberInRange(0,9);
        String imageUrl = "https://app-1527725476.000webhostapp.com/images/book_" + randomInt + ".png";

        imageView.setImageUrl(imageUrl, imageLoader);
        textView.setText(categories.get(i).getName());

        return view;
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


}
