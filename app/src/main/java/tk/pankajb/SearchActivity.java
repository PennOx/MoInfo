package tk.pankajb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        initializeUI();

    }

    @Override
    protected void onStart() {
        super.onStart();

        startSearchThreadForMovie(getIntentMovieName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 400) {
            Toast.makeText(this, data.getStringExtra("msg"), Toast.LENGTH_LONG).show();
        }
    }

    private void startSearchThreadForMovie(String intentMovieName) {
        SearchQuery queryThread = new SearchQuery(SearchActivity.this);
        queryThread.execute(intentMovieName);
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

    private String getIntentMovieName() {
        return getIntent().getStringExtra("movieName");
    }

    private void initializeUI() {
        setupToolbar();
        setupLoadingProgress();
        setupMoviesRecycler();
    }

    private void setupMoviesRecycler() {
        recyclerView = findViewById(R.id.search_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupLoadingProgress() {
        loading = new LoadingDialog(SearchActivity.this);
        loading.startLoading();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }
}