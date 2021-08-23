package com.example.demoApp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String[] arr = { "Background Remove","Image Retouching","Logo Design","Banner Design","Business card design"};
    AutoCompleteTextView autocomplete;
    TextView cameraBTN;
    TextInputLayout name,item_name;
    Button sendBtn;
    ImageView imageView;

//    private FirebaseDatabase db = FirebaseDatabase.getInstance();
//    private DatabaseReference root = db.getReference().child("user");

    //private FirebaseStorage storage;
    StorageReference storageReference;
    String image_name;
    private DatabaseReference dref;
    String currentTime,currentDate;



//    private static final int PICK_IMAGE_REQUEST = 1888;
//    private static final int MY_PERMISSION_CONSTANT = 5;
    private Uri filepath;
    private static final int CAMERA_REQUEST = 1888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
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

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        //firebase
        storageReference=FirebaseStorage.getInstance().getReference();
        //storageReference = storage.getReference();


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String your_name=name.getEditText().getText().toString();
                String image_editing_item=item_name.getEditText().getText().toString();
                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                if (your_name.length() == 0) {
                    name.setError("Please Enter Your Medicine Name!");
                } else if (image_editing_item.length() == 0) {
                    item_name.setError("Please Enter Your Address!");
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");
                    myRef.setValue("Hello, World!");
//                    dref = FirebaseDatabase.getInstance().getReference().child("orders").child("Image_item");
////                    try {
////                        uploadImage();
////                    } catch (Exception e) {
////                        image_name = "user Does not capture prescription";
////                    }
//                    HashMap<String, Object> medCorner = new HashMap<>();
////                    medCorner.put("Image", image_name);
//                    medCorner.put("User Name", "your_name");
//                    medCorner.put("Image Item", "image_editing_item");
//                    dref.updateChildren(medCorner);
                    Toast.makeText(MainActivity.this,your_name + image_editing_item,Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }


    private void uploadImage() {
        if (filepath !=null){

            ProgressDialog progressDialog
                    = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            image_name = name.getEditText().getText().toString()+"_"+item_name.getEditText().getText().toString()+"_"+currentDate+"_"+currentTime;
            StorageReference ref = storageReference.child("imageItem/"+image_name);
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //do nothing
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress
                            = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                }
            });
        }


    }

}