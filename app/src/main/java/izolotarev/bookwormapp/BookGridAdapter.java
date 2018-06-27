package izolotarev.bookwormapp;

import android.app.Activity;
import android.net.Uri;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

import izolotarev.bookwormapp.model.Book;
import izolotarev.bookwormapp.model.StockItem;

/**
 * Created by Игорь on 27.05.2018.
 */

public class BookGridAdapter extends BaseAdapter{
    ArrayList<StockItem> stock;
    public static Activity activity;

    public BookGridAdapter(Activity activity, ArrayList<StockItem> stock){
        this.stock = stock;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return stock.size();
    }

    @Override
    public Object getItem(int i) {
        return stock.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(activity);
            view = inflater.inflate(R.layout.book_item_layout, null);
        }

        TextView titleTextView = (TextView)view.findViewById(R.id.bookTitleTextView);
        StockItem stockItem = (StockItem) stock.get(i);
        final Book book = stockItem.getBook();

        titleTextView.setText(book.getTitle());

        TextView authorTextView = (TextView)view.findViewById(R.id.bookAuthorTextView);
        authorTextView.setText(book.getAuthor());

        TextView dateTextView = (TextView)view.findViewById(R.id.releaseDateTextView);
        dateTextView.setText(book.getReleaseDate());

        TextView priceTextView = (TextView)view.findViewById(R.id.priceTextView);
        priceTextView.setText(String.valueOf(stockItem.getPrice()));

        Button orderButton = (Button) view.findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookActivity.activity, "order" + book.getBookId(), Toast.LENGTH_SHORT).show();
            }
        });

        final ShareDialog shareDialog = new ShareDialog(BookActivity.activity);

        final Button shareButton = (Button) view.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("This book is just amazing! " + book.getTitle() + " " + book.getAuthor())
                        .setContentUrl(Uri.parse("https://bookworm.ac.nz"))
                        .build();
                if(shareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(linkContent);
                }
            }
        });

        return view;
    }

}
