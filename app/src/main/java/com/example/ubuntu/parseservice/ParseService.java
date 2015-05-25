package com.example.ubuntu.parseservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ubuntu on 24/05/15.
 */
public class ParseService extends Service {
    List<ParseObject> lista;
    Timer timer = new Timer();
    MyTimerTask timerTask;
    ResultReceiver resultReceiver;

    @Override
    public IBinder onBind(Intent intent) {
       //Parse.initialize(this, "zt3WZ4oUyeGcnq0ngrqBeqJE05PWeOdP6c2XYpaR", "SSLu3imS5yFuKwYQ5Zq0OW8PhkU1sbmsPbksDz0Z");
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startid){
        Parse.initialize(this, "zt3WZ4oUyeGcnq0ngrqBeqJE05PWeOdP6c2XYpaR", "SSLu3imS5yFuKwYQ5Zq0OW8PhkU1sbmsPbksDz0Z");
        resultReceiver = intent.getParcelableExtra("receiver");

        timerTask = new MyTimerTask();
        timer.scheduleAtFixedRate(timerTask, 5000, 5000);
        ParseObject testObject = new ParseObject("Users");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        //query.whereEqualTo("playerName", "Dan Stemkoski");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + list.size() + " scores");
                    Bundle bundle = new Bundle();
                    Teste teste = new Teste(list);
                    bundle.putSerializable("lista", teste);
                    resultReceiver.send(100, bundle);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        return START_STICKY;
    }



    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Bundle bundle = new Bundle();
        bundle.putString("end", "Timer Stopped....");
        resultReceiver.send(200, bundle);
    }
    public class Teste implements Serializable {
        List<ParseObject> objects;

        public Teste(List<ParseObject> parseObjects){
            objects = parseObjects;
        }

        public ParseObject get(){
            if(objects.size() > 0)
                return (ParseObject)objects.get(objects.size()-1);
            return null;
         }
    }

    private class MyTimerTask extends TimerTask {
        public MyTimerTask() {
            Bundle bundle = new Bundle();
            bundle.putString("start", "Timer Started....");
            //resultReceiver.send(100, bundle);
        }
        @Override
        public void run() {

            //resultReceiver.send(, null);
        }
    }
}
