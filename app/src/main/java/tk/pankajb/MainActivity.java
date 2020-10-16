package tk.pankajb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText nameInputEditText; //Edit text object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInputEditText = findViewById(R.id.m_name);

    }

    public void search(View view){
        String userInput = nameInputEditText.getText().toString().trim();
        userInput = userInput.replace(" ","%20");
        Intent intent = new Intent(MainActivity.this,SearchResult.class);
        intent.putExtra("movieName",userInput);
        startActivity(intent);

    }

}