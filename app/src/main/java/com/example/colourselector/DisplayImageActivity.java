package com.example.colourselector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DisplayImageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    TextView textView;
    int[] coordinates = new int[2];

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        Intent intent = getIntent();
        imageView = (ImageView) findViewById(R.id.imageView);
        if (intent.hasExtra(MainActivity.EXTRA_URL)) {
            String url = intent.getStringExtra(MainActivity.EXTRA_URL);
            Glide.with(this).load(url).into(imageView);
        } else if (intent.hasExtra(MainActivity.EXTRA_BMAP)) {
            Bitmap newBmap = BitmapFactory.decodeByteArray(intent.getByteArrayExtra(MainActivity.EXTRA_BMAP), 0, intent.getByteArrayExtra(MainActivity.EXTRA_BMAP).length);
            imageView.setImageBitmap(newBmap);
        } else {
            Bitmap newCameraPicture = BitmapFactory.decodeByteArray(intent.getByteArrayExtra(MainActivity.EXTRA_CAMERAPICTURE), 0, intent.getByteArrayExtra(MainActivity.EXTRA_CAMERAPICTURE).length);
            imageView.setImageBitmap(newCameraPicture);
        }

        Button button2 = (Button) findViewById(R.id.button2);
        Button button5 = (Button) findViewById(R.id.button5);
        textView = (TextView) findViewById(R.id.textView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                Log.v("x-coordinate", Integer.toString(x));
                Log.v("y-coordinate", Integer.toString(y));

                coordinates[0] = x;
                coordinates[1] = y;

                return false;
            }
        });

        button2.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                imageView.buildDrawingCache();
                Bitmap bmapPixel = imageView.getDrawingCache();
                int pixel = bmapPixel.getPixel(coordinates[0], coordinates[1]);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                textView.setText("x = " + coordinates[0] + "\ny = " + coordinates[1] + "\nRed = " + redValue + "\nBlue = " + blueValue + "\nGreen = " + greenValue);
                break;

            case R.id.button5:
                finish();
                break;

            default:
                break;
        }

    }
}