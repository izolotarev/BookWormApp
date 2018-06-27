package izolotarev.bookwormapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    TextView textView;
    SharedPreferences sharedPreferences;
    int fontSize;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        textView = (TextView)findViewById(R.id.setTextView);
        sharedPreferences = getSharedPreferences("font", 0);
        fontSize = sharedPreferences.getInt("font", 50);
        seekBar.setProgress(fontSize);
        textView.setTextSize(fontSize);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int fontSizeTwo = seekBar.getProgress();
                textView.setTextSize(fontSizeTwo);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("font", fontSizeTwo);
                editor.commit();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int fontSizeTwo = seekBar.getProgress();
        textView.setTextSize(fontSizeTwo);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("font", fontSizeTwo);
        editor.commit();

        //log the time user spent in the app. This is just an example since there is no login system yet
        try {
            FileOutputStream fileOutputStream = openFileOutput("log.txt",0);
            fileOutputStream.write("The time user spent in the app is: ".getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }



    }

    public String textFont = "";

}
