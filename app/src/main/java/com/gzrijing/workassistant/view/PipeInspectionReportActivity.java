package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeInspectionFaultAdapter;
import com.gzrijing.workassistant.entity.InspectionFault;
import com.gzrijing.workassistant.util.BitmapUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PipeInspectionReportActivity extends AppCompatActivity implements View.OnClickListener {

    private String number;
    private ListView lv_fault;
    private EditText et_remark;
    private ImageView iv_photo1;
    private ImageView iv_photo2;
    private List<InspectionFault> faults;
    private String tempPhotoPath = Environment.getExternalStorageDirectory()
            + "/workassistant/tempPhoto";
    private String photoPath1 = Environment.getExternalStorageDirectory()
            + "/workassistant/pipeInspection/photo1";
    private String photoPath2 = Environment.getExternalStorageDirectory()
            + "/workassistant/pipeInspection/photo2";
    private File tempDir;
    private File photoDir1;
    private File photoDir2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_inspection_report);

        initData();
        initViews();
        setListeners();

    }

    private void initData() {
        Intent intent = getIntent();
        number = intent.getStringExtra("number");

        initDirs();

        faults = new ArrayList<InspectionFault>();
        for (int i = 1; i < 6; i++) {
            InspectionFault fault = new InspectionFault("XXXXXX故障" + i, false);
            faults.add(fault);
        }

    }

    private void initDirs() {
        tempDir = new File(tempPhotoPath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        photoDir1 = new File(photoPath1);
        if (!photoDir1.exists()) {
            photoDir1.mkdirs();
        }
        photoDir2 = new File(photoPath2);
        if (!photoDir2.exists()) {
            photoDir2.mkdirs();
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(number);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_fault = (ListView) findViewById(R.id.pipe_inspection_report_fault_lv);
        PipeInspectionFaultAdapter adapter = new PipeInspectionFaultAdapter(this, faults);
        lv_fault.setAdapter(adapter);
        et_remark = (EditText) findViewById(R.id.pipe_inspection_report_remark_et);
        iv_photo1 = (ImageView) findViewById(R.id.pipe_inspection_report_photo_iv1);
        iv_photo2 = (ImageView) findViewById(R.id.pipe_inspection_report_photo_iv2);
    }

    private void setListeners() {
        iv_photo1.setOnClickListener(this);
        iv_photo2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pipe_inspection_report_photo_iv1:
                getPhoto(100);
                break;

            case R.id.pipe_inspection_report_photo_iv2:
                getPhoto(200);
                break;
        }
    }

    public void getPhoto(int requestCode) {
        File[] files = tempDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        Uri imageUri = Uri.fromFile(new File(tempDir, "/"
                + System.currentTimeMillis() + ".jpg"));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                try {
                    File[] files1 = photoDir1.listFiles();
                    for (int i = 0; i < files1.length; i++) {
                        files1[i].delete();
                    }
                    File[] files = tempDir.listFiles();
                    String fileName = files[0].getName();
                    Bitmap bitmap = BitmapFactory.decodeFile(tempDir + "/"
                            + fileName);
                    Bitmap newBitmap = BitmapUtil.handleBitmap(bitmap,800,600);
                    FileOutputStream fos = new FileOutputStream(photoDir1 + "/"
                            + fileName);
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 40, fos);
                    fos.close();
                    files[0].delete();
                    iv_photo1.setImageBitmap(newBitmap);
                    iv_photo2.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(requestCode == 200){
            if (resultCode == RESULT_OK) {
                try {
                    File[] files2 = photoDir2.listFiles();
                    for (int i = 0; i < files2.length; i++) {
                        files2[i].delete();
                    }
                    File[] files = tempDir.listFiles();
                    String fileName = files[0].getName();
                    Bitmap bitmap = BitmapFactory.decodeFile(tempDir + "/"
                            + fileName);
                    Bitmap newBitmap = BitmapUtil.handleBitmap(bitmap,800,600);
                    FileOutputStream fos = new FileOutputStream(photoDir2 + "/"
                            + fileName);
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 40, fos);
                    fos.close();
                    files[0].delete();
                    iv_photo2.setImageBitmap(newBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pipe_inspection_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_report) {

        }

        return super.onOptionsItemSelected(item);
    }
}
