

package com.example.manalighare.inclass05;

import android.os.AsyncTask;
import android.util.Log;


import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetData extends AsyncTask<String, Void, ArrayList<News_class>> {

    LoadData loadData;

    public GetData(LoadData loadData) {
        this.loadData = loadData;
    }

    @Override
    protected ArrayList<News_class> doInBackground(String... strings) {


        HttpURLConnection connection = null;

        ArrayList<News_class> list_of_news= new ArrayList<>();

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               String json = IOUtils.toString(connection.getInputStream(), "UTF-8");

               Log.d("size is:",""+json.length());
                JSONObject root = new JSONObject(json);
                JSONArray articles = root.getJSONArray("articles");


                for (int i=0;i<articles.length();i++) {
                    JSONObject articlesJSONObject = articles.getJSONObject(i);

                    News_class news = new News_class();
                    news.Title = articlesJSONObject.getString("title");
                    news.Publish_date = articlesJSONObject.getString("publishedAt").substring(0,10);
                    news.image_url = articlesJSONObject.getString("urlToImage");
                    news.description = articlesJSONObject.getString("description");

                    list_of_news.add(news);


                }

            }
        }   catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list_of_news;

    }


    @Override
    protected void onPostExecute(ArrayList<News_class> news_classes) {
        if (news_classes!=null){
            Log.d("demo",news_classes.toString());
            loadData.Load_Data(news_classes);
        }else{
            Log.d("demo","No result found");
        }
    }

    public static interface LoadData{
        public void Load_Data(ArrayList<News_class> news);
    }
}
