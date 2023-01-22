package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class MainActivity extends AppCompatActivity {
    // RESOURCE: https://www.youtube.com/watch?v=3uAqwnxZMwI
    // 1. JSON link on Internet:
    // https://run.mocky.io/v3/59e6beb7-765e-44bc-8649-f238d3996e36
    private static String JSON_URL = "https://run.mocky.io/v3/59e6beb7-765e-44bc-8649-f238d3996e36";

    // 2. Create RecyclerView in xml file and movie_xml for single movie.
    // 3. Create Java MovieModelClass.class should contain:
    //      3.1 All required fields from JSON.
    //      3.2 Two constructors empty and with all fields.
    //      3.3 Getters and setters.
    // 4. Create adapter for recycler view.
    // 5. Create variables and methods in MainActivity.java
    // 6. Add INTERNET permissions in AndroidManifest.xml

    private List<MovieModelClass> movieList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e)   {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("movies");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setName(jsonObject1.getString("name"));
                    model.setImg(jsonObject1.getString("image"));

                    movieList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            putDataIntoRecyclerView(movieList);
        }
    }

    private void putDataIntoRecyclerView(List<MovieModelClass> movieList) {
        MovieAdapter adapter = new MovieAdapter(this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }
}