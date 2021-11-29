package com.example.wordsbook;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MyDatabaseHelper dbHelper;
    Button insert = null;
    Button search = null;
    TextView wordTextView;
    TextView detailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this, "MyWordsBook.db", 1);
        insert = (Button) findViewById(R.id.insert);
        search = (Button) findViewById(R.id.search);
        wordTextView = (TextView) findViewById(R.id.word);
        detailTextView = (TextView) findViewById(R.id.detail);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String word = wordTextView.getText().toString();
                String detail = detailTextView.getText().toString();
                wordTextView.setText("");
                detailTextView.setText("");
                dbHelper.insertData(dbHelper.getReadableDatabase(), word, detail);
                Toast.makeText(MainActivity.this, "添加生词成功", Toast.LENGTH_LONG).show();
            }
        });
        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String key = ((TextView) findViewById(R.id.key)).getText().toString();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);

            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null)
            dbHelper.close();
    }
}