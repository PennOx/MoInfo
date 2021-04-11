package tk.pankajb.Info;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import tk.pankajb.Info.InfoQueryResponse.InfoResponse;
import tk.pankajb.InfoActivity;

public class InformationQuery extends AsyncTask<String, Void, InfoResponse> {

    private WeakReference<InfoActivity> weakReference;

    public InformationQuery(InfoActivity context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected InfoResponse doInBackground(String... strings) {

        String movieId = strings[0];

        if (isActivityAlive()) {
            try {

                HttpURLConnection connection = getNewMovieInformationQueryConnection(movieId);

                if (isConnectionResponseSuccess(connection)) {

                    InfoResponse response = getConnectionResponse(connection);

                    if (isResponseOK(response)) {
                        return response;
                    }
                }

            } catch (IOException e) {
                Log.e("Error:- ", e.getMessage());
            }
        }
        return null;
    }

    private boolean isResponseOK(InfoResponse response) {
        return response.getResponse().equals("True");
    }

    private InfoResponse getConnectionResponse(HttpURLConnection connection) throws IOException {
        String rawResponse = getConnectionResponseJSON(connection);
        InfoResponse infoResponse = getResponseObjectFromJSON(rawResponse);
        return infoResponse;
    }

    private InfoResponse getResponseObjectFromJSON(String rawResponse) {
        Gson gson = new Gson();
        InfoResponse response = gson.fromJson(rawResponse, InfoResponse.class);
        return response;
    }

    private String getConnectionResponseJSON(HttpURLConnection connection) throws IOException {
        Scanner scan = new Scanner(connection.getInputStream());
        String rawResponse = scan.nextLine();
        return rawResponse;
    }

    private boolean isConnectionResponseSuccess(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() <= 200;
    }

    private HttpURLConnection getNewMovieInformationQueryConnection(String movieId) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) getMovieInformationQueryURL(movieId).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        return connection;
    }

    private URL getMovieInformationQueryURL(String movieId) throws MalformedURLException {
        return new URL(String.format("https://www.omdbapi.com/?apikey=3b00e127&i=%s", movieId));
    }

    private boolean isActivityAlive() {
        return getContext() != null || getContext().isFinishing();
    }

    private InfoActivity getContext() {
        return weakReference.get();
    }

    @Override
    protected void onPostExecute(InfoResponse infoResponse) {
        super.onPostExecute(infoResponse);

        InfoActivity context = weakReference.get();

        if (infoResponse != null) {

            context.updateUI(infoResponse);
        } else {
            context.goneWrong("Results not found");
        }
    }
}
