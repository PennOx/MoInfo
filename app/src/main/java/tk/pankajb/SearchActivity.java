package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import tk.pankajb.Search.QueryRecyclerAdapter;
import tk.pankajb.Search.SearchQuery;
import tk.pankajb.Search.SearchQueryResponse.Search;

public class SearchActivity extends AppCompatActivity {

    private String query;
    private RecyclerView recyclerView;
    private QueryRecyclerAdapter adapter;

    private List<Search> list = null;

    public void setList(List<Search> list) {
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        query = getIntent().getStringExtra("movieName");
        recyclerView = findViewById(R.id.search_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchQuery queryThread = new SearchQuery(SearchActivity.this);
        queryThread.execute(query);
    }

    public void updateData() {
        adapter = new QueryRecyclerAdapter(SearchActivity.this,list);
        recyclerView.setAdapter(adapter);
    }
}