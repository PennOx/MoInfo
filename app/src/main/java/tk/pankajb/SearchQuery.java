package tk.pankajb;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import tk.pankajb.SearchQueryResponse.SearchResponse;

public class SearchQuery extends AsyncTask<Void,Void,Void> {

    private Context context;
    private String searchQuery;

    private URL url;
    private HttpURLConnection connection;

    public SearchQuery(Context context, String userInput) {
        this.context = context;
        this.searchQuery = userInput;

        try {
            url = new URL(String.format("http://www.omdbapi.com/?apikey=3b00e127&s=%s",searchQuery));
        } catch (MalformedURLException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (url==null){
            return null;
        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            if(connection.getResponseCode()<=200){

                String resJson = new Scanner(connection.getInputStream()).nextLine();

                SearchResponse response = new Gson().fromJson(resJson,SearchResponse.class);

                if (response.getResponse().equals("True")){

                }

            }else{
                Toast.makeText(context, connection.getResponseMessage(),Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
