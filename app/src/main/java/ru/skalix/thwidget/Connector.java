package ru.skalix.thwidget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Connector extends Service {
    public Connector() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
