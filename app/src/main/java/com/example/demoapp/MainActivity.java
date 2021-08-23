package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String[] arr = { "Background Remove","Image Retouching","Logo Design","Banner Design","Business card design"};
    AutoCompleteTextView autocomplete;
    TextView cameraBTN;
    TextInputLayout name,item_name;
    Button sendBtn;
    ImageView imageView;
//    private static final int PICK_IMAGE_REQUEST = 1888;
//    private static final int MY_PERMISSION_CONSTANT = 5;
    private Uri filepath;
    private static final int SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int MY_PERMISSION_CONSTANT =3 ;
    File output = null;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.textInputLayout);
        autocomplete=findViewById(R.id.autoCompleteText);
        item_name=findViewById(R.id.autocompleteTV);
        sendBtn=findViewById(R.id.sendBtn);
        cameraBTN=findViewById(R.id.cameraBtn);
        imageView=findViewById(R.id.imgpres);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
        cameraBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String your_name=name.getEditText().getText().toString();
                String image_editing_item=item_name.getEditText().getText().toString();
                Toast.makeText(MainActivity.this,your_name + image_editing_item,Toast.LENGTH_LONG).show();

            }
        });

    }







}