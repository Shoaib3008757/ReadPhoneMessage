package ranglerz.readphonemessage;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class ReadMessage extends Service {

    int PERMISSION_REQUEST_READ_CONTACTS = 777;

    private static int SplashScreenTimeOut = 3000;//3 seconds8
    private int timer = 3;
    Handler mHandler;



    public ReadMessage() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        useHandler();
        //showMessage();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);




    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


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



    //Thread for starting mainActivity
    private Runnable mRunnableStartMainActivity = new Runnable() {
        @Override
        public void run() {
            Log.d("Handler", " Calls");
            timer--;
            mHandler = new Handler();
            mHandler.postDelayed(this, 1000);

            if (timer == 2) {
                //loading.setText("Loading...");
            }
            if (timer == 1) {
                //loading.setText("Loading.");
            }
            if (timer == 0) {

                showMessage();

                //timer = 100;

            }
        }
    };


    //handler for the starign activity
    Handler newHandler;
    public void useHandler(){

        newHandler = new Handler();
        newHandler.postDelayed(mRunnableStartMainActivity, 1000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnableStartMainActivity);
    }
}
