package com.upgradedsoftware.android.chat.utils;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.SystemClock;

import com.upgradedsoftware.android.chat.App;
import com.upgradedsoftware.android.chat.R;

public class SoundMaster {

    private static final SoundMaster ourInstance = new SoundMaster();
    private long lastTime;

    private SoundMaster() {
    }

    public static SoundMaster getInstance() {
        return ourInstance;
    }

    public void playNotification() {
        if (lastTime < SystemClock.elapsedRealtime()) {
            SoundThread soundThread = new SoundThread();
            soundThread.start();
        }
        lastTime = SystemClock.elapsedRealtime();
    }


    class SoundThread extends Thread {

        private SoundThread() {
        }

        @Override
        public void run() {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .setMaxStreams(10)
                    .build();

            int sound = mSoundPool.load(App.getContext(), R.raw.notify, 1);
            mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
    }
}
