package com.example.qaunewsalerts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBNews extends SQLiteOpenHelper {
    private static int DB_VERSION=1;
    private static String DATABASE_NAME="News";
    private static String TABLE_NAME="FavoriteNews";
    public static String KEY_ID="ID";
    public static  String REG_ID="Registeration_id";
    public static String NEWS_TITLE = "NewsTitle";
    public static String NEWS_DESCRIPTION = "NewsDescription";
    public static String NEWS_IMAGE = "NewsImage";
    public static String READ_STATUS = "READ_STATUS";
    public static String FAVORITE_STATUS = "fStatus";
    // dont forget write this spaces
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + "  Integer PRIMARY KEY AUTOINCREMENT," + NEWS_TITLE+ " TEXT,"+ NEWS_DESCRIPTION+ " TEXT,"
            + NEWS_IMAGE + " TEXT," + FAVORITE_STATUS+" TEXT,"+ REG_ID+" TEXT," + READ_STATUS+" Boolean)";

    public DBNews(Context context) { super(context,DATABASE_NAME,null,DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    // create empty table
    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // enter your value
        for (int x = 1; x < 11; x++) {
            cv.put(KEY_ID, x);
            cv.put(FAVORITE_STATUS,"0");

            db.insert(TABLE_NAME,null, cv);
        }
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//        cv.put(FAVORITE_STATUS, true);
//        long status = db.insert(TABLE_NAME,null, cv);
//        for (int x = 1; x < 11; x++) {
//            cv.put(KEY_ID, x);
//            cv.put(FAVORITE_STATUS, "0");
//
//            db.insert(TABLE_NAME,null, cv);}
       // Log.d("InsertStatus", status+"");
    }

    // insert data into database
    public void insertIntoTheDatabase(String item_title, String item_description, String item_image, String id, String fav_status,String Reg_id ) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NEWS_TITLE, item_title);
        cv.put(NEWS_DESCRIPTION, item_description);
        cv.put(NEWS_IMAGE, item_image);
        cv.put(REG_ID,Reg_id);
        cv.put(KEY_ID, id);
        cv.put(FAVORITE_STATUS, fav_status);
        long Updid = db.insert(TABLE_NAME,null, cv);
        Log.d("FavDB ID", Updid+"");
    }

    public void insertUniNews(String item_title, String item_description, String item_image, String id, String fav_status,Boolean read_status) {
        SQLiteDatabase db;

        db = this.getWritableDatabase();

        Cursor cursor =readAllData();
        try {
            if(cursor!=null){
                while (cursor.moveToNext()) {
                    if(id!=cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.KEY_ID)))) {
                        ContentValues cv = new ContentValues();
                        cv.put(NEWS_TITLE, item_title);
                        cv.put(NEWS_DESCRIPTION, item_description);
                        cv.put(NEWS_IMAGE, item_image);
                        cv.put(KEY_ID, id);
                        cv.put(String.valueOf(READ_STATUS), read_status);
                        cv.put(FAVORITE_STATUS, fav_status);
                        long Updid = db.insert(TABLE_NAME,null, cv);
                        Log.d("FavDB ID", Updid+"");
                        break;
                    }
                }
            }

            else {
                ContentValues cv = new ContentValues();
                cv.put(NEWS_TITLE, item_title);
                cv.put(NEWS_DESCRIPTION, item_description);
                cv.put(NEWS_IMAGE, item_image);
                cv.put(KEY_ID, id);
                cv.put(String.valueOf(READ_STATUS), read_status);
                cv.put(FAVORITE_STATUS, fav_status);
                long Updid = db.insert(TABLE_NAME,null, cv);
                Log.d("FavDB ID", Updid+"");

            }} finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }


    }

    public Cursor readAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from  '"+TABLE_NAME+"'";
        return db.rawQuery(sql,null,null);
    }

    // read all data
    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID+"="+id+"";
        return db.rawQuery(sql,null,null);
    }

    public Cursor read_all_userbased_data(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + REG_ID+"="+userId+"";
        return db.rawQuery(sql,null,null);
    }

    // remove line from database
    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  "+ FAVORITE_STATUS+" ='0' WHERE "+KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove", String.valueOf(id));

    }

    // select all favorite list

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FAVORITE_STATUS+" ='1'";
        return db.rawQuery(sql,null,null);
    }

    public Cursor update_read_status(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  "+ READ_STATUS+" =true WHERE "+KEY_ID+"="+id+"";

        return db.rawQuery(sql,null,null);
    }


}