package com.example.ekzamenjava_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class EntryActivity extends AppCompatActivity {
    ListView userList;
    EditText nameBox;
    EditText yearBox;
    SQLiteDatabase db;
    DatabaseHelper databaseHelper;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        nameBox = findViewById(R.id.name);
        yearBox = findViewById(R.id.year);
        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RegistationActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        //databaseHelper.create_db();
    }
    public void Input(View view) {
        boolean a = false;
        db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        // Создаем массив String для сравнение с введеным значением login и password
        if(c!=null&&c. moveToFirst()){
            do{
                String password  = c.getString(c.getColumnIndexOrThrow ("password"));
                String login  = c.getString(c.getColumnIndexOrThrow ("login"));
                // Сравниваем значения
                if(login.equals(nameBox.getText().toString()) && password.equals(yearBox.getText().toString()))
                {
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    a = true;
                    break;
                }

            }while(c.moveToNext());
            if(a == false)
            {
                Toast toast = Toast.makeText(this, "Вы не Вошли!",Toast.LENGTH_LONG);
                toast.show();
            }
        }

    }

    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}