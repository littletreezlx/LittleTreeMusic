package com.example.littletreemusic.activity.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.di.Component.main.DaggerMainTitleComponent;
import com.example.littletreemusic.di.Component.main.MainTitleComponent;
import com.example.littletreemusic.di.Component.main.MainTitleModule;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.main.MainTitleContract;
import com.example.littletreemusic.presenter.main.MainTitlePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/8/30.
 */

public class MainTitleFragment extends Fragment implements MainTitleContract.IMainTitleView {


    @BindView(R.id.main_title_textview)
    TextView tv_title;
    @BindView(R.id.main_title_btnback)
    ImageButton btn_Back;
    @BindView(R.id.main_title_btnsearch)
    ImageButton btn_Search;
    Unbinder unbinder;

    @Inject
    MainFMPresenter mainFMPresenter;
    @Inject
    MainTitlePresenter mainTitlePresenter;
    RelativeLayout mtitletemp;
    String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        MainTitleComponent mainTitleComponent=DaggerMainTitleComponent.builder()
                .mainActivityComponent(((MainActivity)getActivity()).getMainActivityComponent())
                .mainTitleModule(new MainTitleModule(this))
                .build();
        mainTitleComponent.inject(this);
        mainTitleComponent.inject(mainTitlePresenter);
        View view = inflater.inflate(R.layout.fragment_main_title, mtitletemp, true);
        mtitletemp = (RelativeLayout) view.findViewById(R.id.main_title);
        unbinder = ButterKnife.bind(this, view);
        backToHome();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.main_title_btnback, R.id.main_title_btnsearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_title_btnback:
                mainFMPresenter.backPressed();
                break;
            case R.id.main_title_btnsearch:

                break;
        }
    }


    public void setTitle(String setTitle){
        title=setTitle;
        tv_title.setText(title);
        btn_Back.setVisibility(View.VISIBLE);
        btn_Search.setVisibility(View.VISIBLE);
    }


//    回到首页后改textview并隐藏按钮
    public void backToHome(){
        tv_title.setText("小树音乐");
        btn_Back.setVisibility(View.GONE);
        btn_Search.setVisibility(View.GONE);
    }

}



