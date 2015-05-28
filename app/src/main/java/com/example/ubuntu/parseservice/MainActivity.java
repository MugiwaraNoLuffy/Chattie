package com.example.ubuntu.parseservice;

import android.content.Intent;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseRelation;


public class MainActivity extends ActionBarActivity {
    TextView txtView;
    MyResultReceiver resultReceiver;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.hello);
        resultReceiver = new MyResultReceiver(null);
        intent = new Intent(this, ParseService.class);
        intent.putExtra("receiver", resultReceiver);
        startService(intent);
        final EditText edit = (EditText) findViewById(R.id.message);
        Parse.initialize(this, "zt3WZ4oUyeGcnq0ngrqBeqJE05PWeOdP6c2XYpaR", "SSLu3imS5yFuKwYQ5Zq0OW8PhkU1sbmsPbksDz0Z");
        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(MainActivity.this, edit.getText(), Toast.LENGTH_SHORT).show();

                    ParseObject obj = new ParseObject("msg_chatest");
                    obj.put("userid", ParseObject.createWithoutData("Users", "7hKOJBhn7J"));
                    obj.put("message", edit.getText().toString());
                    obj.saveInBackground();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStop(){
        stopService(intent);
        super.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class UpdateUI implements Runnable{
        String updateString;

        public UpdateUI(String updateString) {
            this.updateString = updateString;
        }
        public void run() {
            txtView.setText(updateString);
        }
    }

    private class MyResultReceiver extends ResultReceiver{

        public MyResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {
            if(resultData != null)
                runOnUiThread(new UpdateUI((String)((ParseService.Teste)resultData.getSerializable("lista")).get()));
        }
    }
}
