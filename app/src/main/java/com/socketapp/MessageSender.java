package com.socketapp;

import android.os.AsyncTask;
import android.content.Context;
import android.app.ProgressDialog;
import java.io.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.lang.String;
import android.util.Log;

public class MessageSender extends AsyncTask<String,String,String>{
    Socket s;
    DataOutputStream dos;
    PrintWriter pw;
    ObjectInputStream ois = null;
    String receivedMessage;
    private TaskCompleted mCallback;
    private Context mContext;

    public MessageSender(Context context){
        this.mContext = context;
        this.mCallback = (TaskCompleted) context;
    }
    @Override
    protected String doInBackground(String... voids){

        String message = voids[0];

        try {
            s = new Socket("192.168.0.196", 6666);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            int bytesRead;
            byte[] buffer = new byte[1024];
            InputStream inputStream = s.getInputStream();
            if ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                receivedMessage = byteArrayOutputStream.toString("UTF-8");
            }
            Log.d("msg", receivedMessage);
            pw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return receivedMessage;
    }

    @Override
    protected void onPostExecute(String msg){
        Log.d("onPostExecute", receivedMessage);
        mCallback.onTaskComplete(receivedMessage);
        //return receivedMessage;
    }

}
