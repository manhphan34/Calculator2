package com.example.ma.myapplication.mclass;

import android.content.Context;
import android.widget.Toast;

public class Helper {
    public static void showToast(Context mContext, String s) throws RuntimeException{
        if(s != null){
            Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
        }else throw new RuntimeException("no data to show");
    }
}
