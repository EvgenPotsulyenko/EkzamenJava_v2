package com.example.ekzamenjava_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistationActivity extends AppCompatActivity {
    EditText nameBox;
    EditText yearBox;
    Button delButton;
    Button saveButton;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        nameBox = findViewById(R.id.name);
        yearBox = findViewById(R.id.year);
        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getReadableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            yearBox.setText(String.valueOf(userCursor.getInt(2)));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }
    public void save(View view){
        boolean CheckEntry = true;
        db = sqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DatabaseHelper.TABLE, null);
        // Создаем массив String для сравнение с введеным значением login и password
        if(c!=null&&c. moveToFirst()) {
            do {
                String password = c.getString(c.getColumnIndexOrThrow("password"));
                String login = c.getString(c.getColumnIndexOrThrow("login"));
                // Сравниваем значения
                if (login.equals(nameBox.getText().toString()) || password.equals(yearBox.getText().toString())) {
                    CheckEntry = false;
                }

            } while (c.moveToNext());
        }
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_LOGIN, nameBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_PASSWORD, yearBox.getText().toString());

        if (userId > 0 && CheckEntry == true) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null);
        } else if(CheckEntry == true) {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }
        else
        {
            Toast toast = Toast.makeText(this, "Не удалось добавить данные,попробуйте другой логин или пароль",Toast.LENGTH_LONG);
            toast.show();
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}