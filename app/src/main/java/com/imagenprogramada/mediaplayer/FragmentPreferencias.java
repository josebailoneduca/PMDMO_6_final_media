package com.imagenprogramada.mediaplayer;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.imagenprogramada.mediaplayer.databinding.FragmentPreferenciasBinding;
import com.imagenprogramada.mediaplayer.databinding.FragmentSecondBinding;

public class FragmentPreferencias extends Fragment {

    private FragmentPreferenciasBinding binding;
    SharedPreferences misPreferencias;
    boolean verAudio;
    boolean verVideo;
    boolean verStreaming;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        misPreferencias=getActivity().getSharedPreferences("Basic Data",MODE_PRIVATE);
        verAudio=misPreferencias.getBoolean("verAudio",true);
        verVideo=misPreferencias.getBoolean("verVideo",true);
        verStreaming=misPreferencias.getBoolean("verStreaming",true);

        binding = FragmentPreferenciasBinding.inflate(inflater, container, false);
        inicializa();

        return binding.getRoot();

    }

    private void inicializa() {
        binding.switchAudio.setOnClickListener(v -> actualizar());
        binding.switchAudio.setChecked(verAudio);
        binding.switchVideo.setOnClickListener(v -> actualizar());
        binding.switchVideo.setChecked(verVideo);
        binding.switchStreaming.setOnClickListener(v -> actualizar());
        binding.switchStreaming.setChecked(verStreaming);



    }

    private void actualizar() {
        SharedPreferences.Editor editor=misPreferencias.edit();
        editor.putBoolean("verAudio",binding.switchAudio.isChecked());
        editor.putBoolean("verVideo",binding.switchVideo.isChecked());
        editor.putBoolean("verStreaming",binding.switchStreaming.isChecked());
        editor.commit();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}