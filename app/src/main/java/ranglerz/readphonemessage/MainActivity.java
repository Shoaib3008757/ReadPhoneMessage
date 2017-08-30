package ranglerz.readphonemessage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    int PERMISSION_REQUEST_READ_CONTACTS = 777;
    int PERMISSION_REQUEST_RECEIVE_SMS = 888;

    ArrayList<Helper> messages;
    ArrayList<HashMap<String, String>> smsrecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int permissionCheckReceiveSMS= ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        messages = new ArrayList<Helper>();
        smsrecord = new ArrayList<>();

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            //showMessage();

            Intent i = new Intent(MainActivity.this, ReadMessage.class);
            //startService(i);

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},PERMISSION_REQUEST_READ_CONTACTS);
        }


        if (permissionCheckReceiveSMS == PackageManager.PERMISSION_GRANTED){
            //showMessage();

            Intent i = new Intent(MainActivity.this, ReadMessage.class);
           // startService(i);

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},PERMISSION_REQUEST_RECEIVE_SMS);
        }

        //getAllMessage();

        startMapActivty();


    }//end of onCreate

    public void showMessage(){

        String[] phoneNumber = new String[] { "+923466770839" };
        Cursor cursor1 = getContentResolver().query(Uri.parse("content://sms/inbox"), new String[] { "_id", "thread_id", "address", "person", "date","body", "type" }, "address=?", phoneNumber, null);
        StringBuffer msgData = new StringBuffer();
        if (cursor1.moveToFirst()) {
            do {


                for(int idx=0;idx<cursor1.getColumnCount();idx++)
                {
                    msgData.append(" " + cursor1.getColumnName(idx) + ":" + cursor1.getString(idx));

                    Toast.makeText(this, "Message: " + cursor1.getString(cursor1.getColumnIndex("body")), Toast.LENGTH_SHORT).show();
                    //Log.e("TAG: ", "MESSAGE: " + cursor1.getString(cursor1.getColumnIndex("body")));
                    //cursor1.getString(cursor1.getColumnIndex("body"));

                }
                Log.e("TAG: ", "MESSAGE: " + cursor1.getString(cursor1.getColumnIndex("body")));
                Log.e("TAG: ", "Time: " + cursor1.getString(cursor1.getColumnIndex("date")));

            } while (cursor1.moveToNext());



        } else {

            String str = Arrays.toString(phoneNumber);
            Toast.makeText(this, "Messsage: " + str , Toast.LENGTH_SHORT).show();
            Log.e("TAG: ", "MESSAGE: " + str);

        }

    }


    public void getAllMessage(){


        DBHelper dh = new DBHelper(MainActivity.this);
         messages = new ArrayList<Helper>();

        messages = dh.getAllRecord();

        for (int i = 0; i < messages.size(); i++){

            Helper helper = new Helper();
             helper = messages.get(i);
            String lat = helper.getLat();
            String lng = helper.getLng();

            Log.e("TAG", "the Message Lat: " + lat);
            Log.e("TAG", "the Message Lnt: " + lng);

            HashMap<String, String> contact = new HashMap<>();

            contact.put("lat", lat);
            contact.put("lng", lng);

            smsrecord.add(contact);
        }

    }//end of getAllMessage



    public void startMapActivty(){

        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(i);
        finish();
    }



}
