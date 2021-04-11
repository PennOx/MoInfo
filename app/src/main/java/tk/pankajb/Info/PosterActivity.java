package tk.pankajb.Info;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import tk.pankajb.R;

public class PosterActivity extends AppCompatActivity {
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        initializeUI();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isIntentHasImageUrl()) {
            placeImageInImageView();
        }
    }

    private void placeImageInImageView() {
        Glide.with(PosterActivity.this).load(getImageUrlFromIntent())
                .placeholder(R.drawable.movie_default).into(poster);
    }

    private boolean isIntentHasImageUrl() {
        String imageUrl = getImageUrlFromIntent();
        return imageUrl != null && !imageUrl.isEmpty();
    }

    private String getImageUrlFromIntent() {
        return getIntent().getStringExtra("imageUrl");
    }

    private void initializeUI() {
        poster = findViewById(R.id.poster_only);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_poster);
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
    }
}