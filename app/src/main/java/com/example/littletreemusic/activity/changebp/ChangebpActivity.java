package com.example.littletreemusic.activity.changebp;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.GridViewAdapter1;
import com.example.littletreemusic.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangebpActivity extends BaseActivity {

    GridViewAdapter1 gridViewAdapter1;

    @BindView(R.id.changebp_back)
    Button backButton;
    @BindView(R.id.changebp_add)
    Button addButton;
    @BindView(R.id.changebp_gridview)
    GridView bpGridView;

    private int bpImages[] = new int[]{
            R.drawable.bp_0, R.drawable.bp_1, R.drawable.bp_2,
            R.drawable.bp_3, R.drawable.bp_4, R.drawable.bp_5,
            R.drawable.bp_6, R.drawable.bp_7, R.drawable.bp_8};

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_changebp);
        ButterKnife.bind(this);
    }

    @Override
    public void onCreateBusiness() {
        gridViewAdapter1 = new GridViewAdapter1(this, bpImages);
        bpGridView.setAdapter(gridViewAdapter1);
        bpGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmDialog(position);
            }
        });
    }

    public void showConfirmDialog(int position) {

        final int ps = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否选择该图片?");
        builder.setIcon(R.drawable.icon_checked);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                editor.remove("bp_default");
                editor.remove("bp_album");
                editor.putString("bp_default", "bp_" + ps);
                editor.apply();
                Intent bpIntent = new Intent("changeBP");
                setResult(RESULT_OK, bpIntent);

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
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {
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
        if (DocumentsContract.isDocumentUri(this, uri)) {
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

        SharedPreferences.Editor editor1 = getSharedPreferences("sp", MODE_PRIVATE).edit();
        editor1.remove("bp_album");
        editor1.remove("bp_default");
        editor1.putString("bp_album", imagePath);
        editor1.apply();
        Intent bpIntent1 = new Intent("changeBP");
        setResult(RESULT_OK, bpIntent1);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);

        SharedPreferences.Editor editor1 = getSharedPreferences("sp", MODE_PRIVATE).edit();
        editor1.remove("bp_album");
        editor1.remove("bp_default");
        editor1.putString("bp_album", imagePath);
        editor1.apply();
        Intent bpIntent1 = new Intent("changeBP");
        setResult(RESULT_OK, bpIntent1);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @OnClick({R.id.changebp_back, R.id.changebp_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.changebp_back:
                finish();
                break;
            case R.id.changebp_add:
                if (ContextCompat.checkSelfPermission(ChangebpActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChangebpActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
        }
    }
}
