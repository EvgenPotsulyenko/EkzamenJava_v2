package com.example.ekzamenjava_v2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "userstore.db"; // название бд
    private static final int SCHEMA = 3; // версия базы данных
    static final String TABLE = "users"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID = "_id";
    static final String COLUMN_LOGIN = "login";
    static final String COLUMN_PASSWORD = "password";

    static final String TABLE_POST = "posts";
    static final String COLUMN_ID_POST = "_id";
    static final String COLUMN_NAME_TEXT_POST = "name_text";
    static final String COLUMN_DATA_POST = "data";
    static final String COLUMN_TEXT_POST = "text";
    static final String COLUMN_NAME_AUTHOR_POST = "name_author";
    private Context myContext;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LOGIN
                + " TEXT, " + COLUMN_PASSWORD + " TEXT);");
        // добавление начальных данных
//        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_LOGIN
//                + ", " + COLUMN_YEAR  + ") VALUES ('Том Смит', 1981);");


        db.execSQL("CREATE TABLE posts (" + COLUMN_ID_POST
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_TEXT_POST
                + " TEXT, " + COLUMN_DATA_POST + " TEXT, " + COLUMN_TEXT_POST + " TEXT, " + COLUMN_NAME_AUTHOR_POST + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE_POST +" (" + COLUMN_NAME_TEXT_POST
                + ", " + COLUMN_DATA_POST  + ", " + COLUMN_TEXT_POST  + ", " + COLUMN_NAME_AUTHOR_POST  + ") VALUES ('Ananas', 2010,'primer','primer');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_POST);
        onCreate(db);
    }
}

//public class DatabaseHelper extends SQLiteOpenHelper {
//    private static String DB_PATH; // полный путь к базе данных
//    private static String DB_NAME = "EkzamenJava.db";
//    private static final int SCHEMA = 3; // версия базы данных
//    static final String TABLE = "users"; // название таблицы в бд
//    // названия столбцов
//    static final String COLUMN_ID = "_id";
//    static final String COLUMN_LOGIN = "login";
//    static final String COLUMN_PASSWORD = "password";
//    // название таблицы в бд
//    static final String TABLE_POST = "posts";
//    static final String COLUMN_ID_POST = "_id";
//    static final String COLUMN_NAME_TEXT_POST = "name_text";
//    static final String COLUMN_DATA_POST = "data";
//    static final String COLUMN_TEXT_POST = "text";
//    static final String COLUMN_NAME_AUTHOR_POST = "name_author";
//    private Context myContext;
//
//    DatabaseHelper(Context context) {
//        super(context, DB_NAME, null, SCHEMA);
//        this.myContext=context;
//        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) { }
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) { }
//
//    void create_db(){
//
//        File file = new File(DB_PATH);
//        if (!file.exists()) {
//            //получаем локальную бд как поток
//            try(InputStream myInput = myContext.getAssets().open(DB_NAME);
//                // Открываем пустую бд
//                OutputStream myOutput = new FileOutputStream(DB_PATH)) {
//                // побайтово копируем данные
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = myInput.read(buffer)) > 0) {
//                    myOutput.write(buffer, 0, length);
//                }
//                myOutput.flush();
//            }
//            catch(IOException ex){
//                Log.d("DatabaseHelper", ex.getMessage());
//            }
//        }
//    }
//    public SQLiteDatabase open()throws SQLException {
//
//        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
//    }
//}
