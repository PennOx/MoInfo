package tk.pankajb.Info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tk.pankajb.R;

public class PosterActivity extends AppCompatActivity {
    private String imageUrl;
    private ImageView poster;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        toolbar = findViewById(R.id.toolbar_poster);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MoInfo");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        poster = findViewById(R.id.poster_only);
        imageUrl = getIntent().getStringExtra("imageUrl");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(PosterActivity.this).load(imageUrl).placeholder(R.drawable.movie_default).into(poster);
        }

    }
}