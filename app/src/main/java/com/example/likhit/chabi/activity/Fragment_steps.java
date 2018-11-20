package com.example.likhit.chabi.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.likhit.chabi.helper.MySingelton;
import com.example.likhit.chabi.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.likhit.chabi.helper.TouchImageView;
//import com.github.chrisbanes.photoview.PhotoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class Fragment_steps extends Fragment {

    private String appName;
    private int appId;
    private int questionId;
    private int page;
    private JSONObject stps;
    private Context context;
    private LinearLayout layoutBottomsheet;
    private BottomSheetBehavior sheetBehavior;
    private Button watchVideo;
    private FloatingActionButton fab;


    //String json_url="http://192.168.0.103:5000/index/app/";

    public static Fragment_steps newInstance(int page,String appName,int appId,int questionId,String steps){
        Fragment_steps fragSteps=new Fragment_steps();
        Bundle args=new Bundle();
        args.putInt("page",page);
        args.putString("appName",appName);
        args.putInt("appId",appId);
        args.putInt("questionId",questionId);
        args.putString("steps",steps);
        fragSteps.setArguments(args);
        return fragSteps;
    }

    public Fragment_steps() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page=getArguments().getInt("page");
        appName=getArguments().getString("appName");
        appId=getArguments().getInt("appId");
        questionId=getArguments().getInt("questionId");
        String st=getArguments().getString("steps");
        try {
            stps=new JSONObject(st);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json_url=json_url+appName+"/"+questionId;
        context=getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_fragment_steps,container,false);

        //final TextView stepLabel=(TextView)view.findViewById(R.id.stepText);

        //final ImageView stepImage=(ImageView)view.findViewById(R.id.stepimage);
        final TouchImageView stepImage=(TouchImageView) view.findViewById(R.id.imgDisplay);
        stepImage.setImageResource(R.drawable.facebook101);

        layoutBottomsheet=view.findViewById(R.id.bottom_sheet);
        sheetBehavior=BottomSheetBehavior.from(layoutBottomsheet);
        sheetBehavior.setHideable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fab.getRotation() != 180){
                    fab.setRotation(180);
                }else{
                    fab.setRotation(0);
                }
                if(sheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    watchVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(context,QuestionStepVideo.class);
                            i.putExtra("app",appName);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    });
                }
                else{
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });

        watchVideo=view.findViewById(R.id.watch_video);
        //photoView=(PhotoView)view.findViewById(R.id.view_pager_image);

        final TextView bottom_detail=(TextView)view.findViewById(R.id.bottomsheet_detail);
        final TextView bottom_title=(TextView)view.findViewById(R.id.bottomsheet_title);


        int count = 0;
        while (count < stps.length()) {

            try {
                //JSONObject jsonObject = response.getJSONObject(count);
                switch (page) {
                    case 1:
                        if ((appName.equalsIgnoreCase("Youtube"))) {
                            stepImage.setImageResource(R.drawable.step1);
                        }
//                                        stepLabel.setText(//stepLabel.getText() + "\n" +
//                                                (jsonObject.getString("step1")));
                        bottom_title.setText("Step:1");
                        bottom_detail.setText(stps.getString("step1"));
                        //Glide.with(context).load(stps.getString("step1_url")).into(stepImage);
                        Glide.with(context).asBitmap().load(stps.getString("step1_url")).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                stepImage.setImageBitmap(resource);
                            }
                        });
                        break;
                    case 2:
                        if ((appName.equalsIgnoreCase("Youtube"))) {
                            stepImage.setImageResource(R.drawable.step2);
                        }
//                                        stepLabel.setText(//stepLabel.getText() + "\n" +
//                                                (jsonObject.getString("step2")));
                        bottom_title.setText("Step:2");
                        bottom_detail.setText(stps.getString("step2"));
                        //Glide.with(context).load(stps.getString("step2_url")).into(stepImage);
                        Glide.with(context).asBitmap().load(stps.getString("step2_url")).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                stepImage.setImageBitmap(resource);
                            }
                        });
                        break;
                    case 3:
                        if ((appName.equalsIgnoreCase("Youtube"))) {
                            stepImage.setImageResource(R.drawable.step3);
                        }
//                                        stepLabel.setText(//stepLabel.getText() + "\n" +
//                                                (jsonObject.getString("step3")));
                        bottom_title.setText("Step:3");
                        bottom_detail.setText(stps.getString("step3"));
                        //Glide.with(context).load(stps.getString("step3_url")).into(stepImage);
                        Glide.with(context).asBitmap().load(stps.getString("step3_url")).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                stepImage.setImageBitmap(resource);
                            }
                        });
                        break;
                    case 4:
                        if ((appName.equalsIgnoreCase("Youtube"))) {
                            stepImage.setImageResource(R.drawable.step4);
                        }
//                                        stepLabel.setText(//stepLabel.getText() + "\n" +
//                                                (jsonObject.getString("step4")));
                        bottom_title.setText("Step:4");
                        bottom_detail.setText(stps.getString("step4"));
                        //Glide.with(context).load(stps.getString("step4_url")).into(stepImage);
                        Glide.with(context).asBitmap().load(stps.getString("step4_url")).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                stepImage.setImageBitmap(resource);
                            }
                        });
                        break;
                    case 5:
                        if ((appName.equalsIgnoreCase("Youtube"))) {
                            stepImage.setImageResource(R.drawable.step5);
                        }
//                                        stepLabel.setText(//stepLabel.getText() + "\n" +
//                                                (jsonObject.getString("step5")));
                        bottom_title.setText("Step:5");
                        bottom_detail.setText(stps.getString("step5"));
                        //Glide.with(context).load(stps.getString("step5_url")).into(stepImage);
                        Glide.with(context).asBitmap().load(stps.getString("step5_url")).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                stepImage.setImageBitmap(resource);
                            }
                        });
                        break;
                    default:
//                                        stepLabel.setText(//stepLabel.getText() + "\n" +
//                                                (jsonObject.getString("step1")));
                        bottom_title.setText("Step:1");
                        bottom_detail.setText(stps.getString("step1"));
                        //Glide.with(context).load(stps.getString("step1_url")).into(stepImage);
                        Glide.with(context).asBitmap().load(stps.getString("step1_url")).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                stepImage.setImageBitmap(resource);
                            }
                        });
                        break;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            count++;
        }


        //stepLabel.setMovementMethod(new ScrollingMovementMethod());

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, (String) null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        int count = 0;
//                        while (count < response.length()) {
//
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(count);
//                                switch (page) {
//                                    case 1:
//                                        if ((appName.equalsIgnoreCase("youtube"))) {
//                                            stepImage.setImageResource(R.drawable.step1);
//                                        }
////                                        stepLabel.setText(//stepLabel.getText() + "\n" +
////                                                (jsonObject.getString("step1")));
//                                        bottom_title.setText("Step:1");
//                                        bottom_detail.setText(jsonObject.getString("step1"));
//                                        break;
//                                    case 2:
//                                        if ((appName.equalsIgnoreCase("youtube"))) {
//                                            stepImage.setImageResource(R.drawable.step2);
//                                        }
////                                        stepLabel.setText(//stepLabel.getText() + "\n" +
////                                                (jsonObject.getString("step2")));
//                                        bottom_title.setText("Step:2");
//                                        bottom_detail.setText(jsonObject.getString("step2"));
//                                        break;
//                                    case 3:
//                                        if ((appName.equalsIgnoreCase("youtube"))) {
//                                            stepImage.setImageResource(R.drawable.step3);
//                                        }
////                                        stepLabel.setText(//stepLabel.getText() + "\n" +
////                                                (jsonObject.getString("step3")));
//                                        bottom_title.setText("Step:3");
//                                        bottom_detail.setText(jsonObject.getString("step3"));
//                                        break;
//                                    case 4:
//                                        if ((appName.equalsIgnoreCase("youtube"))) {
//                                            stepImage.setImageResource(R.drawable.step4);
//                                        }
////                                        stepLabel.setText(//stepLabel.getText() + "\n" +
////                                                (jsonObject.getString("step4")));
//                                        bottom_title.setText("Step:4");
//                                        bottom_detail.setText(jsonObject.getString("step4"));
//                                        break;
//                                    case 5:
//                                        if ((appName.equalsIgnoreCase("youtube"))) {
//                                            stepImage.setImageResource(R.drawable.step5);
//                                        }
////                                        stepLabel.setText(//stepLabel.getText() + "\n" +
////                                                (jsonObject.getString("step5")));
//                                        bottom_title.setText("Step:5");
//                                        bottom_detail.setText(jsonObject.getString("step5"));
//                                        break;
//                                    default:
////                                        stepLabel.setText(//stepLabel.getText() + "\n" +
////                                                (jsonObject.getString("step1")));
//                                        bottom_title.setText("Step:1");
//                                        bottom_detail.setText(jsonObject.getString("step1"));
//                                        break;
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            count++;
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                stepLabel.setText(stepLabel.getText()+"\n"+"Error.....");
//                bottom_title.setText("Error loading data. Please Try Again");
//                Toast.makeText(context, "Error....", Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//            }
//        }
//        );
//
//        //MySingelton.getInstance().addToRequestQueue(jsonArrayRequest);
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(jsonArrayRequest);

        //stepLabel.setMovementMethod(new ScrollingMovementMethod());

        //sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

//        stepImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(sheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    watchVideo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent i = new Intent(context,QuestionStepVideo.class);
//                            i.putExtra("app",appName);
//                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(i);
//                        }
//                    });
//                }
//                else{
//                    //sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                }
//                //AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
//                //View mView = getLayoutInflater().inflate(R.layout.dialog_image_layout, null);
//                //photoView.setImageResource(R.drawable.stepImage);
//                //mBuilder.setView(mView);
//                //AlertDialog mDialog = mBuilder.create();
//                //mDialog.show();
//            }
//        });



        return view;
    }
}
