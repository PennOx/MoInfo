package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MovieInformation extends AppCompatActivity {

    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        movieId = getIntent().getStringExtra("Id");
    }
}