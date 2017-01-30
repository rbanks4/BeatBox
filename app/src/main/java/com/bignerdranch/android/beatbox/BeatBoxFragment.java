package com.bignerdranch.android.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by RBanks on 10/3/2016.
 */

public class BeatBoxFragment extends Fragment {

    private BeatBox m_beatBox;


    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        m_beatBox = new BeatBox(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        m_beatBox.release();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beat_box, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_beat_box_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new SoundAdapter(m_beatBox.getSounds()));

        return view;
    }

    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Button m_button;
        private Sound m_sound;

        public SoundHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_sound, container, false));

            m_button = (Button) itemView.findViewById(R.id.list_item_sound_button);
            m_button.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            m_beatBox.play(m_sound);
            Log.i("BeatBox", "Playing sound " + m_sound.getName() + " with ID: " + m_sound.getSoundID());
        }

        public void bindSound(Sound sound) {
            m_sound = sound;
            m_button.setText(m_sound.getName());
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
        private List<Sound> m_sounds;

        public SoundAdapter(List<Sound> sounds) {
            m_sounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(SoundHolder soundHolder, int position) {
            Sound sound = m_sounds.get(position);
            soundHolder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
//            return 0;
            return m_sounds.size();
        }
    }
}
