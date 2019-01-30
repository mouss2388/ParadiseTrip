package com.abdelouahad.mustapha.myapp.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestDataTravel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.abdelouahad.mustapha.myapp.model.EXTRA.EXTRA_COUNTRY_ID;

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

        String travelId= getIntent().getStringExtra(EXTRA_COUNTRY_ID);

        //Active le scrollbar du textView
        description = findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());


        banner =findViewById(R.id.banner_description);
        btn_choose =findViewById(R.id.btn_description);
        title = findViewById(R.id.title_description);
        description=findViewById(R.id.description);


        layout = findViewById(R.id.book_description);

        Toast.makeText(DescriptionActivity.this,travelId,Toast.LENGTH_LONG).show();


        if (getSupportActionBar() != null) {
            ActionBar actionbar = getSupportActionBar();
            //Disable Title ToolBar
            actionbar.setDisplayShowTitleEnabled(true);
            String title = getResources().getString(R.string.app_name);
            actionbar.setTitle(title);

            //Display Arrow Back
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
        }

        final RequestDataTravel info = new RequestDataTravel();
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
                intent.putExtra(EXTRA_COUNTRY_ID, id);
                DescriptionActivity.this.startActivity(intent);
            }
        });


    }

    private void showImgTravel(RequestDataTravel request){

        image[0]=findViewById(R.id.id_index_book_image_1);
        image[1]=findViewById(R.id.id_index_book_image_2);
        image[2]=findViewById(R.id.id_index_book_image_3);



        byte[] decodedString=null;

        for(int i=0;i<3;i++)
        {
//            Log.i("showImgTravel", request.getImg_str_1());
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
            Bitmap bitmapRounded=roundCornerImage(bitmap,100f);
            image[i].setImageBitmap(bitmapRounded);

        }
    }

    public Bitmap roundCornerImage(Bitmap raw, float round) {
        int width = raw.getWidth();
        int height = raw.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#000000"));

        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.R));
        canvas.drawBitmap(raw, rect, rect, paint);

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
