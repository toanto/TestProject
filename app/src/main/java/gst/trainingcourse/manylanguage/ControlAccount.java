package gst.trainingcourse.manylanguage;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gst.trainingcourse.manylanguage.adapter.ControlAccountAdapter;
import gst.trainingcourse.manylanguage.database.MyDatabase;
import gst.trainingcourse.manylanguage.interface_click.onClickItemRecyclerviewAccount;
import gst.trainingcourse.manylanguage.model.Account;

public class ControlAccount extends AppCompatActivity {

    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private MyDatabase mMyDatabase;
    private ControlAccountAdapter mAccountAdapter;
    private List<Account> mAccountArrayList;
    private SearchView mSearchView;
    private onClickItemRecyclerviewAccount onClickDelete = new onClickItemRecyclerviewAccount() {
        @Override
        public void onClickImgDelete(int position) {
            Account account = mAccountArrayList.get(position);
            mMyDatabase.deleteAccount(account.getId());
            mAccountArrayList.clear();
            mAccountArrayList.addAll(mMyDatabase.getAllAccount());
            mAccountAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_account);

        initView();
        initData();
        addControlAccountAdapter();
        setupToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setQueryHint("Nhap username...");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAccountAdapter.querySearch(s);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void addControlAccountAdapter() {
        mAccountAdapter = new ControlAccountAdapter(mAccountArrayList, onClickDelete);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAccountAdapter);
    }

    private void initData() {
        mMyDatabase = new MyDatabase(ControlAccount.this);
        mAccountArrayList = new ArrayList<>();
        mAccountArrayList = mMyDatabase.getAllAccount();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolBar);
        mRecyclerView = findViewById(R.id.recyclerviewAccount);
    }

    private void setupToolBar() {
        setSupportActionBar(mToolBar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
