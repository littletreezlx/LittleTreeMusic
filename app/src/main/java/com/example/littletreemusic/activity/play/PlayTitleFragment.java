package com.example.littletreemusic.activity.play;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.di.Component.play.DaggerPlayTitleComponent;
import com.example.littletreemusic.di.Component.play.PlayTitleComponent;
import com.example.littletreemusic.di.Component.play.PlayTitleModule;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.presenter.play.PlayTitleContract;
import com.example.littletreemusic.presenter.play.PlayTitlePresenter;
import com.example.littletreemusic.service.MusicService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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

public class PlayTitleFragment extends Fragment implements PlayTitleContract.IPlayTitleView {

    Unbinder unbinder;
    @Inject
    PlayTitlePresenter playTitlePresenter;

    @BindView(R.id.play_title_titletv)
    TextView tv_Title;
    @BindView(R.id.play_title_artisttv)
    TextView tv_Artist;
    @BindView(R.id.play_title_tagstv)
    TextView tv_Tags;
    @BindView(R.id.play_title_back)
    ImageButton btn_Back;

    RelativeLayout mtitletemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        PlayTitleComponent playTitleComponent = DaggerPlayTitleComponent.builder()
                .playActivityComponent(((PlayActivity) getActivity()).getPlayActivityComponent())
                .playTitleModule(new PlayTitleModule(this))
                .build();
        playTitleComponent.inject(this);
        playTitleComponent.inject(playTitlePresenter);
        View view = inflater.inflate(R.layout.play_title, mtitletemp, true);
        mtitletemp = (RelativeLayout) view.findViewById(R.id.play_title);
        unbinder = ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.play_title_back)
    public void onViewClicked() {
        getActivity().finish();
    }

    public void updataTags() {
        String uristr = MusicService.playingUriStr;
        List<Song> list0 = DataSupport.where("uri=?", uristr).find(Song.class);
        if (list0 != null && list0.size() != 0) {
            List<String> playingTags = list0.get(0).getTagList();
            tv_Tags.setText("");
            for (String t : playingTags) {
                tv_Tags.append("  " + t);
            }
        }
    }


    @Subscribe
    public void onEvent(Song song) {
        tv_Title.setText(song.getTitle());
        tv_Artist.setText(song.getArtist());
        tv_Tags.setText("");
        for (String tag : song.getTagList()) {
            tv_Tags.append("  " + tag);
        }
    }


}
