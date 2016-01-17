package ru.skalix.thwidget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Connector extends Service {

    private TCPClient client = null;
    private Thread conn = null;
    private Timer tmr = null;
    private SharedPreferences sp;

    static boolean trg = true;
    static boolean get_trg = false;

    static String IP = "127.0.0.1";
    static int PORT = 2007;
    static int UPD = 300000;

    private String LOGTAG = "CON";

    public Connector() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        conn = new Thread(new MThread());
        tmr = new Timer();
        conn.start();
        sp = getSharedPreferences("SETUP", Context.MODE_PRIVATE);
        Log.d(LOGTAG, "Create service.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(LOGTAG, "Start service.");
        tmr.schedule(new MTimer(), 0, sp.getInt("UPDATE", 5000));

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(conn != null) conn.interrupt();
        if(tmr != null) tmr.cancel();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("IP", IP);
        editor.putInt("PORT", PORT);
        editor.putInt("UPDATE", UPD);
        editor.apply();
    }

    class MTimer extends TimerTask {

        @Override
        public void run() {
            Connector.trg = true;
            Log.d(LOGTAG, "TRG = true");
        }
    }
    class MThread implements Runnable {

        @Override
        public void run() {
            for(;;){
                if(Connector.trg){
                    Log.d("myLogs", "WORK!");
                    Connector.trg = false;
                }
                else if(Connector.get_trg){
                    Intent send_intent = new Intent("ru.skalix.thstarter.get");
                    send_intent.setAction("ru.skalix.thstarter.get");
                    send_intent.putExtra("SIP", IP);
                    send_intent.putExtra("SPORT", PORT);
                    send_intent.putExtra("SUPDATE", UPD);
                    sendBroadcast(send_intent);
                    Connector.get_trg = false;
                }
            }
        }
    }
}
