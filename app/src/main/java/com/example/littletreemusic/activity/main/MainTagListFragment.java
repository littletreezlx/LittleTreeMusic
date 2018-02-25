package com.example.littletreemusic.activity.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.TagListRecyclerViewAdapter;
import com.example.littletreemusic.di.Component.main.DaggerMainTagListComponent;
import com.example.littletreemusic.di.Component.main.MainTagListComponent;
import com.example.littletreemusic.di.Component.main.MainTagListModule;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.main.TagListContract;
import com.example.littletreemusic.presenter.main.TagListPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 春风亭lx小树 on 2017/12/26.
 */

public class MainTagListFragment extends Fragment implements TagListContract.ITagListView {

    @BindView(R.id.recyclerview_mytags)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @Inject
    TagListPresenter tagListPresenter;
    @Inject
    MainFMPresenter mainFMPresenter;

    RelativeLayout mbodytemp;
    TagListRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        MainTagListComponent mainTagListComponent=DaggerMainTagListComponent.builder()
                .mainActivityComponent(((MainActivity) getActivity()).getMainActivityComponent())
                .mainTagListModule(new MainTagListModule(this))
                .build();
        mainTagListComponent.inject(this);
        mainTagListComponent.inject(tagListPresenter);
        View view = inflater.inflate(R.layout.main_taglist, mbodytemp, true);
        mbodytemp = (RelativeLayout) view.findViewById(R.id.main_body);
        unbinder = ButterKnife.bind(this, view);

        adapter = new TagListRecyclerViewAdapter(getActivity(), tagListPresenter.getTagList());
        adapter.setOnItemClickLitener(new TagListRecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mainFMPresenter.toOneTagSongList(position, adapter.findTagById(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "long", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), OrientationHelper.HORIZONTAL));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}



