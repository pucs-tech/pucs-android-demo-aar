package com.fadanllc.pucsandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.fadanllc.pucsadplayer.ui.PUCsAdView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    SurfaceView surfaceView;
    MediaPlayer mediaPlayer;
    Button playButton, stopButton;
    PUCsAdView adView;
    Uri mediaUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surface_view);
        playButton = findViewById(R.id.play_btn);
        stopButton = findViewById(R.id.stop_btn);

        adView = new PUCsAdView(this);
        try {
            adView.initialize("", surfaceView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaUri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        final SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        mediaPlayer = MediaPlayer.create(this, mediaUri);

        adView.transitionAdViewToSize(320, 240);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    adView.playAds(null, null);
                } else {
                    adView.stopAds();
                    mediaPlayer.start();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adView.stopAds();
                mediaPlayer.reset();
                surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
                surfaceHolder.setFormat(PixelFormat.OPAQUE);
                try {
                    mediaPlayer.setDataSource(MainActivity.this, mediaUri);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(null);
    }
}