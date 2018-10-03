

package com.example.manalighare.inclass05;

import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GetData.LoadData {

    private TextView title;
    private TextView publish_date;

    private ImageView display_image;
    private ImageView prev_image;
    private ImageView next_image;

    private TextView desc;

    private Button quit_btn;
    private ArrayList<News_class> headline;
    
    private int counter=0;
    private int arraylist_size=0;

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BuzzFeed Headlines");

        title = (TextView)findViewById(R.id.headline_label);
        publish_date = (TextView)findViewById(R.id.date_label);

        display_image = (ImageView)findViewById(R.id.display_image);
        prev_image = (ImageView)findViewById(R.id.prev_image);
        next_image = (ImageView)findViewById(R.id.next_image);

        desc = (TextView) findViewById(R.id.desc_content);

        quit_btn = (Button)findViewById(R.id.quit_btn);

        prev_image.setOnClickListener(this);
        next_image.setOnClickListener(this);
        quit_btn.setOnClickListener(this);

        desc.setMovementMethod(new ScrollingMovementMethod());

        headline = new ArrayList<>();


        if(isConnected()){
            new GetData(MainActivity.this).execute("https://newsapi.org/v2/top-headlines?sources=buzzfeed&apiKey=be6624b28c2d46b4983cb56e665785d9");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();

            builder.setTitle("Loading News").setView(inflater.inflate(R.layout.dialog_bar, null));
            dialog = builder.create();
            dialog.show();
        }else{
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean isConnected(){

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo== null ||!networkInfo.isConnected()){
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quit_btn:
                finish();
                break;
            case R.id.prev_image:
                if (counter>0){
                    counter--;
                    dialog.show();
                    LoadHeadline();
                }else{
                    Toast.makeText(this, "You are on the first news!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.next_image:
                if (counter<arraylist_size-1){
                    counter++;
                    dialog.show();
                    LoadHeadline();
                }else{
                    Toast.makeText(this, "This is the last news for today!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void Load_Data(ArrayList<News_class> news) {
        headline=news;
        counter=0;
        arraylist_size=headline.size();
        LoadHeadline();
    }

    private void LoadHeadline() {
        Picasso.get().load(headline.get(counter).image_url).into(display_image);
        title.setText(headline.get(counter).Title);
        desc.setText(headline.get(counter).description);
        publish_date.setText("Published on:"+headline.get(counter).Publish_date);

        dialog.dismiss();
    }
}
