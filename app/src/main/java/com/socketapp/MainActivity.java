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

    EditText e1;
    TextView tv;
    Button TPbtn;
    Button magentaBtn, cyanBtn, greenBtn;
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
                messageSender.execute("purple");
            }
        });
        cyanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MessageSender messageSender = new MessageSender(MainActivity.this);
                messageSender.execute("cyan");
            }
        });
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MessageSender messageSender = new MessageSender(MainActivity.this);
                messageSender.execute("green");
            }
        });
        alarmONOFF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MessageSender messageSender = new MessageSender(MainActivity.this);
                    messageSender.execute("alarmOn");
                }
                else{
                    MessageSender messageSender = new MessageSender(MainActivity.this);
                    messageSender.execute("alarmOff");
                }
            }
        });
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        TextView textView = (TextView)findViewById(R.id.timePicker);
        textView.setText("Hour: "+hourOfDay+ " m: "+minute);
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
        MessageSender messageSender = new MessageSender(MainActivity.this);
        messageSender.execute("sa"+h+"m"+m+"end");
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

        //String temp = e1.getText() +"\n" +msg;

        tv.setText(msg + "\n"+ tv.getText());
    }
}
