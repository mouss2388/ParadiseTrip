package com.abdelouahad.mustapha.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.abdelouahad.mustapha.myapp.MainActivity.EXTRA_COUNTRY;

public class DescriptionActivity extends AppCompatActivity {

    TextView title,description;
    ImageView [] image =new ImageView[3];
    Button btn_choose;
    ImageButton banner;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        String travelId= getIntent().getStringExtra(EXTRA_COUNTRY);

        //Active le scrollbar du textView
        description = findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());


        banner =findViewById(R.id.banner_description);
        btn_choose =findViewById(R.id.btn_description);
        title = findViewById(R.id.title_description);
        description=findViewById(R.id.description);


        layout = findViewById(R.id.book_description);

        Toast.makeText(DescriptionActivity.this,travelId,Toast.LENGTH_LONG).show();



        final ReadData info = new ReadData();
        info.getData("TRAVEL_"+travelId,new FirebaseCallback() {

            @Override
            public void onCallback() {
                byte[] decodedString = Base64.decode(String.valueOf(info.getBanner()), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                banner.setImageBitmap(decodedByte);

                title.setText("Découvrez "+info.getCountry());
                description.setText(info.getDescription());
                showImgTravel(info);




            }
        });

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescriptionActivity.this, ChooseDateActivity.class);
                String id= String.valueOf(info.getId());
                intent.putExtra(EXTRA_COUNTRY, id);
                DescriptionActivity.this.startActivity(intent);
            }
        });


    }

    private void showImgTravel(ReadData request){

        image[0]=findViewById(R.id.id_index_book_image_1);
        image[1]=findViewById(R.id.id_index_book_image_2);
        image[2]=findViewById(R.id.id_index_book_image_3);



        byte[] decodedString=null;

        for(int i=0;i<3;i++)
        {
            Log.i("showImgTravel", request.getImg_str_1());
            switch (i){
                case 0:
                    decodedString = Base64.decode(String.valueOf(request.getImg_str_1()), Base64.NO_WRAP);
                    break;

                case 1:
                    decodedString = Base64.decode(String.valueOf(request.getImg_str_2()), Base64.NO_WRAP);
                    break;

                    default:
                    decodedString = Base64.decode(String.valueOf(request.getImg_str_3()), Base64.NO_WRAP);
                    break;

            }
            InputStream inputStream  = new ByteArrayInputStream(decodedString);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            image[i].setImageBitmap(bitmap);

            image[i].setPadding(2, 10, 8, 2);

            int size_able_layout = layout.getLayoutParams().height;
            image[i].getLayoutParams().height = size_able_layout;
            image[i].getLayoutParams().width = size_able_layout;
            image[i].requestLayout();
        }
    }
}
