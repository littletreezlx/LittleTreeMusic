package com.example.littletreemusic.presenter.play;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class PlayBottomPresenter implements PlayBottomContract.IPlayBottomPresenter{


    @Inject
    SharedPreferences sp;
    @Inject
    PlayBottomContract.IPlayBottomView mIPlayBottomView;

    Context mContext;
    Set<String> tagSet;
    List<String> tagList;


    //    显示添加、应用标签项和标签栏
    public void findTagList() {
        tagSet = sp.getStringSet("TagSet", null);
        tagList = new ArrayList<>();
        if (tagSet != null) {
            tagList.addAll(tagSet);
        }
        mIPlayBottomView.showTaglistDialog(tagList);
    }

//    保存新标签
    public void saveNewTag(String newTagName){
        tagSet = sp.getStringSet("TagSet", null);
        if (tagSet == null) {
            tagSet=new HashSet<>();
        }
        tagSet.add(newTagName);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("TagSet");
        editor.putStringSet("TagSet", tagSet);
        editor.apply();
        tagList.clear();
        tagList.addAll(tagSet);
        mIPlayBottomView.updateTagListDialog(tagList);
    }

//    删除长按标签
    public void deleteTag(String deleteTagName){
        tagSet=sp.getStringSet("TagSet",null);
        tagSet.remove(deleteTagName);
        SharedPreferences.Editor editor=sp.edit();
        editor.remove("TagSet");
        editor.putStringSet("TagSet",tagSet);
        editor.apply();
        tagList.clear();
        tagList.addAll(tagSet);
        mIPlayBottomView.updateTagListDialog(tagList);

    }


}


