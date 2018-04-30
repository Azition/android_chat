package com.addapp.izum.OtherClasses;

import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Азат on 15.10.2015.
 */
public class DownloadStream extends Thread {
    private final String TAG = getClass().getSimpleName();

    private final int SIZE = 16384;

    private static final int START = 0;
    private static final int CONTINUE = 1;
    private static final int END = 2;

    private AtomicBoolean started;
    private OnSendDataListener mListener;
    private int maxLength;
    private int pos;

    private int currentStatus;
    private String URL;

    private SocketIO mSocket = SocketIO.getInstance();

    public DownloadStream(String URL) {
        started = new AtomicBoolean(true);
        this.URL = URL;

    }

    public void setListener(OnSendDataListener mListener){
        this.mListener = mListener;
    }

    public int getPos() {
        return pos;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getURL(){
        return URL;
    }

    @Override
    public void run() {

//        File fin = new File("/storage/emulated/0/Pictures/tropik.jpg");
        File fin = new File(URL);
        String stringBuffer;
        String encodedString;
        InputStream inputStream = null;
        pos = 0;
        int part = SIZE;
        maxLength = 0;
        char buf[] = new char[SIZE];

        maxLength = (int) fin.length();

        try {
            inputStream = new FileInputStream(fin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes;
        byte[] buffer = new byte[SIZE];
        int bytesRead;

/*        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while((bytesRead = inputStream.read(buffer)) != -1)
                output.write(buffer, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
        maxLength = encodedString.length();
        Log.e(TAG, "Length: " + maxLength);
        Log.e(TAG, "encodedString: " + encodedString);*/

        currentStatus = START;
//        currentStatus = END;

        while(started.get()){
/*            if(currentStatus == END){
                mListener.onSendData("", currentStatus);
            } else {*/
/*                if (pos + part <= encodedString.length()) {
                    encodedString.getChars(pos, pos + part, buf, 0);
                    stringBuffer = new String(buf);
                    Log.e(TAG, "Pos: " + pos + " / " + maxLength);
                    mListener.onSendData(stringBuffer, currentStatus);
                    currentStatus = CONTINUE;
                    pos += part;
                } else {
                    part = encodedString.length() - pos;
                    encodedString.getChars(pos, pos + part, buf, 0);
                    stringBuffer = new String(buf);
                    Log.e(TAG, "Last Pos: " + pos + " / " + maxLength);
                    mListener.onSendData(stringBuffer, currentStatus);
                    currentStatus = END;
                }
            }*/
                try {

                    if ((bytesRead = inputStream.read(buffer)) != -1){
                        stringBuffer = Base64.encodeToString(buffer, 0, bytesRead, Base64.DEFAULT);
//                        Log.e(TAG, "bytesRead: " + bytesRead + "\nBuffer: " + stringBuffer);
                        mListener.onSendData(stringBuffer, currentStatus);
                        currentStatus = CONTINUE;
                        pos += bytesRead;
                    } else {
                        currentStatus = END;
                        mListener.onSendData("", currentStatus);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }

            synchronized (this){
                try {
                    Log.e(TAG, "ожидание...");
                    wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void nextPart(){
        synchronized (this){
            notify();
        }
    }

    public void stopDownload(){
        synchronized (this){
            started.set(false);
            notify();
        }
    }

    public interface OnSendDataListener{
        void onSendData(String base64, int state);
    }
}
