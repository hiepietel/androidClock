package com.socketapp;

import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, TaskCompleted{

    EditText e1;
    TextView tv;
    Button TPbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.editText);
        tv = (TextView)findViewById(R.id.textView);
        TPbtn = (Button)findViewById(R.id.TP);
        TPbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }

        });
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        TextView textView = (TextView)findViewById(R.id.timePicker);
        textView.setText("Hour: "+hourOfDay+ " m: "+minute);
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
