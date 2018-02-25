package com.example.littletreemusic.activity.play;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.ViewPagerAdapter;
import com.example.littletreemusic.animate.DepthPageTransformer;
import com.example.littletreemusic.di.Component.play.DaggerViewPagerComponent;
import com.example.littletreemusic.di.Component.play.ViewPagerComponent;
import com.example.littletreemusic.di.Component.play.ViewPagerModule;
import com.example.littletreemusic.presenter.play.ViewPagerContract;
import com.example.littletreemusic.presenter.play.ViewPagerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class ViewPagerFragment extends Fragment implements ViewPagerContract.IViewPagerView
        ,ViewPager.OnPageChangeListener{

    @BindView(R.id.play_viewpager_viewpager)
    ViewPager viewPager;
    Unbinder unbinder;

    @Inject
    ViewPagerPresenter viewPagerPresenter;
    @Inject
    PlayActivity playActivity;

    ViewPagerComponent viewPagerComponent;
    RelativeLayout viewPagerTemp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        viewPagerComponent = DaggerViewPagerComponent.builder()
                .playActivityComponent(((PlayActivity) getActivity()).getPlayActivityComponent())
                .viewPagerModule(new ViewPagerModule(this))
                .build();
        viewPagerComponent.inject(this);
        viewPagerComponent.inject(viewPagerPresenter);
        View view = inflater.inflate(R.layout.play_viewpager, viewPagerTemp, true);
        viewPagerTemp = (RelativeLayout) view.findViewById(R.id.play_viewpager);
        unbinder = ButterKnife.bind(this, view);

        initViewPager();
        return view;
    }

    private void initViewPager(){
        FragmentManager fm=playActivity.getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fm,viewPagerComponent);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    //ViewPager页面切换
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (i == 2) {
            switch (viewPager.getCurrentItem()) {
                case 0:
                    break;
                case 1:
//                    viewPager.getAdapter().notifyDataSetChanged();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
