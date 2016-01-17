package ru.skalix.thwidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class Receiver extends BroadcastReceiver {

    public Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String LOGTAG = "CON";
        if(intent.getAction().equals("ru.skalix.thwidget.receiver")){
            Log.d(LOGTAG, "Getting receiver action!");
            if(intent.getStringExtra("IP") != null) Connector.IP = intent.getStringExtra("IP");
            else Connector.IP = "127.0.0.1";
            Connector.PORT = intent.getIntExtra("PORT", 2007);
            Connector.UPD = intent.getIntExtra("UPDATE", 300000);
        }
        else if(intent.getAction().equals("ru.skalix.thwidget.force")){
            Log.d(LOGTAG, "Getting force action!");
            Connector.trg = intent.getBooleanExtra("TRG", false);
        }
        else if(intent.getAction().equals("ru.skalix.thwidget.get")){
            Log.d(LOGTAG, "Getting get action!");
            Connector.get_trg = true;
        }
        else{
            Log.d(LOGTAG, "Invalid action!");
        }
    }
}
