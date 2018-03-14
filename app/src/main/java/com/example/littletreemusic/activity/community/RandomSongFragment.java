package com.example.littletreemusic.activity.community;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.adapter.CommunityRandomSongAdapter;
import com.example.littletreemusic.adapter.MainSongListAdapter;
import com.example.littletreemusic.di.Component.community.DaggerRandomSongComponent;
import com.example.littletreemusic.di.Component.community.RandomSongComponent;
import com.example.littletreemusic.di.Component.community.RandomSongModule;
import com.example.littletreemusic.pojo.ServerSong;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.presenter.community.RandomSongContract;
import com.example.littletreemusic.presenter.community.RandomSongPresenter;
import com.example.littletreemusic.service.MusicService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class RandomSongFragment extends Fragment implements RandomSongContract.IRandomSongView {

    @Inject
    RandomSongPresenter randomSongPresenter;

    @BindView(R.id.com_randomsong_button_refresh)
    Button btn_Refresh;
    @BindView(R.id.com_randomsong_button_changestyle)
    Button btn_Changestyle;
    @BindView(R.id.com_randomsong_rv)
    RecyclerView recyclerView;
    Unbinder unbinder;

    RelativeLayout mBodytemp;
    CommunityRandomSongAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        RandomSongComponent RandomSongComponent = DaggerRandomSongComponent.builder()
                .mainActivityComponent(((MainActivity) getActivity()).getMainActivityComponent())
                .randomSongModule(new RandomSongModule(this))
                .build();
        RandomSongComponent.inject(this);
        RandomSongComponent.inject(randomSongPresenter);
        View view = inflater.inflate(R.layout.com_randomsong, mBodytemp, true);
        mBodytemp = (RelativeLayout) view.findViewById(R.id.main_body);
        unbinder = ButterKnife.bind(this, view);
        init();

        return view;
    }


    @Override
    public void fillAdapter(List<ServerSong> serverSongs) {
        adapter = new CommunityRandomSongAdapter(serverSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), OrientationHelper.VERTICAL));
//       解决点击item，notifyItemChanged闪烁问题
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    public void showList() {

    }

    private void init(){
        randomSongPresenter.getRandomSongs();
    }









    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.com_randomsong_button_refresh, R.id.com_randomsong_button_changestyle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.com_randomsong_button_refresh:

                break;
            case R.id.com_randomsong_button_changestyle:

                break;
        }
    }


}








