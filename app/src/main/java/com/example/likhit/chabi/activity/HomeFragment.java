package com.example.likhit.chabi.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.res.Resources;
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

public class HomeFragment extends Fragment {

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
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    //String json_url="http://192.168.0.102:5000/index/app";
    String json_url="https://chabi-ca490.firebaseio.com/app.json";

    public HomeFragment() {
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
       mList = getActivity().findViewById(R.id.main_list);
        //mList.setHasFixedSize(true);
        //mList.setLayoutManager(new LinearLayoutManager(getActivity()));
//
        appList = new ArrayList<>();
        adapter = new AppListAdapter(getActivity(),appList);

//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        gridLayoutManager=new GridLayoutManager(getActivity(),2);
        mList.addItemDecoration(new GridSpacingItemDecoration(2,dptopx(10),true));
        mList.setItemAnimator(new DefaultItemAnimator());

        mList.setHasFixedSize(true);
        //mList.setLayoutManager(linearLayoutManager);
        //mList.addItemDecoration(dividerItemDecoration);
        mList.setLayoutManager(gridLayoutManager);
        mList.setAdapter(adapter);
        getData();
        /*swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                getData();

            }
        });*/

       // mAppDatabase= FirebaseDatabase.getInstance().getReference("app");



       /* mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String app1= dataSnapshot.toString();
               DataSnapshot appData=dataSnapshot;
               Iterable<DataSnapshot> appDt=appData.getChildren();
               for(DataSnapshot ad: appDt){
                   String idiApp=ad.getKey();

               }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void setUpFirebaseAdapter() {
        Query query = FirebaseDatabase.getInstance().getReference().child("app").limitToLast(50);
        FirebaseRecyclerOptions<App> apps1=new FirebaseRecyclerOptions.Builder<App>().setQuery(mAppDatabase,App.class).build();

        mFirebaseAdapter=new FirebaseRecyclerAdapter<App,FirebaseAppListAdapter>(apps1) {
            @NonNull
            @Override
            public FirebaseAppListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_card_view,parent,false);

                return new FirebaseAppListAdapter(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull FirebaseAppListAdapter holder, int position, @NonNull App model) {
                holder.bindApp(model);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
            }

            @Override
            public void onError(@NonNull DatabaseError error) {
                super.onError(error);
                Toast.makeText(getActivity(),"ErrorFirebase",Toast.LENGTH_LONG).show();;
            }
        };


        gridLayoutManager=new GridLayoutManager(getActivity(),2);
        mList.addItemDecoration(new GridSpacingItemDecoration(2,dptopx(10),true));
        mList.setItemAnimator(new DefaultItemAnimator());

        mList.setHasFixedSize(true);
        //mList.setLayoutManager(linearLayoutManager);
        //mList.addItemDecoration(dividerItemDecoration);
        mList.setLayoutManager(gridLayoutManager);
        //mList.setAdapter(adapter);
        //mList.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseAdapter.startListening();
        mList.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        //setUpFirebaseAdapter();
        //attachRecyclerViewAdapter();
    }

    @Override
    public void onStop() {
        super.onStop();
        //mFirebaseAdapter.stopListening();
    }

    private void attachRecyclerViewAdapter() {

        final RecyclerView.Adapter adapter = newAdapter();
        mList.setAdapter(adapter);
    }

    protected RecyclerView.Adapter newAdapter() {

        Query sAppQuery = FirebaseDatabase.getInstance().getReference().child("app").limitToLast(50);
        FirebaseRecyclerOptions<App> options =
                new FirebaseRecyclerOptions.Builder<App>()
                        .setQuery(sAppQuery, App.class)
                        .setLifecycleOwner(this)
                        .build();

        mFirebaseAdapter= new FirebaseRecyclerAdapter<App,FirebaseAppListAdapter>(options) {

            @NonNull
            @Override
            public FirebaseAppListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseAppListAdapter(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_card_view,parent,false));
            }

            @Override
            protected void onBindViewHolder(@NonNull FirebaseAppListAdapter holder, int position, @NonNull App model) {
                holder.bindApp(model);
            }

            @Override
            public void onDataChanged() {
                Toast.makeText(getContext(),"DtatChanges",Toast.LENGTH_LONG).show();
            }

        };

        return  mFirebaseAdapter;

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
//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST,json_url,(String)null,
//                new Response.Listener<JSONArray>(){
//
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        for(int count=0;count<response.length();count++){
//                            try {
//                                JSONObject jsonObject=response.getJSONObject(count);
//                                AppList app=new AppList();
//                                app.setAppName(jsonObject.getString("appname"));
//                                app.setImageId(jsonObject.getInt("appid"));
//                                appList.add(app);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            adapter.notifyDataSetChanged();
//                            progressDialog.dismiss();
//                        }
//
//                    }
//                },new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//
//            }
//        }
//        );



        MySingelton.getInstance().addToRequestQueue(jsonObjectRequest);
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(jsonArrayRequest);

    }


}
