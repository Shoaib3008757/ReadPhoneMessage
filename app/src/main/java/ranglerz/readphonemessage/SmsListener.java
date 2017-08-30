package ranglerz.readphonemessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by User-10 on 29-Aug-17.
 */
public class SmsListener  extends BroadcastReceiver {
    // SmsManager class is responsible for all SMS related actions
    final SmsManager sms = SmsManager.getDefault();
    public void onReceive(Context context, Intent intent) {
        // Get the SMS message received
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                // A PDU is a "protocol data unit". This is the industrial standard for SMS message
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    // This will create an SmsMessage object from the received pdu
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    // Get sender phone number
                    String phoneNumber = sms.getDisplayOriginatingAddress();
                    String sender = phoneNumber;
                    String message = sms.getDisplayMessageBody();
                    String formattedText = String.format(sender, message);

                    Log.e("TAG", "The Message Is: " + message);
                    Log.e("TAG", "The Sender Is: " + sender);
                    // Display the SMS message in a Toast
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();


                    if (sender.equals("+923030434420")) {


                        String[] lines = message.split("\\n");

                        // String splilted[]  = message.split("\\s+");


                        Log.e("Tag", "spilted string1: " + lines[0]);
                        Log.e("Tag", "spilted string1: " + lines[1]);

                        String lat = lines[0].toString();
                        String lng = lines[1].toString();

                        String latSplit[] = lat.split("\\s+");
                        String latLeft = latSplit[0];
                        String latRight = latSplit[1];

                        String lngSplit[] = lng.split("\\s+");
                        String lngLeft = lngSplit[0];
                        String lngRight = lngSplit[1];


                        Log.e("Tag", "lat Word: " + latLeft);
                        Log.e("Tag", "Lat Number: " + latRight);
                        Log.e("Tag", "Lng Word: " + lngLeft);
                        Log.e("Tag", "Lng Number: " + lngRight);


                        Helper helper = new Helper();
                        helper.setLat(latRight);
                        helper.setLng(lngRight);

                        DBHelper dbHelper = new DBHelper(context);
                        long isInserted = dbHelper.insertingSMSInDatabaseTable(helper);

                        if (isInserted > -1) {
                            Log.e("TAG", "Inserted in Database");
                        }


                        // String message2 = message.trim();

                        // Log.e("Tag", "spilted string1: " + splilted[0]);
                        //Log.e("Tag", "spilted string1: " + splilted[1]);
                        // Log.e("Tag", "Trim Message: " + message2);

                    }


                    //MainActivity inst = MainActivity.instance();
                    //inst.updateList(formattedText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}