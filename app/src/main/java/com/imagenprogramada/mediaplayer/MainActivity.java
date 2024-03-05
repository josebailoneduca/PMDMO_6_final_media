package com.imagenprogramada.mediaplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.os.*;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.imagenprogramada.mediaplayer.databinding.ActivityMainBinding;
import com.imagenprogramada.mediaplayer.databinding.FragmentFirstBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity implements  MediaController.MediaPlayerControl, View.OnTouchListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    MediaPlayer mediaPlayer;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //carga de medios
        Medios.loadMediosFromJSON(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        mc=  new MediaController(this){
            @Override
            public void show(int timeout) {
                super.show(0);
            }
        };

        mc.setMediaPlayer(this);
        mc.setAnchorView(binding.getRoot());

        findViewById(R.id.list).setOnTouchListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_FirstFragment_to_fragmentPreferencias);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void play(Medios.Medio medio){
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        switch (medio.getTipo()){
            case Tipos.audio:
                playAudio(medio.getURI());
                break;
            case Tipos.video:
                playVideo(medio.getURI(),medio.getTipo());
            case Tipos.streaming:
                playVideo(medio.getURI(),medio.getTipo());
        }
    }


    private void playVideo(String uri,String tipo) {
        Bundle bundle = new Bundle();
        bundle.putString("uri", uri);
        bundle.putString("tipo", tipo);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
    }

    private void playAudio(String uri) {
        int id=getResources().getIdentifier(uri, "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, id);
        Handler h;
        h=new Handler();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mc.show(0);
                        start();
                    }
                });
            }
        });


    }

    @Override
    public void start() {
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }
    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }
    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }
    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }
    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }
    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
    @Override
    public int getBufferPercentage() {
        return 0;
    }
    @Override
    public boolean canPause() {
        return true;
    }
    @Override
    public boolean canSeekBackward() {
        return true;
    }
    @Override
    public boolean canSeekForward() {
        return true;
    }
    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN && mediaPlayer!=null)
        if(!mc.isShowing())
                mc.show(0);
            else
                mc.hide();
        return false;
    }

}