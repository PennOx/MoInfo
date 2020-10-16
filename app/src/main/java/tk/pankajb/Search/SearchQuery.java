package tk.pankajb.Search;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import tk.pankajb.SearchActivity;
import tk.pankajb.Search.SearchQueryResponse.Search;
import tk.pankajb.Search.SearchQueryResponse.SearchResponse;

public class SearchQuery extends AsyncTask<String, Void, List<Search>> {

    private WeakReference<SearchActivity> weakReference;

    private HttpURLConnection connection;
    private URL url;


    public SearchQuery(SearchActivity context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected List<Search> doInBackground(String... strings) {

        String movieName = strings[0];
        SearchActivity context = weakReference.get();

        if (context != null || context.isFinishing()) {
            try {
                url = new URL(String.format("https://www.omdbapi.com/?apikey=3b00e127&s=%s", movieName));
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();

                if (connection.getResponseCode() <= 200) {

                    String apiRes = new Scanner(connection.getInputStream()).nextLine();
                    SearchResponse response = new Gson().fromJson(apiRes, SearchResponse.class);

                    if (response.getResponse().equals("True")) {
                        return response.getSearch();
                    }
                }
            } catch (IOException e) {
                Log.e("Error:- ", e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Search> searches) {
        super.onPostExecute(searches);

        SearchActivity context = weakReference.get();

        context.setList(searches);
        context.updateData();
    }
}
