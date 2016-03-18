package com.ketan;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
EditText SMSBODY;
    Button Send;
    String sent = "SMS_SENT";
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == Activity.RESULT_OK)
            {
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
//

            }
            else
            {
//
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
//
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SMSBODY= (EditText) findViewById(R.id.SMSBODY);
        Send= (Button) findViewById(R.id.Send);
        registerReceiver(receiver,new IntentFilter(sent));
Send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(SMSBODY.getText().length()!=0)
        {
            SendSMS();
        }
    }
});
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void SendSMS(){
        try {
            Intent mIntent=new Intent(sent);
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts =smsManager.divideMessage(SMSBODY.getText().toString());
            int part=parts.size();
            ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
            for (int i = 0; i < part; i++) {
                sentIntents.add(PendingIntent.getBroadcast(MainActivity.this, 0,mIntent, 0));
            }



                smsManager.sendMultipartTextMessage("Your Number",null,parts,sentIntents,null);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Exception","true");
        }
    }
}
