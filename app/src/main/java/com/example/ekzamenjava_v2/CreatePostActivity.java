package com.example.ekzamenjava_v2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;

public class CreatePostActivity extends AppCompatActivity {

    EditText nameBox;
    EditText authorBox;
    EditText textBox;

    Button delButton;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long postId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        nameBox = findViewById(R.id.name);
        authorBox = findViewById(R.id.author);
        textBox = findViewById(R.id.text);
        delButton = findViewById(R.id.deleteButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getReadableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getLong("id");
        }
        // если 0, то добавление
        if (postId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_POST + " where " +
                    DatabaseHelper.COLUMN_ID_POST + "=?", new String[]{String.valueOf(postId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            authorBox.setText(String.valueOf(userCursor.getInt(2)));
            textBox.setText(String.valueOf(userCursor.getInt(3)));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Save(View view)
    {
        LocalDate currentDate = LocalDate.now();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME_TEXT_POST, nameBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_NAME_AUTHOR_POST, authorBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_TEXT_POST, textBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_DATA_POST, currentDate.toString());

        if (postId > 0) {
            db.update(DatabaseHelper.TABLE_POST, cv, DatabaseHelper.COLUMN_ID_POST + "=" + postId, null);
        } else {
            db.insert(DatabaseHelper.TABLE_POST, null, cv);
        }
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}