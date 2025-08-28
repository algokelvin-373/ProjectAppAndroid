package com.algokelvin.videoprocessing;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.videoprocessing.databinding.ActivityPreviewVideoBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;

public class PreviewVideoActivity extends AppCompatActivity {
    private static final String VIDEO_URI = "video_uri";
    private ActivityPreviewVideoBinding binding;
    private ExoPlayer player;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uri = Uri.parse(getIntent().getStringExtra(VIDEO_URI));
        playVideo();
    }

    private void playVideo() {
        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer((Player) player);
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();
        player.play();
    }
}