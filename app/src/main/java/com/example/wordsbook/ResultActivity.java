package com.example.wordsbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    private SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.wordsbook/databases/MyWordsBook.db", null, SQLiteDatabase.OPEN_READWRITE);
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        showResult();
        Button back = (Button) findViewById(R.id.back_button);
        assert back != null;
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    protected void showResult(){
        ListView listView = (ListView) findViewById(R.id.show);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        Cursor cursor = db.rawQuery("select*from dict where word like ?", new String[]{ "%" + key + "%"});
        List<Map<String, String>> list = converCursorToList(cursor);
        SimpleAdapter adapter = new SimpleAdapter(ResultActivity.this, list,
                R.layout.line, new String[] { "word", "detail", }, new int[] {
                R.id.word, R.id.detail });
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter=parent.getAdapter();
                Map<String,String> map=(Map<String, String>) adapter.getItem(position);
                Intent intent = new Intent(ResultActivity.this,SelectActivity.class);
                String words = map.get("word");
                String detail = map.get("detail");
                intent.putExtra("word",words);
                intent.putExtra("detail",detail);
                startActivityForResult(intent,1);
                return false;
            }
        });
    }

    protected ArrayList<Map<String, String>> converCursorToList(Cursor cursor){
        ArrayList<Map<String, String>> result = new  ArrayList<Map<String, String>>();
        while (cursor.moveToNext()){
            Map<String, String> map = new HashMap<String, String>();
            map.put("word",cursor.getString(1));
            map.put("detail", cursor.getString(2));
            result.add(map);
        }
        return result;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (Integer.valueOf(data.getStringExtra("action")) == 2) {
                db.delete("dict", "word=?", new String[]{data.getStringExtra("word")});
                Toast.makeText(this,"删除" + data.getStringExtra("word") + "成功", Toast.LENGTH_SHORT).show();

            }
        }
        showResult();
    }
}