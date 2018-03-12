package com.example.littletreemusic.activity.community;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.adapter.GridViewAdapter0;
import com.example.littletreemusic.di.Component.community.CommunityHomeComponent;
import com.example.littletreemusic.di.Component.community.CommunityHomeModule;
import com.example.littletreemusic.di.Component.community.DaggerCommunityHomeComponent;
import com.example.littletreemusic.presenter.community.CommunityFMPresenter;
import com.example.littletreemusic.presenter.community.CommunityHomeContract;
import com.example.littletreemusic.presenter.community.CommunityHomePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ZLX Vincent on 2017/8/30.
 */

public class CommunityHomeFragment extends Fragment implements CommunityHomeContract.ICommunityHomeView {

    @BindView(R.id.fragment_body_gridview)
    GridView gridView;
    Unbinder unbinder;

    @Inject
    CommunityFMPresenter communityFMPresenter;
    @Inject
    CommunityHomePresenter communityHomePresenter;

    GridViewAdapter0 gridViewAdapter0;
    RelativeLayout mBodytemp;



    private String[] titles = new String[]{"猜你喜欢", "看图挑歌", "推荐排行", "敬请期待"};
    private int[] images = new int[]{R.drawable.icon_luck_100px_white, R.drawable.icon_picture_100px_white
            , R.drawable.icon_leaderboard_100px_white, R.drawable.icon_live_100px_white};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        CommunityHomeComponent communityHomeComponent = DaggerCommunityHomeComponent.builder()
                .mainActivityComponent(((MainActivity) getActivity()).getMainActivityComponent())
                .communityHomeModule(new CommunityHomeModule(this))
                .build();
        communityHomeComponent.inject(this);
        communityHomeComponent.inject(communityHomePresenter);
        View view = inflater.inflate(R.layout.com_home, mBodytemp, true);
        mBodytemp = (RelativeLayout) view.findViewById(R.id.main_body);
        unbinder = ButterKnife.bind(this, view);

        gridViewAdapter0 = new GridViewAdapter0(this.getActivity(), titles, images);
        gridView.setAdapter(gridViewAdapter0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        communityFMPresenter.toRandomSong();
                        break;
                    case 1:
                        communityFMPresenter.toRandomPicture();
                        break;
                    case 2:
                        communityFMPresenter.toTopSong();
                        break;
                    case 3:
                        communityFMPresenter.toLive();
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





