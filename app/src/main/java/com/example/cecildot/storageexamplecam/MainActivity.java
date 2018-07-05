package com.example.cecildot.storageexamplecam;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
Button btn,selectbtn;
ImageView img;
File imagesrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder =new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        imagesrc = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"profilepic.png");
        btn = (Button) findViewById(R.id.btncamera);
        img = (ImageView) findViewById(R.id.imgview);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri pathuri = Uri.fromFile(imagesrc);
                capture.putExtra(MediaStore.EXTRA_OUTPUT, pathuri);
                startActivityForResult(capture,123);
                }catch (Exception ex){
                    Log.v("camera_exp",ex.getMessage()+ex.getCause());
                }
            }

        });
        selectbtn = (Button) findViewById(R.id.select);
        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callgallary = new Intent(Intent.ACTION_PICK);
                callgallary.setType("image/*");
                startActivityForResult(Intent.createChooser(callgallary,"select pic From"),222);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK){
            Toast.makeText(this, "image Saved", Toast.LENGTH_SHORT).show();
            try{
                FileInputStream fis = new FileInputStream(imagesrc);
                Bitmap bmp = BitmapFactory.decodeStream(fis);
                img.setImageBitmap(bmp);
            }catch (Exception ex){

            }
        }
        if(requestCode==222 && resultCode==RESULT_OK){
            Uri selectedimage = data.getData();
            ContentResolver resolver = getContentResolver();
            try {
                InputStream is = resolver.openInputStream(selectedimage);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                img.setImageBitmap(bmp);
            }catch (Exception ex){

            }

        }
    }
}
