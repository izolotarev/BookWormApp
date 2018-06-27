package izolotarev.bookwormapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import izolotarev.bookwormapp.model.Branch;

/**
 * Created by Игорь on 03.05.2018.
 */

public class BranchGridAdapter extends BaseAdapter {
    ArrayList<Branch> branches;
    private static Activity activity;
    private ImageLoader imageLoader;

    public BranchGridAdapter(Activity activity, ArrayList<Branch> branches){
        this.branches = branches;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return branches.size();
    }

    @Override
    public Object getItem(int position) {
        return branches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(activity);
            view = inflater.inflate(R.layout.branch_item_layout, null);
        }
        imageLoader = VolleySingleton.getmInstance(activity).getImageLoader();


        NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.branchImageView);
        TextView textView = (TextView)view.findViewById(R.id.branchTextView);

        imageView.setImageUrl(branches.get(position).getImageUrl(), imageLoader);

        //imageView.setImageResource(imageIds[position]);
        textView.setText(branches.get(position).getName());

        return view;
    }

    // references to images
    private Integer[] imageIds = {
            R.drawable.ack, R.drawable.wel,
            R.drawable.chr, R.drawable.dun,

    };


}
