package com.example.mallshop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MyUtil {
    public static void loadImg(Context context, String url, ImageView imageView){
        if (!TextUtils.isEmpty(url))  Glide.with(context).load(url).into(imageView);
    }
    public static void setText(TextView textView,String text){
        if (!TextUtils.isEmpty(text)) textView.setText(text);
    }

    public static void toast(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
