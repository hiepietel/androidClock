package com.socketapp;

import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.util.Log;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, TaskCompleted{
    String IP ="192.168.0.176";
    EditText e1, editIP;
    TextView tv, changeIP;
    Button TPbtn;
    Button magentaBtn, cyanBtn, greenBtn, IPBtn;
    Switch alarmONOFF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.editText);
        tv = (TextView)findViewById(R.id.textView);
        TPbtn = (Button)findViewById(R.id.TP);
        cyanBtn = (Button)findViewById(R.id.cyanBtn);
        magentaBtn = (Button)findViewById(R.id.magentaBtn);
        greenBtn = (Button)findViewById(R.id.greenBtn);
        alarmONOFF = (Switch)findViewById(R.id.alarmONOFF);
        //IP
        editIP = (EditText) findViewById(R.id.editIP);
        IPBtn = (Button)findViewById(R.id.setIP);
        changeIP = (TextView)findViewById(R.id.changeIP);

        IPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IP = editIP.getText().toString();
                changeIP.setText("IP set to: "+IP);
                MessageSender messageSender = new MessageSender(MainActivity.this);
                messageSender.execute("connect", IP);
            }
        });
        TPbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }

        });
        magentaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageSender messageSender = new MessageSender(MainActivity.this);
                messageSender.execute("purple", IP);
            }
        });
        cyanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MessageSender messageSender = new MessageSender(MainActivity.this);
                messageSender.execute("cyan", IP);
            }
        });
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MessageSender messageSender = new MessageSender(MainActivity.this);
                messageSender.execute("green", IP);
            }
        });
        alarmONOFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MessageSender messageSender = new MessageSender(MainActivity.this);
                    messageSender.execute("alarmOn", IP);
                }
                else{
                    MessageSender messageSender = new MessageSender(MainActivity.this);
                    messageSender.execute("alarmOff", IP);
                }
            }
        });
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        TextView textView = (TextView)findViewById(R.id.timePicker);

        String h;
        if(hourOfDay<10){
            h = "0"+Integer.toString(hourOfDay);
        }
        else {
            h = Integer.toString(hourOfDay);
        }
        String m;
        if(minute<10){
            m = "0"+Integer.toString(minute);
        }
        else{
            m = Integer.toString(minute);
        }
        textView.setText("ALARM TIME SET TO "+h+":"+m);
        MessageSender messageSender = new MessageSender(MainActivity.this);
        messageSender.execute("sa"+h+"m"+m+"end", IP);
    }
    public void send(View v){
        MessageSender messageSender = new MessageSender(MainActivity.this);
        messageSender.execute(e1.getText().toString(), IP);
        //tv.setText(messageSender.receivedMessage);
        //Log.d("send ",messageSender.doInBackground());
        //messageSender.recievedMessage();
        //e1.setText("");
    }
    @Override
    public void onTaskComplete(String msg) {
        Log.d("msg", "ontask");
        e1.setText("");

        //String temp = e1.getText() +"\n" +msg;
        tv.setText(msg + "\n"+ tv.getText());

    }
}
