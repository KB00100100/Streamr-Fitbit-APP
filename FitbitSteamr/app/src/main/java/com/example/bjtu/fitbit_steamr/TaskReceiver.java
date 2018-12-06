package com.example.bjtu.fitbit_steamr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TaskReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Task Receiver received!");
        Intent i = new Intent(context, TaskService.class);
        Bundle bundle = intent.getExtras();

        i.putExtras(bundle);
        context.startService(i);
    }
}
