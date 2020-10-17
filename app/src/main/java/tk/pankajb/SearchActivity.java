package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import tk.pankajb.CustomWidgets.LoadingDialog;
import tk.pankajb.Search.QueryRecyclerAdapter;
import tk.pankajb.Search.SearchQuery;
import tk.pankajb.Search.SearchQueryResponse.Search;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Search> list = null;

    private LoadingDialog loading;

    public void setList(List<Search> list) {
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        loading = new LoadingDialog(SearchActivity.this);
        loading.startLoading();

        String query = getIntent().getStringExtra("movieName");
        recyclerView = findViewById(R.id.search_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchQuery queryThread = new SearchQuery(SearchActivity.this);
        queryThread.execute(query);
    }

    public void updateData() {
        QueryRecyclerAdapter adapter = new QueryRecyclerAdapter(SearchActivity.this, list);
        recyclerView.setAdapter(adapter);
        loading.stopLoading();
    }

    public void noResult() {
        loading.stopLoading();
        setResult(404);
        finish();
    }
}