package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import tk.pankajb.SearchQueryResponse.Search;

public class SearchResult extends AppCompatActivity {

    private String query;
    private RecyclerView recyclerView;
    private QueryRecyclerAdapter adapter;

    List<Search> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        query = getIntent().getStringExtra("movieName");
        recyclerView = findViewById(R.id.search_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchQuery queryThread = new SearchQuery(SearchResult.this);
        queryThread.execute(query);
    }

    public void updateData() {
        adapter = new QueryRecyclerAdapter(SearchResult.this,list);
        recyclerView.setAdapter(adapter);
    }
}