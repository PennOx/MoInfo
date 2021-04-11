package tk.pankajb.Search;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import tk.pankajb.Search.SearchQueryResponse.Search;
import tk.pankajb.Search.SearchQueryResponse.SearchResponse;
import tk.pankajb.SearchActivity;

public class SearchQuery extends AsyncTask<String, Void, List<Search>> {

    private final WeakReference<SearchActivity> weakReference;

    public SearchQuery(SearchActivity context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected List<Search> doInBackground(String... strings) {

        if (activityIsAlive()) {
            try {
                String movieName = strings[0];
                HttpURLConnection connection = getNewMovieSearchConnection(movieName);

                if (isConnectionResponseSuccess(connection)) {

                    SearchResponse response = getConnectionResponse(connection);

                    if (isResponseOK(response)) {
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

        if (!searchIsEmpty(searches)) {
            returnSearchData(searches);
        } else {
            notifyNoResult();
        }
    }

    private boolean isResponseOK(SearchResponse response) {
        return response.getResponse().equals("True");
    }

    private SearchResponse getConnectionResponse(HttpURLConnection connection) throws IOException {

        String rawResponse = getConnectionResponseJSON(connection);
        SearchResponse response = getResponseObjectFromJSON(rawResponse);
        return response;
    }

    private SearchResponse getResponseObjectFromJSON(String rawResponse) {
        Gson gson = new Gson();
        SearchResponse searchResponse = gson.fromJson(rawResponse, SearchResponse.class);
        return searchResponse;
    }

    private String getConnectionResponseJSON(HttpURLConnection connection) throws IOException {
        Scanner scan = new Scanner(connection.getInputStream());
        String rawResponse = scan.nextLine();
        return rawResponse;
    }

    private boolean isConnectionResponseSuccess(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() <= 200;
    }

    private HttpURLConnection getNewMovieSearchConnection(String movieName) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) getMovieRequestURL(movieName).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    private URL getMovieRequestURL(String movieName) throws MalformedURLException {
        return new URL(String.format("https://www.omdbapi.com/?apikey=3b00e127&s=%s", movieName));
    }

    private SearchActivity getContext() {
        return weakReference.get();
    }

    private void notifyNoResult() {
        getContext().noResult();
    }

    private void returnSearchData(List<Search> searches) {
        getContext().setList(searches);
        getContext().updateData();
    }

    private boolean searchIsEmpty(List<Search> searches) {
        return searches == null || searches.isEmpty();
    }

    private boolean activityIsAlive() {
        return getContext() != null || getContext().isFinishing();
    }
}
