package tk.pankajb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInputEditText;
    private final int SEARCH_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();

    }

    private void initializeUI() {
        setupMovieEditText();
    }

    private void setupMovieEditText() {
        nameInputEditText = findViewById(R.id.m_name);
    }

    public void search(View view) { // Click listener for search Button

        getAndSendMovieNameToSearchActivity();

    }

    private void getAndSendMovieNameToSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("movieName", getMovieNameFromUI());
        startActivityForResult(intent, SEARCH_REQUEST_CODE);
    }

    private String getMovieNameFromUI() {
        String userInput = nameInputEditText.getText().toString().trim();
        userInput = userInput.replace(" ", "%20");
        return userInput;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!isSearchResultSuccess(requestCode, resultCode)) {
            nameInputEditText.setError("No result found");
        }

    }

    private boolean isSearchResultSuccess(int requestCode, int resultCode) {
        return requestCode == SEARCH_REQUEST_CODE && resultCode != 404;
    }

}