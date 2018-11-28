package com.abdelouahad.mustapha.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 3/14/2017.
 */

public class CompagnyListAdapter extends ArrayAdapter<Compagny> {

    private static final String TAG = "CompagnyListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;



    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView price;
        TextView classe;
        TextView start_date, return_date;
        ImageView imageView;
    }

    /**
     * Default constructor for the CompagnyListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public CompagnyListAdapter(Context context, int resource, ArrayList<Compagny> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;


    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String price = getItem(position).getPrice();
        String classe = getItem(position).getClasse();
        String start_date = getItem(position).getStart_date();
        String return_date= getItem(position).getReturn_date();
        ImageView imageView = getItem(position).getImageView();
        String imageBase64 = getItem(position).getImageBase64();

        //Create the person object with the information
        Compagny compagny = new Compagny(price,classe,start_date,return_date,imageView, imageBase64);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.price = convertView.findViewById(R.id.price);
            holder.classe =  convertView.findViewById(R.id.classe);
            holder.start_date =  convertView.findViewById(R.id.start_date);
            holder.return_date = convertView.findViewById(R.id.return_date);
            holder.imageView = convertView.findViewById(R.id.logo);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.price.setText(compagny.getPrice());
        holder.classe.setText(compagny.getClasse());
        holder.start_date.setText(compagny.getStart_date());
        holder.return_date.setText(compagny.getReturn_date());

        byte[] decodedString = Base64.decode(String.valueOf(imageBase64), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        holder.imageView.setImageBitmap(decodedByte);


        return convertView;
    }
}