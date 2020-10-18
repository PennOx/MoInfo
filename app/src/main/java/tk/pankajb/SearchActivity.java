package tk.pankajb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import tk.pankajb.CustomWidgets.LoadingDialog;
import tk.pankajb.Search.QueryRecyclerAdapter;
import tk.pankajb.Search.SearchQuery;
import tk.pankajb.Search.SearchQueryResponse.Search;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Search> list = null;

    private LoadingDialog loading;
    private Toolbar toolbar;

    public void setList(List<Search> list) {
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode == 400){
            Toast.makeText(this, data.getStringExtra("msg"),Toast.LENGTH_LONG).show();
        }
    }
}