package com.abdelouahad.mustapha.myapp.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.abdelouahad.mustapha.myapp.R;
import com.abdelouahad.mustapha.myapp.model.FirebaseCallback;
import com.abdelouahad.mustapha.myapp.model.RequestPromotions;

public class Promotions extends AppCompatActivity {

    ImageView []imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);

        imgView =new ImageView[10];
        final RequestPromotions [] reqPromo = new RequestPromotions[imgView.length];



        for (int i =0;i<imgView.length;i++) {
            int resourceId = this.getResources().getIdentifier("promo_"+(i+1), "id", this.getPackageName());
            imgView[i] = findViewById(resourceId);

            final int fnlI = i;
            reqPromo[i] = new RequestPromotions();
            reqPromo[i].getData("PROMOTIONS", (fnlI+1), new FirebaseCallback() {
                @Override
                public void onCallback() {
                    byte[] decodedString = Base64.decode(String.valueOf(reqPromo[fnlI].getImg()), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgView[fnlI].setImageBitmap(roundCornerImage(decodedByte,50f));
                }
            });
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
}
