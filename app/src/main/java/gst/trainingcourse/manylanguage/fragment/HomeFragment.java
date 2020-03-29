package gst.trainingcourse.manylanguage.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
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

import java.util.ArrayList;

import gst.trainingcourse.manylanguage.MainActivity;
import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.adapter.HomeAdapter;
import gst.trainingcourse.manylanguage.model.Data;
import gst.trainingcourse.manylanguage.model.Information;

public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Information> mListGirls = new ArrayList<>();
    private HomeAdapter mHomeAdapter;

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
    }

    private void setupAdapter() {
        mHomeAdapter = new HomeAdapter(mListGirls);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mHomeAdapter);
    }
}
