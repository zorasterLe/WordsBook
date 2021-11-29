package com.example.wordsbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.wordsbook/databases/MyWordsBook.db", null, SQLiteDatabase.OPEN_READWRITE);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final Intent intent = getIntent();
        final EditText word = (EditText) findViewById(R.id.WordToEdit);
        final EditText detail = (EditText) findViewById(R.id.DetailToEdit);
        word.setHint(intent.getStringExtra("word"));
        detail.setHint(intent.getStringExtra("detail"));


        Button EditButton = (Button) findViewById(R.id.EditActivityButton);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EditDetail = detail.getText().toString();
                String EditWord = word.getText().toString();
                db.execSQL("update dict set detail = ? where word = ?",new String[]{EditDetail,intent.getStringExtra("word")});
                db.execSQL("update dict set word = ? where detail = ?",new String[]{EditWord,EditDetail});
                Toast.makeText(EditActivity.this, "修改" + intent.getStringExtra("word") + "成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}