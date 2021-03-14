package com.example.food_donation_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.food_donation_app.HomeActivity.EXTRA_CREATOR;
import static com.example.food_donation_app.HomeActivity.EXTRA_MOBILE;
import static com.example.food_donation_app.HomeActivity.EXTRA_URL;

public class Name extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        Intent i=getIntent();
        String imageUrl= getIntent().getStringExtra(EXTRA_URL);
        String creatorName= getIntent().getStringExtra(EXTRA_CREATOR);
        String creatorName2= getIntent().getStringExtra(EXTRA_MOBILE);

        ImageView imageView=findViewById(R.id.img);
        TextView textView=findViewById(R.id.text);
        TextView textView2=findViewById(R.id.text2);

        Picasso.with(this).load(imageUrl).fit().centerCrop().into(imageView);
        textView.setText(creatorName);
        textView2.setText(creatorName2);
    }
}