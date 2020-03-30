package com.example.netcontentdownload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.mbms.MbmsDownloadSessionCallback;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.Reader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    Button      button_web_page, button_web_image;
    TextView    web_textView;
    ImageView   web_imageView;
    EditText    url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Botons
        button_web_page  = findViewById(R.id.button_web);
        button_web_image = findViewById(R.id.button_image);

        //Edit Text
        url = findViewById(R.id.editUrl);

        button_web_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new downloadWebContent().execute(url.getText().toString());
            }
        });

        button_web_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new downloadWebImage().execute(url.getText().toString());
            }
        });

        //Text
        web_textView     = findViewById(R.id.web_text);

        //Imatge
        web_imageView    = findViewById(R.id.web_image);

        TestNetConnection();
    }

    private void TestNetConnection()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null) {
            //textView1.setText(networkInfo.toString());
            if (networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    Toast.makeText(this, "Wifi connected!", Toast.LENGTH_SHORT).show();
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Toast.makeText(this, "Mobile connected!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class downloadWebContent extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return String.valueOf(R.string.invalidUrl);
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            web_textView.setText(result);
        }


        private String downloadUrl (String argUrl) throws IOException
        {
            InputStream is = null;
            int len = 20;

            try
            {
                URL url = new URL(argUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000); //miliseconds
                conn.setConnectTimeout(15000); //miliseconds
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();
                int response = conn.getResponseCode();
                //Log.d(DEBUG_TAG, );
                is = conn.getInputStream();

                String contentAsString = readStream(is, len);

                return contentAsString;
            }
            finally
            {
                if (is != null)
                {
                    is.close();
                }
            }
        }

        public String readStream(InputStream stream, int maxReadSize) throws IOException, UnsupportedEncodingException
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            String ret = "";
            String line ;
            //read content of the file line by line
            while((line = br.readLine())!=null && maxReadSize > 0){

                ret += line + "\n";

                maxReadSize --;
        }
            return ret;
        }
    }

    private class downloadWebImage extends AsyncTask<String, Bitmap, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {

            return downloadImage(urls[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            web_imageView.setImageBitmap(result);
        }

        private Bitmap downloadImage(String argUrl) {
            Bitmap bitmap = null;
            try {
           // Download Image from URL
                InputStream input = new java.net.URL(argUrl).openStream();
           // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
