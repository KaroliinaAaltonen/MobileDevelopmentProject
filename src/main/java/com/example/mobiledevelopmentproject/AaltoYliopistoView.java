package com.example.mobiledevelopmentproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AaltoYliopistoView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aalto_yliopisto_view);

        TextView lyrics = findViewById(R.id.textViewSanat);
        EditText search_bar = findViewById(R.id.search_bar);
        ScrollView sv = (ScrollView)findViewById(R.id.scroller);

        ImageView nextResult = findViewById(R.id.nextButton);
        displayLyrics(lyrics);

        nextResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLyrics(lyrics);
                String ett =search_bar.getText().toString();
                String tvt =lyrics.getText().toString();

                int ofe = tvt.indexOf(ett,0);
                Spannable WordtoSpan = new SpannableString( lyrics.getText());

                for(int ofs=0;ofs<tvt.length() && ofe!=-1;ofs=ofe+1)
                {


                    ofe = tvt.indexOf(ett,ofs);
                    if(ofe == -1)
                        break;
                    else
                    {
                        sv.scrollTo(ofe, sv.getBottom());
                        WordtoSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), ofe, ofe+ett.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        lyrics.setText(WordtoSpan, TextView.BufferType.SPANNABLE);
                    }


                }
            }
        });

    }

    public void displayLyrics(TextView lyrics){
        String mytext = "";
        try{
            InputStream instream = getAssets().open("laulukirja_aalto");
            int size = instream.available();
            byte[] buffer = new byte[size];
            instream.read(buffer);
            mytext = new String(buffer);
            instream.close();
            lyrics.setText(mytext);
        } catch(IOException e){

        }
    }
}
