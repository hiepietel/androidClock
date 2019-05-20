package com.socketapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements TaskCompleted{

    EditText e1;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.editText);
        tv = (TextView)findViewById(R.id.textView);

    }
    public void sendAndReceive(){

    }
    public void send(View v){
        MessageSender messageSender = new MessageSender(MainActivity.this);
        messageSender.execute(e1.getText().toString());
        //tv.setText(messageSender.receivedMessage);
        //Log.d("send ",messageSender.doInBackground());
        //messageSender.recievedMessage();
        //e1.setText("");
    }
    @Override
    public void onTaskComplete(String msg) {
        Log.d("msg", "ontask");
        //e1.setText("");
        tv.setText(msg);
    }
}
