package com.example.littletreemusic.activity.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.SongListRecyclerViewAdapter;
import com.example.littletreemusic.di.Component.main.DaggerMainSongListComponent;
import com.example.littletreemusic.di.Component.main.MainSongListComponent;
import com.example.littletreemusic.di.Component.main.MainSongListModule;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.presenter.main.SongListContract;
import com.example.littletreemusic.presenter.main.SongListPresenter;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.view.SideBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class MainSongListFragment extends Fragment implements SongListContract.ISongListView{

    @BindView(R.id.fragment_universal_body_edittext)
    EditText editText;
    @BindView(R.id.fragment_universal_body_sidebar)
    SideBar sideBar;
    @BindView(R.id.fragment_universal_body_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_universal_body_textview)
    TextView textView;
    Unbinder unbinder;


    boolean isVisible;
    SongListRecyclerViewAdapter adapter;
    RelativeLayout mbodytemp;
    boolean haveSentSongs=false;
    int viewId = -1;

    @Inject
    SongListPresenter songListPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        MainSongListComponent mainSongListComponent= DaggerMainSongListComponent.builder()
                .mainActivityComponent(((MainActivity)getActivity()).getMainActivityComponent())
                .mainSongListModule(new MainSongListModule(this))
                .build();
        mainSongListComponent.inject(this);
        mainSongListComponent.inject(songListPresenter);
        View view = inflater.inflate(R.layout.fragment_main_songlist, mbodytemp, true);
        mbodytemp = (RelativeLayout) view.findViewById(R.id.main_body);
        unbinder = ButterKnife.bind(this, view);

//        EventBus.getDefault().register(this);

        init();
        return view;
    }

    public void init() {
        int mode = getArguments().getInt("mode");
        final List<Song> songList= songListPresenter.findSongListByTagPosition(mode);
        adapter = new SongListRecyclerViewAdapter(songList);
        adapter.setOnRecyclerClickListener(new SongListRecyclerViewAdapter.OnRecyclerClickListener() {
            @Override
            public void onImageClick(View view, int position) {
                Song song = new Song();
                if (view.isSelected()){
                    view.setSelected(false);
                    song.setToDefault("isFavourite");
                }else {
                    view.setSelected(true);
                    song.setIsFavourite(1);
                }
                int updateId = songList.get(position).getId();
                song.update(updateId);
            }

            @Override
            public void onTextClick(View view, int position) {
//                每次在一个新的歌曲列表界面第一次点击歌曲，则向Service传入该歌曲列表
                if (!haveSentSongs){
                    MusicService musicService=((MainActivity)getActivity()).musicService;
                    musicService.setSongList(songList);
                }
                if (viewId != -1){
                    view.findViewById(viewId).setSelected(false);
                }
               viewId=view.getId();
                view.findViewById(viewId).setSelected(true);
                Intent intent = new Intent(getActivity(), MusicService.class);
                String uri = songList.get(position).getUri();
                intent.putExtra("mode", "list");
                intent.putExtra("position", position);
                intent.putExtra("uri", uri);
                getActivity().startService(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), OrientationHelper.VERTICAL));
//       解决点击item，notifyItemChanged闪烁问题
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        //设置右侧触摸监听
        sideBar.setTextView(textView);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    recyclerView.scrollToPosition(position);
                }
            }
        });

        //根据输入框输入值的改变来过滤搜索
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                List<Song> filterDateList= songListPresenter.filterData(s.toString());
//                adapter.updateRecyclerView(filterDateList);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void showPlayingSong(int playingPosition) {
        Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();
//        adapter.notifyItemChanged(playingPosition);
    }


    //切换页面时更新播放状态
    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;

    }

    @Override
    public void onStop() {
        super.onStop();
        isVisible = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}








