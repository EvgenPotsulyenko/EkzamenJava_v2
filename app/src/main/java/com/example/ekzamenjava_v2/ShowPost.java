package com.example.ekzamenjava_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowPost extends AppCompatActivity {

    TextView PostName;
    TextView Text;
    TextView Author;
    TextView Data;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        PostName = findViewById(R.id.PostName);
        Text = findViewById(R.id.Text);
        Author = findViewById(R.id.Author);
        Data = findViewById(R.id.Data);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_POST + " where " +
                    DatabaseHelper.COLUMN_ID_POST + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();

            PostName.setText(userCursor.getString(1));
            Text.setText(userCursor.getString(3));
            Author.setText(userCursor.getString(2));
            Data.setText(userCursor.getString(4));

            userCursor.close();
        }
    }
    public void GoHome(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}