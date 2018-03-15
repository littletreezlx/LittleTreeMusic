package com.example.littletreemusic.activity.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.base.BaseFragment;
import com.example.littletreemusic.presenter.navigation.NavAboutContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class NavAboutFragment extends BaseFragment implements NavAboutContract.INavAboutView {


    @BindView(R.id.nav_about_btnback)
    ImageButton btn_Back;
    @BindView(R.id.nav_about_tvcontent)
    TextView tv_Contenr;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.nav_about, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
