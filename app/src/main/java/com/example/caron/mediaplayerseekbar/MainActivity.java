package com.example.caron.mediaplayerseekbar;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.media.MediaPlayer;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isstopped = false;
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.button_start);
        final Button stop = (Button) findViewById(R.id.button_stop);
        Button pause = (Button) findViewById(R.id.button_pause);
        SeekBar volume = (SeekBar)findViewById(R.id.sound);
        final SeekBar music = (SeekBar) findViewById(R.id.music);
        Switch mute = (Switch) findViewById(R.id.switch1);
        mediaPlayer = mediaPlayer.create(this, R.raw.rock);
        mediaPlayer.setLooping(false);
        music.setMax(mediaPlayer.getDuration());
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        volume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
       // mediaPlayer.setVolume(0,0);
        volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer==null) {
                    mediaPlayer = mediaPlayer.create(v.getContext(), R.raw.rock);
                    mediaPlayer.setLooping(false);
                    music.setMax(mediaPlayer.getDuration());
                }
                    mediaPlayer.start();
                    Bigcomputing bc = new Bigcomputing(getApplicationContext(), (SeekBar) findViewById(R.id.music), mediaPlayer);
                    bc.execute();

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // au stop on reset la bar et on realease le media pour le redefinir au start
                // en cours de recherche pour solutionner le restart sans recharger le media (gestion event ?)
                // si stop et set progress 0 quand start sans reboot media on a erreur -38,0
                mediaPlayer.stop();
                music.setProgress(0);
                mediaPlayer.release();
                mediaPlayer = null;

            }
        });


        try {
            music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {mediaPlayer.seekTo(progress);}
            });
        }
        catch (Exception e){e.printStackTrace();}

        try {
            volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                 //   mediaPlayer.setVolume(1,1);
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

                }
            });
        }
        catch (Exception e) {e.printStackTrace();}

        try{
            mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        mediaPlayer.setVolume(0,0);
                    }
                    else
                    {
                        mediaPlayer.setVolume(1,1);
                    }
                }
            });
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
