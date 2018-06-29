package com.example.mallock.imagetovoice;

/**
 * Created by Mallock on 6/26/2018.
 */

public class Mp3ClientFactory {
    FileClient mp3FileHandler;

    public FileClient getMp3FileHandler() {
        if (this.mp3FileHandler == null) {
            mp3FileHandler = new FileClient();
        }
        return mp3FileHandler;
    }
}
