package com.caicorp.boostcamp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.caicorp.boostcamp.Data.MovieItem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    ArrayList<MovieItem> items = new ArrayList<MovieItem>();
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem item = items.get(position);
        holder.setItem(item);

        holder.setOnItemClickListener(listener);

    }

    public void addItem(MovieItem item){
        items.add(item);
    }

    public void addItems(ArrayList<MovieItem> items) {
        this.items = items;
    }

    public MovieItem getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;


    }




    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView yearTextView;
        TextView directorTextView;
        TextView actorTextView;
        RatingBar ratingBar;
        ImageView imageView;
        Bitmap bitmap;
        String urlStr;

        OnItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            yearTextView = (TextView) itemView.findViewById(R.id.yearTextView);
            directorTextView = (TextView) itemView.findViewById(R.id.directorTextView);
            actorTextView = (TextView) itemView.findViewById(R.id.actorTextView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });

        }

        public void setItem(MovieItem item) {

            titleTextView.setText(Html.fromHtml(item.getTitle()));
            yearTextView.setText(item.getYear());
            directorTextView.setText(item.getDirector());
            actorTextView.setText(item.getActor());
            if(!TextUtils.isEmpty(item.getRate())) {
                float rating = Float.parseFloat(item.getRate());
                ratingBar.setRating(rating);
            }

            urlStr = item.getUrl();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        URL url = new URL(urlStr);
                        URLConnection conn = url.openConnection();
                        conn.connect();
                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                        bitmap = BitmapFactory.decodeStream(bis);
                        bis.close();
                    } catch(Exception e){

                    }

                }
            });

            t.start();
            try {
                t.join();
                imageView.setImageBitmap(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }


    }


}
