package com.gzrijing.workassistant.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ReportProgressGriViewAdapter;
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.service.ReportProgressService;
import com.gzrijing.workassistant.util.ImageUtils;

import java.util.ArrayList;

public class ReportProgressFragment extends Fragment implements View.OnClickListener {

    private View layoutView;
    private String orderId;
    private String userNo;
    private EditText et_content;
    private Button btn_report;
    private GridView gv_image;
    private ArrayList<PicUrl> picUrls = new ArrayList<PicUrl>();
    private ReportProgressGriViewAdapter adapter;
    private Uri imageUri;

    public ReportProgressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        SharedPreferences app = getActivity().getSharedPreferences(
                "saveUser", getActivity().MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getActivity().getIntent();
        orderId = intent.getStringExtra("orderId");

        PicUrl picUrl = new PicUrl();
        picUrls.add(picUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layoutView = inflater.inflate(R.layout.fragment_report_progress, container, false);

        initViews();
        setListeners();

        return layoutView;
    }

    private void initViews() {
        et_content = (EditText) layoutView.findViewById(R.id.fragment_report_progress_content_et);
        btn_report = (Button) layoutView.findViewById(R.id.fragment_report_progress_report_btn);

        gv_image = (GridView) layoutView.findViewById(R.id.fragment_report_progress_image_gv);
        adapter = new ReportProgressGriViewAdapter(getActivity(), picUrls);
        gv_image.setAdapter(adapter);
    }

    private void setListeners() {
        btn_report.setOnClickListener(this);

        gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position + 1 == picUrls.size()) {
                    showImagePickDialog();
                }
            }
        });
    }

    /**
     * 显示获取照片不同方式对话框
     */
    private void showImagePickDialog() {
        String title = "选择获取图片方式";
        String[] items = new String[]{"拍照", "相册"};
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                pickImageFromCamera();
                                break;
                            case 1:
                                pickImageFromAlbum();
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * 打开本地相册选取图片
     */
    private void pickImageFromAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_FROM_ALBUM);
    }

    /**
     * 打开相机拍照获取图片
     */
    private void pickImageFromCamera() {
        imageUri = ImageUtils.createImageUri(getActivity());

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_FROM_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("uri", "ok");
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_FROM_CAMERA:
                if (resultCode == getActivity().RESULT_CANCELED) {
                    ImageUtils.deleteImageUri(getActivity(), imageUri);
                    return;
                }
                String path = ImageUtils.getPicPath(getActivity(), imageUri);
                PicUrl picUrl = new PicUrl();
                picUrl.setPicUrl(path);
                picUrls.add(picUrl);
                adapter.notifyDataSetChanged();
                break;

            case ImageUtils.REQUEST_CODE_FROM_ALBUM:
                if (resultCode == getActivity().RESULT_CANCELED) {
                    return;
                }
                Uri imageUri = data.getData();
                String path1 = ImageUtils.getPicPath(getActivity(), imageUri);
                PicUrl picUrl1 = new PicUrl();
                picUrl1.setPicUrl(path1);
                picUrls.add(picUrl1);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_report_progress_report_btn:
                String content = et_content.getText().toString().trim();

                Intent intent = new Intent(getActivity(), ReportProgressService.class);
                intent.putParcelableArrayListExtra("picUrls", picUrls);
                intent.putExtra("userNo", userNo);
                intent.putExtra("orderId", orderId);
                intent.putExtra("content", content);
                startActivityForResult(intent, 10);
                break;
        }
    }
}
