package com.example.colourselector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_BMAP = "com.example.colourselector.BMAP";
    public static final String EXTRA_CAMERAPICTURE = "com.example.colourselector.CAMERAPICTURE";
    public static final String EXTRA_URL = "com.example.colourselector.URL";
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int OPEN_DOCUMENT_CODE = 2;
    Button button, button3, button4;
    Intent photoPickerIntent, urlIntent, bmapIntent, cameraIntent, cameraPictureIntent;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
               photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
               photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
               photoPickerIntent.setType("image/*");
               startActivityForResult(photoPickerIntent, OPEN_DOCUMENT_CODE);
               break;

            case R.id.button:
                urlIntent = new Intent(this, DisplayImageActivity.class);
                editText = findViewById(R.id.editText);
                String url = editText.getText().toString();
                urlIntent.putExtra(EXTRA_URL, url);
                startActivity(urlIntent);
                break;

            case R.id.button4:
                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_DOCUMENT_CODE && resultCode == RESULT_OK){
            if(data != null) {
                try {
                    bmapIntent = new Intent(this, DisplayImageActivity.class);
                    Uri imageUri = data.getData();
                    Bitmap bmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                    bmapIntent.putExtra(EXTRA_BMAP, bs.toByteArray());
                    startActivity(bmapIntent);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null) {
                cameraPictureIntent = new Intent(this, DisplayImageActivity.class);
                Bundle extras = data.getExtras();
                Bitmap cameraPicture = (Bitmap) extras.get("data");
                ByteArrayOutputStream bs2 = new ByteArrayOutputStream();
                cameraPicture.compress(Bitmap.CompressFormat.PNG, 50, bs2);
                cameraPictureIntent.putExtra(EXTRA_CAMERAPICTURE, bs2.toByteArray());
                startActivity(cameraPictureIntent);
            }
        }
    }
}