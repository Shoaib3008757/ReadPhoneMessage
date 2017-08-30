package ranglerz.readphonemessage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by User-10 on 29-Aug-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASE_NAME = "sms.db";
    private static final int DatabaseVersion = 1;
    private static final String NAME_OF_TABLE = "smstable";
    public static final String Col_1 = "id";
    public static final String Col_2 = "lat";
    public static final String Col_3 = "lng";
    public static final String Col_4 = "time";
    public static final String Col_5 = "date";

    String CREATE_TABLE_CALL = "CREATE TABLE " + NAME_OF_TABLE + "(" + Col_1 + " integer PRIMARY KEY AUTOINCREMENT," + Col_2 + " TEXT, " + Col_3 + " TEXT," + Col_4  + " TEXT, " + Col_5 + " TEXT " + ")";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DatabaseVersion);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CALL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_OF_TABLE);


    }

    //inserting post in databse
    public long insertingSMSInDatabaseTable(Helper helper) {
        long result;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col_2, helper.getLat());
        values.put(Col_3, helper.getLng());
        values.put(Col_4, helper.getTime());
        values.put(Col_5, helper.getDate());

        //inserting valuse into table columns
        result = db.insert(NAME_OF_TABLE, null, values);
        db.close();
        return result;

    }


    /* fetching records from Database Table*/
    public ArrayList<Helper> getAllRecord() {
        String query = "SELECT * FROM " + NAME_OF_TABLE;
        ArrayList<Helper> smsList = new ArrayList<Helper>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                String lat = c.getString(c.getColumnIndex(Col_2));
                String lng = c.getString(c.getColumnIndex(Col_3));
                String time = c.getString(c.getColumnIndex(Col_4));
                String date = c.getString(c.getColumnIndex(Col_5));
                try {

                    Helper postHelper = new Helper();
                    postHelper.setLat(lat);
                    postHelper.setLng(lng);
                    postHelper.setTime(time);
                    postHelper.setdate(date);

                    smsList.add(postHelper);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        db.close();
        return smsList;

    }
}
