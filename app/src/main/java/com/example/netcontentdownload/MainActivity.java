package com.example.netcontentdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button      button_web_page, button_web_image;
    TextView    web_textView;
    ImageView   web_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Botons
        button_web_page  = findViewById(R.id.button_web);
        button_web_image = findViewById(R.id.button_image);

        button_web_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWebPage();
            }
        });

        button_web_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWebImage();
            }
        });

        //Text
        web_textView     = findViewById(R.id.web_text);

        //Imatge
        web_imageView    = findViewById(R.id.web_image);


    }

    private void downloadWebImage() {
    }

    private void downloadWebPage() {
    }
}
