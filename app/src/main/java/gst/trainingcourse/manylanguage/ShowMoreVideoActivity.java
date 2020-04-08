package gst.trainingcourse.manylanguage;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

import gst.trainingcourse.manylanguage.adapter.MoreVideoAdapter;
import gst.trainingcourse.manylanguage.interface_click.onClickItemRecyclerviewVideo;
import gst.trainingcourse.manylanguage.lib_Helper.CheckConnectionInternet;

public class ShowMoreVideoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private VideoView mVideoView;
    private ProgressBar mProgressBar;
    private ArrayList<String> mArrayList;
    private MoreVideoAdapter mMoreVideoAdapter;
    private MediaController mMediaController;
    private int mPosition = 0;
    private int mCurrentPosition = 0;
    private onClickItemRecyclerviewVideo mOnClick = new onClickItemRecyclerviewVideo() {
        @Override
        public void onClickText(int position) {
            CheckConnectionInternet connectionInternet = new CheckConnectionInternet(getApplicationContext());
            if (connectionInternet.isConnected()) {
                setVideo(mArrayList.get(position));
                mPosition = position;
            } else {
                Toast.makeText(ShowMoreVideoActivity.this, "Bạn chưa kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_video);

        initView();
        initData();
        addAdapter();
    }

    private void setVideo(String uri) {
        if (mMediaController == null) {
            mMediaController = new MediaController(ShowMoreVideoActivity.this);
            mMediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mMediaController);

            mMediaController.setPrevNextListeners(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //next
                    nextVideo();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //previous
                    previousVideo();
                }
            });
        }
        mVideoView.setVideoURI(Uri.parse(uri));
        mProgressBar.setVisibility(View.VISIBLE);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mProgressBar.setVisibility(View.GONE);
                mVideoView.seekTo(mCurrentPosition);
                if (mCurrentPosition == 0) mVideoView.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                nextVideo();
            }
        });
    }

    private void nextVideo() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mPosition ++;
            if (mPosition > mArrayList.size() - 1) {
                mPosition = 0;
            }
            setVideo(mArrayList.get(mPosition));
            mVideoView.start();
        }
    }

    private void previousVideo() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mPosition --;
            if (mPosition < 0) {
                mPosition = mArrayList.size() - 1;
            }
            setVideo(mArrayList.get(mPosition));
            mVideoView.start();
        }
    }

    private void addAdapter() {
        mMoreVideoAdapter = new MoreVideoAdapter(mArrayList, mOnClick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mMoreVideoAdapter);
    }

    private void initData() {
        mArrayList = new ArrayList<>();

        Intent intent = getIntent();
        String[] link = intent.getStringArrayExtra("linkVideo");

        if (link != null) {
            for (int i = 0; i < link.length; i++) {
                mArrayList.add(link[i]);
            }
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.listView);
        mProgressBar = findViewById(R.id.progressBar);
        mVideoView = findViewById(R.id.videoView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPosition", mVideoView.getCurrentPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPosition = savedInstanceState.getInt("currentPosition");
        mVideoView.seekTo(mCurrentPosition);
    }
}
