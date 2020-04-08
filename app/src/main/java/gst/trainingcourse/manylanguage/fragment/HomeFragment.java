package gst.trainingcourse.manylanguage.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import gst.trainingcourse.manylanguage.MainActivity;
import gst.trainingcourse.manylanguage.ShowMoreVideoActivity;
import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.ShowMoreImageActivity;
import gst.trainingcourse.manylanguage.adapter.HomeAdapter;
import gst.trainingcourse.manylanguage.fragment.dialog_fagment.DialogShowImage;
import gst.trainingcourse.manylanguage.interface_click.onClickItemRecycerviewGirl;
import gst.trainingcourse.manylanguage.lib_Helper.CheckConnectionInternet;
import gst.trainingcourse.manylanguage.lib_Helper.ReadJsonFile;
import gst.trainingcourse.manylanguage.model.Data;
import gst.trainingcourse.manylanguage.model.Information;
import gst.trainingcourse.manylanguage.model.LinkVideo;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Information> mListGirls = new ArrayList<>();
    private ArrayList<LinkVideo> mLinkVideos = new ArrayList<>();
    public static HomeAdapter mHomeAdapter;
    private onClickItemRecycerviewGirl mOnClick = new onClickItemRecycerviewGirl() {
        @Override
        public void onClickImage(int position) {
            Information information = mListGirls.get(position);

            DialogShowImage dialogShowImage = DialogShowImage.newInstance(information.getmImage());
            dialogShowImage.show(getFragmentManager(), "dialogImg");
        }

        @Override
        public void onClickIconLove(int position) {
            //Lam gi do o day
        }

        @Override
        public void onClickMoreVideo(int position) {
            Information information = mListGirls.get(position);
            for (int i = 0; i < mLinkVideos.size(); i++) {
                if (information.getmHoTen().equals(mLinkVideos.get(i).getmName())) {
                    Intent intent = new Intent(getActivity(), ShowMoreVideoActivity.class);
                    intent.putExtra("linkVideo", mLinkVideos.get(i).getmLink());
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onClickMoreImage(int position) {
            Information information = mListGirls.get(position);
            CheckConnectionInternet checkConnectionInternet = new CheckConnectionInternet(getContext());
            if (checkConnectionInternet.isConnected()) {
                Intent intent = new Intent(getActivity(), ShowMoreImageActivity.class);
                intent.putExtra("linkRss", information.getmLinkRss());
                intent.putExtra("nameGirl", information.getmHoTen());
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Bạn chưa kết nối Internet", Toast.LENGTH_SHORT).show();

            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initData();
        setupAdapter();
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerviewHome);
    }

    private void initData() {
        Data data = new Data();
        mListGirls.addAll(data.getmList());

        mLinkVideos = new ArrayList<>();
        try {
            mLinkVideos = ReadJsonFile.readLinkVideoJson(getContext());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupAdapter() {
        mHomeAdapter = new HomeAdapter(getContext(), mListGirls, mOnClick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mHomeAdapter);
    }
}
