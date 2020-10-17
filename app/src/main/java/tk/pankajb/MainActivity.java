package tk.pankajb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText nameInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInputEditText = findViewById(R.id.m_name);

    }

    public void search(View view) { // Click listener for search Button

        String userInput = nameInputEditText.getText().toString().trim();
        userInput = userInput.replace(" ", "%20");

        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("movieName", userInput);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 404){
            nameInputEditText.setError("No result found");
        }

    }
}