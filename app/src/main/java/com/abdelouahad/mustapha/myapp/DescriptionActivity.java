package com.abdelouahad.mustapha.myapp;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DescriptionActivity extends AppCompatActivity {

    TextView description = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        String sessionCountry= getIntent().getStringExtra(MainActivity.EXTRA_COUNTRY);

        //Active le scrollbar du textView
        description = findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());

        LinearLayout layout = findViewById(R.id.trend_linear);

        Toast.makeText(DescriptionActivity.this,sessionCountry,Toast.LENGTH_LONG).show();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(2, 10, 8, 2);

            if(i%2==0)
                imageView.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.new_york));
            else
                imageView.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.paris));

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            layout.addView(imageView);
        }
    }
}
