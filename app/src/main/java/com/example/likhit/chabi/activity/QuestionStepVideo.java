package com.example.likhit.chabi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.VideoView;
import android.widget.MediaController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.likhit.chabi.R;

public class QuestionStepVideo extends AppCompatActivity {

    String app;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_question_step_video);
        setContentView(R.layout.steps_video_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbarVideo);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        VideoView stepsVideoView=(VideoView)findViewById(R.id.stepsvideoView);
        //ActionBar ab = getSupportActionBar();
        //ab.setDisplayHomeAsUpEnabled(true);
        Bundle mainAppListActivityData=getIntent().getExtras();

        if(mainAppListActivityData==null){
            return;
        }

        app=mainAppListActivityData.getString("app");

//        if (app.equalsIgnoreCase("youtube")){
            stepsVideoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.uoutube_01);
//        }
//        else{
            //stepsVideoView.setVideoPath("https://firebasestorage.googleapis.com/v0/b/chabi-ca490.appspot.com/o/Youtube%2FYouTube_01.mp4?alt=media&token=74d73bd2-8f6d-4b65-b57a-1ca6f5a995ba");
        //}

        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(stepsVideoView);
        stepsVideoView.setMediaController(mediaController);

        stepsVideoView.start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                // Intent i = new Intent(getApplicationContext(),MainAppListTopic.class);
                // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // startActivity(i);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            String contentAsString = readIt(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("fmt_stream_map")) {
                sb.append(line + "\n");
                break;
            }
        }
        reader.close();
        String result = decode(sb.toString());
        String[] url = result.split("\\|");
        return url[1];
    }

    public String decode(String in) {
        String working = in;
        int index;
        index = working.indexOf("\\u");
        while (index > -1) {
            int length = working.length();
            if (index > (length - 6)) break;
            int numStart = index + 2;
            int numFinish = numStart + 4;
            String substring = working.substring(numStart, numFinish);
            int number = Integer.parseInt(substring, 16);
            String stringStart = working.substring(0, index);
            String stringEnd = working.substring(numFinish);
            working = stringStart + ((char) number) + stringEnd;
            index = working.indexOf("\\u");
        }
        return working;
    }

}
