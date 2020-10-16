package tk.pankajb;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import tk.pankajb.InfoQueryResponse.InfoResponse;

public class InformationQuery extends AsyncTask<String,Void, InfoResponse> {

    private WeakReference<MovieInformation> weakReference;

    private HttpURLConnection connection;
    private URL url;

    public InformationQuery(MovieInformation context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected InfoResponse doInBackground(String... strings) {

        String movieId = strings[0];

        MovieInformation context = weakReference.get();

        if (context != null || context.isFinishing()){
            try {
                url = new URL(String.format("https://www.omdbapi.com/?apikey=3b00e127&i=%s",movieId));
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();

                if (connection.getResponseCode() <= 200){

                    String apiRes = new Scanner(connection.getInputStream()).nextLine();

                    InfoResponse response = new Gson().fromJson(apiRes, InfoResponse.class);

                    if (response.getResponse().equals("True")){
                        return response;
                    }
                }

            } catch (IOException e) {
                Log.e("Error:- ",e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(InfoResponse infoResponse) {
        super.onPostExecute(infoResponse);

        MovieInformation context = weakReference.get();
        context.updateUI(infoResponse);
    }
}
