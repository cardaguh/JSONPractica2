package com.jikansoftware.jsonparsingpractica2;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jikansoftware.jsonparsingpractica2.models.MovieModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivMovieIcon;
    private TextView tvMovie;
    private TextView tvTagline;
    private TextView tvYear;
    private TextView tvDuration;
    private TextView tvDirector;
    private TextView tvMovieRating;
    private RatingBar rbMovieRating;
    private TextView tvCast;
    private TextView tvStory;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setUpViews();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String json = bundle.getString("movieModel");
            MovieModel movieModel = new Gson().fromJson(json, MovieModel.class);

            ImageLoader.getInstance().displayImage(movieModel.getImage(), ivMovieIcon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            tvMovie.setText(movieModel.getMovie());
            tvTagline.setText(movieModel.getTagline());
            tvYear.setText("Year: " + movieModel.getYear());
            tvDuration.setText("Duration: " + movieModel.getDuration());
            tvDirector.setText("Director: " + movieModel.getDirector());

            rbMovieRating.setRating(movieModel.getRating() / 2);

            StringBuffer stringBuffer = new StringBuffer();
            for (MovieModel.Cast cast : movieModel.getCastList()){
                stringBuffer.append(cast.getName() + ", ");
            }

            tvCast.setText("Cast: " + stringBuffer);
            tvStory.setText("Story: " + movieModel.getStory());
        }
    }

    private void setUpViews(){
        ivMovieIcon = (ImageView) findViewById(R.id.ivIcon);
        tvMovie = (TextView) findViewById(R.id.tvMovie);
        tvTagline = (TextView) findViewById(R.id.tvTagline);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvDuration = (TextView) findViewById(R.id.tvDuration);
        tvDirector = (TextView) findViewById(R.id.tvDirector);
        rbMovieRating = (RatingBar) findViewById(R.id.rbMovie);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvStory = (TextView) findViewById(R.id.tvStory);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
