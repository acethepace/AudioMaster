package com.example.mallock.imagetovoice;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Mallock on 6/26/2018.
 */

public class FileClient {
    final String TAG = this.getClass().getCanonicalName();

    public void playMp3Media(String fileName, Context context) {
        File file = new File(fileName);
        playMp3Media(file, context);
    }

    public void playMp3Media(File file, Context context) {
        MediaPlayer recordedSong;
        try {
            recordedSong = MediaPlayer.create(context, Uri.fromFile(file));
            recordedSong.reset();
            recordedSong.setDataSource(context, Uri.fromFile(file));
            recordedSong.prepare();
            recordedSong.start();
            Toast.makeText(context, "Playing audio...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Error playing mp3 file");
            e.printStackTrace();
        }
    }

    public File createFileFromBytes(String filename, byte[] bytes) {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);

        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bytes);
            fos.flush();
            fos.close();
            Log.e(TAG, "successfully written to file!");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "failed to write to file");
        }
        return f;
    }
}
