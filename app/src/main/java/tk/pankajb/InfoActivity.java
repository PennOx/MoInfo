package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tk.pankajb.Info.InformationQuery;
import tk.pankajb.Info.InfoQueryResponse.InfoResponse;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        movieId = getIntent().getStringExtra("Id");
        name = findViewById(R.id.mi_name);
        genre = findViewById(R.id.mi_genre);
        year = findViewById(R.id.mi_year);
        language = findViewById(R.id.mi_language);
        director = findViewById(R.id.mi_director);
        plot = findViewById(R.id.mi_description);
        writer = findViewById(R.id.mi_writer);
        country = findViewById(R.id.mi_country);
        rating = findViewById(R.id.mi_rating);

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

        if (response.getPoster() != null) {
            Glide.with(this).load(response.getPoster()).into(poster);
        }
    }
}