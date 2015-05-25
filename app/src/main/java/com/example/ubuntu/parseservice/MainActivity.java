package com.example.ubuntu.parseservice;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseObject;


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

    }

    @Override
    public void onDestroy(){
        stopService(intent);
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
            runOnUiThread(new UpdateUI("Result Received " + (String)((ParseService.Teste)resultData.getSerializable("lista")).get().getString("username")));
        }
    }
}
