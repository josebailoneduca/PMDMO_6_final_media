package com.imagenprogramada.mediaplayer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imagenprogramada.mediaplayer.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
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
        Log.i("jjbo","onCreateView lista");
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Configurar el recyclerView eligiendo layout lineal si solo hay una columna
        //como es el caso. En caso contrario eligiria gridlayout
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(Medios.getItems(verAudio,verVideo,verStreaming),this));
        }

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void play(Medios.Medio mItem) {
        ((MainActivity)getActivity()).play(mItem);
    }
}