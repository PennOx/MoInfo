package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SearchResult extends AppCompatActivity {

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        query = getIntent().getStringExtra("movieName");

        SearchQuery queryThread = new SearchQuery(SearchResult.this);
        queryThread.execute(query);

    }
}