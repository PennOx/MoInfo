package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tk.pankajb.CustomWidgets.LoadingDialog;
import tk.pankajb.Info.InformationQuery;
import tk.pankajb.Info.InfoQueryResponse.InfoResponse;
import tk.pankajb.Info.PosterActivity;

public class InfoActivity extends AppCompatActivity {

    private String movieId;
    private ImageView poster;
    private TextView name;
    private TextView genre;
    private TextView year;
    private TextView language;
    private TextView director;
    private TextView plot;
    private TextView writer;
    private TextView country;
    private TextView rating;
    private String imageUrl;
    private Toolbar toolbar;

    private LoadingDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        movieId = getIntent().getStringExtra("Id");
        poster = findViewById(R.id.mi_poster);
        name = findViewById(R.id.mi_name);
        genre = findViewById(R.id.mi_genre);
        year = findViewById(R.id.mi_year);
        language = findViewById(R.id.mi_language);
        director = findViewById(R.id.mi_director);
        plot = findViewById(R.id.mi_description);
        writer = findViewById(R.id.mi_writer);
        country = findViewById(R.id.mi_country);
        rating = findViewById(R.id.mi_rating);

        toolbar = findViewById(R.id.mi_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loading = new LoadingDialog(InfoActivity.this);
        loading.startLoading();

        InformationQuery query = new InformationQuery(this);
        query.execute(movieId);
    }

    public void updateUI(InfoResponse response) {

        name.setText(response.getTitle());
        genre.setText(response.getGenre());
        year.setText(response.getYear());
        language.setText(response.getLanguage());
        director.setText(response.getDirector());
        plot.setText(response.getPlot());
        writer.setText(response.getWriter());
        country.setText(response.getCountry());
        rating.setText(response.getImdbRating());

        if (response.getPoster() != null && !response.getPoster().isEmpty()) {
            Glide.with(this).load(response.getPoster()).placeholder(R.drawable.movie_default).into(poster);
            imageUrl = response.getPoster();
        }

        loading.stopLoading();
    }

    public void goneWrong(String msg){

        Intent wrongIntent = new Intent();

        wrongIntent.putExtra("msg",msg);

        setResult(400,wrongIntent);
        finish();
    }
    public void showPoster(View view){
        Intent intent = new Intent(InfoActivity.this, PosterActivity.class);
        intent.putExtra("imageUrl",imageUrl);
        startActivity(intent);
    }
}