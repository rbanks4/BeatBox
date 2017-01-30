package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RBanks on 10/3/2016.
 */

public class BeatBox {
    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager m_assets;
    private List<Sound> m_sounds = new ArrayList<>();
    private SoundPool m_soundPool;

    public BeatBox(Context context) {
        m_assets = context.getAssets();
        m_soundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        //grab all the file names and log the count
        try{
            soundNames = m_assets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds.");
        } catch(IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        //add all your sounds to a list
        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                m_sounds.add(sound);
            } catch(IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = m_assets.openFd(sound.getAssetPath());
        int soundId = m_soundPool.load(afd, 1);
        sound.setSoundID(soundId);
        Log.i(TAG, "Loading sound " + sound.getName() + " with id " + soundId);
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundID();
        if(soundId == null)
            return;

        m_soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        m_soundPool.release();
    }

    public List<Sound> getSounds() {
        return m_sounds;
    }
}
