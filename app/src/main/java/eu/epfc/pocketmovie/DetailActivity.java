package eu.epfc.pocketmovie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import eu.epfc.pocketmovie.model.Film;

public class DetailActivity extends AppCompatActivity {

    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        film = (Film) intent.getSerializableExtra("filmObject");
        ImageView backdrop = findViewById(R.id.image_backdrop);
        TextView title = findViewById(R.id.text_title);
        TextView releaseDate = findViewById(R.id.text_date);
        TextView gender = findViewById(R.id.text_gender);
        TextView rating = findViewById(R.id.text_rating_value);
        TextView summary = findViewById(R.id.text_summary);
        if(film != null) {
            PMHelper.setImageView(backdrop, film.getBackdrop());
            title.setText(film.getTitle());
            releaseDate.setText(film.getReleaseDate());
            gender.setText(film.getGendersString());
            rating.setText("" + film.getRate());
            summary.setText(film.getSummary());
        } else {
            title.setText( "Sorry, unexpected error");
        }
        CheckBox favorite = findViewById(R.id.checkBox_pocket);
        favorite.setChecked(isFavorite(film));
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO save in DB
                if(isChecked) {
                    SavedFilmManager.getInstance().addFilm(film);
                } else {
                    SavedFilmManager.getInstance().removeFilm(film);
                }
            }
        });

    }

    public void onClickButtonVideo(View v) {
        Log.d("test", "onClickButtonVideo: click");
        String videoId = film.getTrailer();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }

    private boolean isFavorite(Film film) {
        return SavedFilmManager.getInstance().contains(film);
    }


}
