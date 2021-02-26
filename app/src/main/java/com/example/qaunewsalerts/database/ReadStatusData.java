package com.example.qaunewsalerts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReadStatusData extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "NewsForReadStatus";
    private static String TABLE_NAME = "ReadNews";
    public static String KEY_ID = "ID";
    public static String NEWS_ID = "NewsId";
    public static String NEWS_TITLE = "NewsTitle";
    public static String NEWS_DESCRIPTION = "NewsDescription";
    public static String NEWS_IMAGE = "NewsImage";
    public static String READ_STATUS = "READ_STATUS";
    public static String FAVORITE_STATUS = "fStatus";

    // dont forget write this spaces
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + "  Integer PRIMARY KEY AUTOINCREMENT,"+ NEWS_ID + " TEXT,"  + NEWS_TITLE + " TEXT," + NEWS_DESCRIPTION + " TEXT,"
            + NEWS_IMAGE + " TEXT," + FAVORITE_STATUS + " TEXT," + READ_STATUS + " Boolean)";

    public ReadStatusData(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID + "=" + id + "";
        return db.rawQuery(sql, null, null);
    }


    public void insertUniNews(String item_title, String item_description, String item_image, String id, Boolean read_status) {
        //SQLiteDatabase db;
        Cursor cursor = readAllData();
        SQLiteDatabase db = getReadableDatabase();

        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Log.d("Ids", id+"----"+cursor.getColumnIndex(NEWS_ID));

                    if (!id.equals(cursor.getString(cursor.getColumnIndex(NEWS_ID)))) {
                        ContentValues cv = new ContentValues();
                        cv.put(NEWS_ID, id);
                        cv.put(NEWS_TITLE, item_title);
                        cv.put(NEWS_DESCRIPTION, item_description);
                        cv.put(NEWS_IMAGE, item_image);
                        cv.put(String.valueOf(READ_STATUS), read_status);
                        long Updid = db.insert(TABLE_NAME, null, cv);
                        Log.d("FavDB ID", Updid + "");
                        break;
                    }
                }
            } else {
                Log.d("Ids else", id);
                ContentValues cv = new ContentValues();
                cv.put(NEWS_ID, id);
                cv.put(NEWS_TITLE, item_title);
                cv.put(NEWS_DESCRIPTION, item_description);
                cv.put(NEWS_IMAGE, item_image);
                cv.put(String.valueOf(READ_STATUS), read_status);

                long Updid = db.insert(TABLE_NAME, null, cv);
                Log.d("FavDB ID", Updid + "");

            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }


    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from  '" + TABLE_NAME + "'";
        return db.rawQuery(sql, null, null);
    }


    public Cursor update_read_status(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  " + READ_STATUS + " =true WHERE " + KEY_ID + "=" + id + "";

        return db.rawQuery(sql, null, null);
    }


}
