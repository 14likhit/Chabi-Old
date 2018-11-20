package com.example.likhit.chabi.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.likhit.chabi.adapter.FirebaseAppListAdapter;
import com.example.likhit.chabi.helper.MySingelton;
import com.example.likhit.chabi.R;
import com.example.likhit.chabi.adapter.AppListAdapter;
import com.example.likhit.chabi.adapter.AppListSearchAdapter;
import com.example.likhit.chabi.model.App;
import com.example.likhit.chabi.model.AppList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentFirebase extends Fragment {

    private RecyclerView mList;
    private Toolbar toolbar;
    //private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<AppList> appList;
    private AppListAdapter adapter;
    private AppListSearchAdapter searchAdapter;

    private DatabaseReference mAppDatabase;
    private FirebaseRecyclerAdapter<App,HomeFragmentFirebase.AppViewHolder> mAppAdapter;

    //String json_url="http://192.168.0.102:5000/index/app";
    String json_url="https://chabi-ca490.firebaseio.com/app.json";

    public HomeFragmentFirebase() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_home,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //swipeRefreshLayout=getActivity().findViewById(R.id.simpleSwipeRefreshLayout);
        //setHasOptionsMenu(true);
        toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);

        initCollapsingToolbar();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        mAppDatabase=database.getReference().child("DataApp");
//        mAppDatabase=FirebaseDatabase.getInstance().getReference().child("DataApp");
//        mAppDatabase.getDatabase().setPersistenceEnabled(true);
        mAppDatabase.keepSynced(true);

        mList = (RecyclerView) getActivity().findViewById(R.id.main_list);
        //mList.setHasFixedSize(true);
        //mList.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference appRef=FirebaseDatabase.getInstance().getReference().child("DataApp");
        Query appQuery=appRef.orderByKey();

        mList.hasFixedSize();
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions appOptions=new FirebaseRecyclerOptions.Builder<App>().setQuery(appQuery,App.class).build();

        mAppAdapter=new FirebaseRecyclerAdapter<App, AppViewHolder>(appOptions) {
            @Override
            protected void onBindViewHolder(@NonNull AppViewHolder holder, int position, @NonNull App model) {
                holder.setAppName(model.getAppname());
                holder.setAppId("3.5");
            }

            @NonNull
            @Override
            public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_card_view,parent,false);
                return new HomeFragmentFirebase.AppViewHolder(v);
            }
        };

        mList.setAdapter(mAppAdapter);
//
//        appList = new ArrayList<>();
//        adapter = new AppListAdapter(getActivity(),appList);
//
////        linearLayoutManager = new LinearLayoutManager(getActivity());
////        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
//
//        gridLayoutManager=new GridLayoutManager(getActivity(),2);
//        mList.addItemDecoration(new GridSpacingItemDecoration(2,dptopx(10),true));
//        mList.setItemAnimator(new DefaultItemAnimator());
//
//        mList.setHasFixedSize(true);
//        //mList.setLayoutManager(linearLayoutManager);
//        //mList.addItemDecoration(dividerItemDecoration);
//        mList.setLayoutManager(gridLayoutManager);
//        mList.setAdapter(adapter);
//        getData();
    }


    public static class AppViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public AppViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setAppName(String appN) {
            TextView appName = (TextView)itemView.findViewById(R.id.tv_android);
            appName.setText(appN);
        }
        public void setAppId(String appd){
            TextView appId=(TextView)itemView.findViewById(R.id.appId);
            appId.setText(appd);
        }

        public void setAppLogo(){
            ImageView appLogo=(ImageView) itemView.findViewById(R.id.img_android);
        }

        public void setThumbnail(){
            ImageView thumbnail=(ImageView) itemView.findViewById(R.id.thumbnail);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        mAppAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAppAdapter.stopListening();
    }


    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout=getActivity().findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout=getActivity().findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow=false;
            int scrollRange= -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    isShow=true;
                }
                else if(isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    isShow=false;
                }

            }
        });

    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount,int spacing,boolean includeEdge){
            this.spanCount=spanCount;
            this.spacing=spacing;
            this.includeEdge=includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position=parent.getChildAdapterPosition(view);
            int column= position%spanCount;

            if(includeEdge){
                outRect.left = spacing- column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if(position < spanCount){
                    outRect.top = spacing;
                }

                outRect.bottom = spacing;
            }
            else{
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if(position >= spanCount){
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dptopx(int dp){
        Resources r=getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics()));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        SearchManager searchManager=(SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if (id==R.id.action_settings){
            return false;
        }

        if (id==R.id.action_search){
            return  true;
        }

        //return super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getData(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, json_url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        for (int i = 1; i <= response.length(); i++) {
                            try {
                                JSONObject ob = response.getJSONObject("app"+i);
                                AppList app=new AppList();
                                app.setAppName(ob.getString("appname"));
                                app.setImageId(100+i);
                                app.setAppQuestion(ob.getJSONObject("questions"));
                                JSONObject job=app.getAppQuestion();
                                appList.add(app);
                                //int leng = response.length();
                                //Toast.makeText(getActivity(), Integer.toString(leng), Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }

        );

        MySingelton.getInstance().addToRequestQueue(jsonObjectRequest);

    }


}
