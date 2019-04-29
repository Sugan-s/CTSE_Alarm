package com.example.suganya.alarmsystem;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;

import java.io.IOException;

public class AudioPlay {
    public static  MediaPlayer mediaPlayer;

    public static boolean isplayingAudio=false;



    public static void PlayAudio(Context c, String path){
        mediaPlayer = MediaPlayer.create(c,Uri.parse(path));

        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        isplayingAudio=true;
//        try {
//            mediaPlayer.setDataSource(c, Uri.parse(path));
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            mediaPlayer.setLooping(true);
//            isplayingAudio=true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//
//        mediaPlayer = MediaPlayer.create(c,id);
//        mediaPlayer.start();

    }
    public static void stopAudio(){
        mediaPlayer.stop();
        isplayingAudio=false;
    }
}
