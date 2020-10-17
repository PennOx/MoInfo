package tk.pankajb.Info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tk.pankajb.R;

public class PosterActivity extends AppCompatActivity {
    private String imageUrl;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        poster = findViewById(R.id.poster_only);
        imageUrl = getIntent().getExtras().toString();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(PosterActivity.this).load(imageUrl).into(poster);
        }
    }
}