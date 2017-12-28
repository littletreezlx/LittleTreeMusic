package com.example.littletreemusic.fragment;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.RecyclerViewAdapter0;
import com.example.littletreemusic.model.Song;
import com.example.littletreemusic.pinyinpaixu.CharacterParser;
import com.example.littletreemusic.pinyinpaixu.PinyinComparator;
import com.example.littletreemusic.pinyinpaixu.SideBar;
import com.example.littletreemusic.service.MusicService;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class BodyFragment1 extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter0 recyclerViewAdapter0;
    RelativeLayout mbodytemp;
    List<Song> songs,songs_all;
    SideBar sideBar;

    TextView textView;
    EditText editText;
    CharacterParser characterParser;
    PinyinComparator pinyinComparator;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_universal_body, mbodytemp, true);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_universal_body_recycler);
        sideBar = (SideBar) view.findViewById(R.id.fragment_universal_body_sidebar);
        textView = (TextView) view.findViewById(R.id.fragment_universal_body_textview);
        editText = (EditText) view.findViewById(R.id.fragment_universal_body_edittext);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            go();
        }
        mbodytemp = (RelativeLayout) view.findViewById(R.id.body_temp);
        return view;
    }

    public void go() {
        int mode = getArguments().getInt("mode");
        switch (mode){
            case 0:
                songs = DataSupport.findAll(Song.class);
                break;
            case 1:
                songs=DataSupport.where("isFavourite = ?","1").find(Song.class);
            default:
                SharedPreferences sp_tag=getActivity().getSharedPreferences("sp_tag", Context.MODE_PRIVATE);
                List<String> tagList = new ArrayList<>();
                Set tagSet=sp_tag.getStringSet("TagSet",null);
                if (tagSet != null){
                    tagList.addAll(tagSet);
                    String tagName = tagList.get(mode-10);
                    songs_all = DataSupport.findAll(Song.class);
                    songs=new ArrayList<>();
                    for (int i=0;i<songs_all.size();i++){
                        if (songs_all.get(i).getTagList().contains(tagName)) {
                            songs.add(songs_all.get(i));
                        }
                }
            }
                break;
        }
//        recyclerViewAdapter0 = new RecyclerViewAdapter0(getContext(), songs);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(recyclerViewAdapter0);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
//
//// /      解决点击item，notifyItemChanged闪烁问题
//        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
//

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(textView);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = recyclerViewAdapter0.getPositionForSection(s.charAt(0));
                if(position != -1){
                    recyclerView.scrollToPosition(position);
                }
            }
        });

        // 根据a-z进行排序源数据
        filledData();
        Collections.sort(songs, pinyinComparator);
        recyclerViewAdapter0 = new RecyclerViewAdapter0(getActivity(), songs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter0);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
//       解决点击item，notifyItemChanged闪烁问题
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

//        把排序过的songs传给service
        ServiceConnection serviceConnection =new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService musicService=((MusicService.MusicBinder)service).getService();
                musicService.setSongList(songs);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent serviceIntent = new Intent(getActivity(), MusicService.class);
        getActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);


        //根据输入框输入值的改变来过滤搜索
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filledData(){
        for(int i=0; i<songs.size(); i++){
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(songs.get(i).getTitle());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                songs.get(i).setSortLetters(sortString.toUpperCase());
            }else{
                songs.get(i).setSortLetters("#");
            }
        }
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Song> filterDateList = new ArrayList<Song>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = songs;
        } else {
            filterDateList.clear();
            for (Song song : songs) {
                String name = song.getArtist();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(song);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        recyclerViewAdapter0.updateRecyclerView(filterDateList);
    }


    @Override
    public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                             int[] grantResults){
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    go();
                } else {
                    Toast.makeText(getContext(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


}








