package theo.tziomakas.news.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by theodosiostziomakas on 29/01/2018.
 */

public class FavouriteDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "favourite.db";

    private static final int DATABASE_VERSION = 8;

    public FavouriteDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
          /*
         * This String will contain a simple SQL statement that will create a table that will
         * cache our weather data.
         */
        final String SQL_CREATE_FAVOURITE_TABLE =

                "CREATE TABLE " + FavouriteContract.FavouriteEntry.TABLE_NAME + " (" +

                        FavouriteContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "      +

                        FavouriteContract.FavouriteEntry.COLUMN_NEWS_AUTHOR + " TEXT NOT NULL, "     +

                        FavouriteContract.FavouriteEntry.COLUMN_NEWS_TITLE + " TEXT NOT NULL,"            +

                        FavouriteContract.FavouriteEntry.COLUMN_NEWS_DESCRIPTION + " TEXT NOT NULL, "        +

                        FavouriteContract.FavouriteEntry.COLUMN_NEWS_URL + " TEXT NOT NULL, "    +

                        FavouriteContract.FavouriteEntry.COLUMN_NEWS_URL_TO_IMAGE + " TEXT NOT NULL, "    +

                        FavouriteContract.FavouriteEntry.COLUMN_NEWS_PUBLISHED_AT + " TEXT NOT NULL);";



        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.FavouriteEntry.TABLE_NAME);
        onCreate(db);

    }

}
