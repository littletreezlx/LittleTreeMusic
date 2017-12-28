package com.example.littletreemusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.MainActivity;
import com.example.littletreemusic.adapter.GridViewAdapter0;


/**
 * Created by ZLX Vincent on 2017/8/30.
 */

public class BodyFragment0 extends Fragment {

    GridViewAdapter0 gridViewAdapter0;
    GridView gridView;
    private String[] titles = new String[]{"音乐", "最爱", "分类", "文件"};
    private int[] images = new int[]{R.drawable.icon_note1, R.drawable.icon_favorites, R.drawable.icon_folder2, R.drawable.icon_folder};
    RelativeLayout mbodytemp;
    Fragment title0;
    Fragment title1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_main_body,mbodytemp,true);
        gridView = (GridView) view.findViewById(R.id.fragment_body_gridview);
        gridViewAdapter0 = new GridViewAdapter0(this.getActivity(),titles, images);
        gridView.setAdapter(gridViewAdapter0);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity activity = (MainActivity) getActivity();
                switch (position) {
                    case 0:
                        activity.toSongList(0);
                        break;
                    case 1:
                        activity.toSongList(1);
                        break;
                    case 2:
                        activity.toMyTags();
                        break;
                    case 3:
                        Toast.makeText(getActivity(),"待添加",Toast.LENGTH_SHORT).show();

                    default:
                        break;
                }
            }
        });
        mbodytemp=(RelativeLayout)view.findViewById(R.id.body_temp);
        return view;
    }


}





