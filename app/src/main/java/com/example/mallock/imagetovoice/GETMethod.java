package com.example.mallock.imagetovoice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Mallock on 6/26/2018.
 */

public class GETMethod extends AsyncTask<Object, Integer, byte[]> {

    private final String TAG = this.getClass().getCanonicalName();
    String url;
    Dialog dialog;
    Context context;

    public GETMethod(String url, Context context) {
        this.url = url;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading audio...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected byte[] doInBackground(Object[] objects) {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(this.url);
        try {
            HttpResponse response = client.execute(get);
            return EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        if (bytes == null) {
            Log.e(TAG, "bytes are null!");
        }

        File f = new Mp3ClientFactory().getMp3FileHandler().createFileFromBytes("/aaa.wav", bytes);
        dialog.dismiss();
        new Mp3ClientFactory().getMp3FileHandler().playMp3Media(f, context);
        Log.e(TAG, "test");
        super.onPostExecute(bytes);
    }
}