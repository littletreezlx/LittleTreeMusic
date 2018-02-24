package com.example.littletreemusic.presenter.main;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class TagListPresenter implements TagListContract.ITagListPresenter {

    @Inject
    SharedPreferences sp;
    @Inject
    TagListContract.ITagListView mITagListView;


    public TagListPresenter() {
    }

//    @Override
//    public void bindView(TagListContract.ITagListIview view) {
//        mITagListIview=view;
//    }

    //    @Override
//    public void init(MainTagListComponent mainTagListComponent) {
//        mainTagListComponent.inject(this);
//    }


    @Override
    public List<String> getTagList() {
        List<String> tagList = new ArrayList<>();
        Set tagSet = sp.getStringSet("TagSet", null);
        if (tagSet != null) {
            tagList.addAll(tagSet);
        }
        return tagList;
    }

}

