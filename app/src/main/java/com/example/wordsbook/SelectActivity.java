package com.example.wordsbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent  = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Button editBtn = (Button) findViewById(R.id.EditButton);
        Button deleteBtn = (Button) findViewById(R.id.DeleteButton);
        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(SelectActivity.this, EditActivity.class);
                editIntent.putExtra("word",intent.getStringExtra("word"));
                editIntent.putExtra("detail",intent.getStringExtra("detail"));
                startActivity(editIntent);
                finish();
            }
        });
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("action","2");
                intent.putExtra("word",intent.getStringExtra("word"));
                setResult(1,intent);
                finish();
            }
        });
    }
}