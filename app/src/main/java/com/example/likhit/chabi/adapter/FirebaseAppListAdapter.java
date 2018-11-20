package com.example.likhit.chabi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.likhit.chabi.R;
import com.example.likhit.chabi.model.App;

public class FirebaseAppListAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    View mView;
    Context mContext;
    TextView appName;

    public FirebaseAppListAdapter(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
         appName=itemView.findViewById(R.id.tv_android);
        itemView.setOnClickListener(this);
    }

    public void  bindApp(App app){

        String name=app.getAppname();
        appName.setText(name);
        //appName.setText(app.getAppname());

    }

    @Override
    public void onClick(View v) {

    }
}
