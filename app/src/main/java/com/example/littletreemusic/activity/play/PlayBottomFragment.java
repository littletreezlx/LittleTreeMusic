package com.example.littletreemusic.activity.play;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.TagListViewAdapter;
import com.example.littletreemusic.di.Component.play.DaggerPlayBottomComponent;
import com.example.littletreemusic.di.Component.play.PlayBottomComponent;
import com.example.littletreemusic.di.Component.play.PlayBottomModule;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.pojo.StringEvent;
import com.example.littletreemusic.presenter.play.PlayBottomContract;
import com.example.littletreemusic.presenter.play.PlayBottomPresenter;
import com.example.littletreemusic.service.MusicService;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class PlayBottomFragment extends Fragment implements PlayBottomContract.IPlayBottomView {

    PercentRelativeLayout mbottomtemp;

    @BindView(R.id.play_bottom_tag)
    ImageButton btn_Tag;
    @BindView(R.id.play_bottom_previous)
    ImageButton btn_Previous;
    @BindView(R.id.play_bottom_pause)
    ImageButton btn_Pause;
    @BindView(R.id.play_bottom_play)
    ImageButton btn_Start;
    @BindView(R.id.play_bottom_next)
    ImageButton btn_Next;
    @BindView(R.id.play_bottom_mode)
    ImageButton btn_Mode;
    Unbinder unbinder;

    @Inject
    PlayBottomPresenter playBottomPresenter;
    @Inject
    MusicService musicService;

    TagListViewAdapter adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        PlayBottomComponent playBottomComponent = DaggerPlayBottomComponent.builder()
                .playActivityComponent(((PlayActivity) getActivity()).getPlayActivityComponent())
                .playBottomModule(new PlayBottomModule(this))
                .build();
        playBottomComponent.inject(this);
        playBottomComponent.inject(playBottomPresenter);
        View view = inflater.inflate(R.layout.fragment_play_bottom, mbottomtemp, true);
        mbottomtemp = (PercentRelativeLayout) view.findViewById(R.id.play_bottom);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
//        updateTATB();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.play_bottom_tag, R.id.play_bottom_previous, R.id.play_bottom_pause, R.id.play_bottom_play, R.id.play_bottom_next, R.id.play_bottom_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_bottom_tag:
                playBottomPresenter.findTagList();
                break;
            case R.id.play_bottom_previous:
                musicService.toNextSong(0);
                break;
            case R.id.play_bottom_pause:
                musicService.pause();
//                btn_Play.setVisibility(View.VISIBLE);
//                btn_Pause.setVisibility(View.GONE);
                break;
            case R.id.play_bottom_play:
                musicService.start();
//                btn_Play.setVisibility(View.GONE);
//                btn_Pause.setVisibility(View.VISIBLE);
                break;
            case R.id.play_bottom_next:
                musicService.toNextSong(1);
                break;
            case R.id.play_bottom_mode:
                break;
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(Song song) {
//        btn_Start.setVisibility(View.GONE);
//        btn_Pause.setVisibility(View.VISIBLE);
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent stringEvent) {
        switch (stringEvent.getStr()){
            case "start":
                btn_Start.setVisibility(View.GONE);
                btn_Pause.setVisibility(View.VISIBLE);
                break;
            case "pause":
                btn_Start.setVisibility(View.VISIBLE);
                btn_Pause.setVisibility(View.GONE);
                break;
            default:break;
        }
    }

//    private void updateTATB() {
//        if (MusicService.isPlaying) {
//            btn_Play.setVisibility(View.GONE);
//            btn_Pause.setVisibility(View.VISIBLE);
//        } else {
//            btn_Play.setVisibility(View.VISIBLE);
//            btn_Pause.setVisibility(View.GONE);
//        }
//    }


    @Override
    public void showTaglistDialog(List<String> tagList){
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.listview_tag, (ViewGroup)getActivity().findViewById(R.id.listview_tag));
        Button addTagBtn = (Button) layout.findViewById(R.id.add_tag);
        Button applyTagBtn = (Button) layout.findViewById(R.id.apply_tag);
        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        applyTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });
        listView = (ListView) layout.findViewById(R.id.mylistview);
        adapter = new TagListViewAdapter(getActivity(), tagList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String deleteTagName = adapter.getItem(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("是否删除该标签?");
                builder.setIcon(R.drawable.icon_checked);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playBottomPresenter.deleteTag(deleteTagName);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                return false;
            }
        });
        TagListViewAdapter.adcheckedTags.clear();
        AlertDialog.Builder builder0= new AlertDialog.Builder(getActivity());
        builder0.setView(layout).create().show();
    }


    //    显示添加新标签的对话框
    public void showInputDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle("添加新标签");
        builder1.setMessage("请添加新标签");
        final EditText et = new EditText(getActivity());
        et.setSingleLine();
        et.setHint("标签");
        builder1.setView(et);
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTagName = et.getText().toString().trim();
                if ("".equals(newTagName)) {
                    Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    playBottomPresenter.saveNewTag(newTagName);
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder1.create().show();
    }

    //    显示确定应用新标签的对话框
    public void showConfirmDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
        builder3.setTitle("标签");
        builder3.setMessage("确认添加选中标签吗？");
        builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Song playingSong = DataSupport.where("uri=?", MusicService.playingUriStr).find(Song.class).get(0);
                int updateId = playingSong.getId();
                Song updateSong = new Song();
                updateSong.setTagList(TagListViewAdapter.adcheckedTags);
                updateSong.update(updateId);

                SparseBooleanArray array = listView.getCheckedItemPositions();
                for (int x = 0; x < array.size(); x++) {
                    int key = array.keyAt(x);
                    boolean b = array.get(key);
                    if(b){
                        //key指的是该item在listview中的position
                        System.out.println(key);
                    }
                }
                String str="updateTags";
                EventBus.getDefault().post(str);


            }
        });
        builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder3.create().show();
    }


    @Override
    public void updateTagListDialog(List<String> tagList){
        adapter.updateTagList(tagList);
    }





}
