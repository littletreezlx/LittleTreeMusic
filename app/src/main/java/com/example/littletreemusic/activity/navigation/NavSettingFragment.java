package com.example.littletreemusic.activity.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.littletreemusic.R;
import com.example.littletreemusic.base.BaseFragment;
import com.example.littletreemusic.presenter.navigation.NavSettingContract;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class NavSettingFragment extends BaseFragment implements NavSettingContract.INavSettingView {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.nav_setting, container, false);
        return view;
    }




}
