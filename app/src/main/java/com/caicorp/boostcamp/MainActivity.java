package com.caicorp.boostcamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.caicorp.boostcamp.Data.MovieItem;
import com.caicorp.boostcamp.Data.MovieList;
import com.caicorp.boostcamp.Data.ResponseInfo;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    Button button;
    MovieAdapter adapter;

    String clientId = "tMjpxQYEx4Y4nKEvag7W";
    String clientSecret = "30XZGfAv7v";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);


        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter(getApplicationContext());


        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieAdapter.ViewHolder holder, View view, int position) {
                MovieItem item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "Clicked Item : " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMovieList();
            }
        });


        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }


    public void requestMovieList() {

        String title = editText.getText().toString();
        String url = "https://openapi.naver.com/v1/search/movie.json?query=";
        String encodeText = null;
        try {
            encodeText = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += encodeText + "&display=100";


        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("MovieRequest", "응답받음 : " + response);
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("X-Naver-Client-Id", clientId);
                headers.put("X-Naver-Client-Secret", clientSecret);
                return headers;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);


    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);
        adapter.addItems(movieList.items);
        recyclerView.setAdapter(adapter);
    }



}
