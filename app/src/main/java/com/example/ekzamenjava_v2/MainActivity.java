package com.example.ekzamenjava_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.list);

        databaseHelper = new DatabaseHelper(getApplicationContext());

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // открываем подключение
//        db = databaseHelper.getReadableDatabase();
//
//        //получаем данные из бд в виде курсора
//        //userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE_POST, null);
//        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
//        // определяем, какие столбцы из курсора будут выводиться в ListView
//        //String[] headers = new String[] {DatabaseHelper.COLUMN_NAME_TEXT_POST, DatabaseHelper.COLUMN_DATA_POST};
//        String[] headers = new String[]{DatabaseHelper.COLUMN_LOGIN, DatabaseHelper.COLUMN_PASSWORD};
//        // создаем адаптер, передаем в него курсор
//        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
//                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
//        userList.setAdapter(userAdapter);
//
//    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, RegistationActivity.class);
        startActivity(intent);
    }
    public void addEntry(View view)
    {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}