package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import tk.pankajb.SearchQueryResponse.Search;

public class SearchResult extends AppCompatActivity {

    private String query;

    List<Search> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        query = getIntent().getStringExtra("movieName");

        SearchQuery queryThread = new SearchQuery(SearchResult.this);
        queryThread.execute(query);
    }

    public void updateData() {


    }
}