package com.example.littletreemusic.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.RecyclerViewAdapter0;
import com.example.littletreemusic.table.Song;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class BodyFragment1 extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter0 recyclerViewAdapter0;
    RelativeLayout mbodytemp;
    List<Song> songs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_universal_body, mbodytemp, true);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_universal_body_recycler);

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


            default:break;
        }

        recyclerViewAdapter0 = new RecyclerViewAdapter0(getContext(), songs);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter0);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

//      解决点击item，notifyItemChanged闪烁问题
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);



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



