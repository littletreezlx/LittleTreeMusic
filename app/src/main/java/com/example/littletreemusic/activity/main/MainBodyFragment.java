package com.example.littletreemusic.activity.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.GridViewAdapter0;
import com.example.littletreemusic.di.Component.main.DaggerMainBodyComponent;
import com.example.littletreemusic.di.Component.main.MainBodyComponent;
import com.example.littletreemusic.presenter.main.MainFMPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ZLX Vincent on 2017/8/30.
 */

public class MainBodyFragment extends Fragment {

    GridViewAdapter0 gridViewAdapter0;
    @BindView(R.id.fragment_body_gridview)
    GridView gridView;

    RelativeLayout mBodytemp;

    private String[] titles = new String[]{"音乐", "最爱", "分类", "文件"};
    private int[] images = new int[]{R.drawable.icon_note1, R.drawable.icon_favorites, R.drawable.icon_folder2, R.drawable.icon_folder};


    Unbinder unbinder;

    @Inject
    MainFMPresenter mainFMPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        MainBodyComponent mainBodyComponent=DaggerMainBodyComponent.builder()
                .mainActivityComponent(((MainActivity)getActivity()).getMainActivityComponent())
                .build();
        mainBodyComponent.inject(this);
        View view = inflater.inflate(R.layout.main_body, mBodytemp, true);
        mBodytemp = (RelativeLayout) view.findViewById(R.id.main_body);
        unbinder = ButterKnife.bind(this, view);

        gridViewAdapter0 = new GridViewAdapter0(this.getActivity(), titles, images);
        gridView.setAdapter(gridViewAdapter0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mainFMPresenter.toFullSongList();
                        break;
                    case 1:
                        mainFMPresenter.toPlayList();
                        break;
                    case 2:
                        mainFMPresenter.toTagList();
                        break;
                    case 3:
                        mainFMPresenter.toCommunity();
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}





