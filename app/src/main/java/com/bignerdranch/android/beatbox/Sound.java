package com.bignerdranch.android.beatbox;

/**
 * Created by RBanks on 10/3/2016.
 */

public class Sound {
    private String m_assetPath;
    private String m_name;
    private int m_soundID;

    public int getSoundID() {
        return m_soundID;
    }

    public void setSoundID(int soundID) {
        m_soundID = soundID;
    }

    public Sound(String assetPath) {
        m_assetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        m_name = filename.replace(".wav", "");
    }

    public String getAssetPath() {
        return m_assetPath;
    }

    public String getName() {
        return m_name;
    }
}
