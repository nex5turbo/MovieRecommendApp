package wonyong.by.movierecommend;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {
    private  static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private  static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mContext;

    private class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBase.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBase.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mContext = context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    public long insertColumn(String movienm, String movienmen, int opendt, String prdtstatnm, String repnationnm, String repgenrenm){
        ContentValues values = new ContentValues();
        values.put(DataBase.CreateDB.MOVIENM, movienm);
        values.put(DataBase.CreateDB.MOVIENMEN, movienmen);
        values.put(DataBase.CreateDB.OPENDT, opendt);
        values.put(DataBase.CreateDB.PRDTSTATNM, prdtstatnm);
        values.put(DataBase.CreateDB.REPNATIONNM, repnationnm);
        values.put(DataBase.CreateDB.REPGENRENM, repgenrenm);
        return mDB.insert(DataBase.CreateDB._TABLENAME0, null, values);
    }

}
