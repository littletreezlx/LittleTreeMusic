package com.example.littletreemusic.activity.navigation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.adapter.GridViewAdapter1;
import com.example.littletreemusic.base.BaseFragment;
import com.example.littletreemusic.di.Component.navigation.DaggerNavChangeBPComponent;
import com.example.littletreemusic.di.Component.navigation.NavChangeBPModule;
import com.example.littletreemusic.pojo.StringEvent;
import com.example.littletreemusic.presenter.navigation.NavChangeBPContract;
import com.example.littletreemusic.presenter.navigation.NavFMPresenter;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NavChangeBPFragment extends BaseFragment implements NavChangeBPContract.INavChangeBPView {

    @BindView(R.id.nav_changebp_tvtitle)
    TextView tv_Title;
    @BindView(R.id.nav_changebp_btnback)
    ImageButton btn_Back;
    @BindView(R.id.nav_changebp_addfromalbum)
    Button btn_Add;
    @BindView(R.id.nav_changebp_gridview)
    GridView bpGridView;
    Unbinder unbinder;

    @Inject
    SharedPreferences sp;
    @Inject
    NavFMPresenter navFMPresenter;


    GridViewAdapter1 gridViewAdapter1;
    private int bpImages[] = new int[]{
            R.drawable.bp_0, R.drawable.bp_1, R.drawable.bp_2,
            R.drawable.bp_3, R.drawable.bp_4, R.drawable.bp_5,
            R.drawable.bp_6, R.drawable.bp_7, R.drawable.bp_8};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        DaggerNavChangeBPComponent.builder()
                .mainActivityComponent(((MainActivity) getActivity()).getMainActivityComponent())
                .navChangeBPModule(new NavChangeBPModule(this))
                .build().inject(this);
        View view = inflater.inflate(R.layout.nav_changebp, container, false);
        unbinder = ButterKnife.bind(this, view);
        gridViewAdapter1 = new GridViewAdapter1(getActivity(), bpImages);
        bpGridView.setAdapter(gridViewAdapter1);
        bpGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmDialog(position);
            }
        });
        return view;
    }

    public void showConfirmDialog(int position) {
        final int ps = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("是否选择该图片?");
        builder.setIcon(R.drawable.icon_checked);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("bp_default");
                editor.remove("bp_album");
                editor.putString("bp_default", "bp_" + ps);
                editor.apply();

                StringEvent stringEvent = new StringEvent("changebp");
                EventBus.getDefault().post(stringEvent);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }

                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {

            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

        SharedPreferences.Editor editor1 = sp.edit();
        editor1.remove("bp_album");
        editor1.remove("bp_default");
        editor1.putString("bp_album", imagePath);
        editor1.apply();
        Intent bpIntent1 = new Intent("changeBP");
        getActivity().setResult(Activity.RESULT_OK, bpIntent1);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);

        SharedPreferences.Editor editor1 = sp.edit();
        editor1.remove("bp_album");
        editor1.remove("bp_default");
        editor1.putString("bp_album", imagePath);
        editor1.apply();
        Intent bpIntent1 = new Intent("changeBP");
        getActivity().setResult(Activity.RESULT_OK, bpIntent1);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.nav_changebp_btnback, R.id.nav_changebp_addfromalbum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_changebp_btnback:
                navFMPresenter.fromNavChangeBP();
                break;
            case R.id.nav_changebp_addfromalbum:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
            default:break;
        }
    }
}
