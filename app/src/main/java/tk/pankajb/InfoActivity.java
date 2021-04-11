package tk.pankajb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import tk.pankajb.CustomWidgets.LoadingDialog;
import tk.pankajb.Info.InfoQueryResponse.InfoResponse;
import tk.pankajb.Info.InformationQuery;
import tk.pankajb.Info.PosterActivity;

public class InfoActivity extends AppCompatActivity {

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

    private LoadingDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        initializeUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

        startInformationQueryThread();
    }

    private void startInformationQueryThread() {
        InformationQuery query = new InformationQuery(this);
        query.execute(getMovieIdFromIntent());
    }

    private void initializeUI() {
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

        setupToolbar();
        setupLoadingDialog();
    }

    private void setupLoadingDialog() {
        loading = new LoadingDialog(InfoActivity.this);
        loading.startLoading();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.mi_toolbar);
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
    }

    private String getMovieIdFromIntent() {
        return getIntent().getStringExtra("Id");
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