package com.example.hj.mylibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HJRecyclerView  extends LinearLayout{
    private SwipeRefreshLayout refreshLayout_hj_recyclerview_layout;
    private RecyclerView recyclerView_hj_recyclerView_layout;
    private LinearLayout loading_footerView_hj_recyclerView_layout;
    private LinearLayout noData_footerView_hj_recyclerView_layout;
    private HJRefreshAndLoadMoreListener hjRefreshAndLoadMoreListener;
    private boolean isRefresh=false;
    private boolean isLoading=false;
    private boolean hasData=true;
    private RecyclerView.Adapter adapter;
    private Context context;
    public HJRecyclerView(Context context) {
        super(context);
        this.context=context;
        initView(context);
    }
    
    public HJRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView(context);
    }
    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
    public boolean isLoading() {
        return isLoading;
    }
    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
    public void setSwipeRefreshEnable(boolean enable) {
        refreshLayout_hj_recyclerview_layout.setEnabled(enable);
    }
    public void setLoading(boolean loading) {
        isLoading = loading;
    }
    public void setHjRefreshAndLoadMoreListener(HJRefreshAndLoadMoreListener hjRefreshAndLoadMoreListener){
        this.hjRefreshAndLoadMoreListener=hjRefreshAndLoadMoreListener;
    }
    private void initView(Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_layout,null,false);
        refreshLayout_hj_recyclerview_layout=view.findViewById(R.id.refreshLayout_hj_recyclerview_layout);
        recyclerView_hj_recyclerView_layout=view.findViewById(R.id.recyclerView_hj_recyclerView_layout);
        loading_footerView_hj_recyclerView_layout=view.findViewById(R.id.loading_footerView_hj_recyclerView_layout);
        noData_footerView_hj_recyclerView_layout=view.findViewById(R.id.noData_footerView_hj_recyclerView_layout);
        refreshLayout_hj_recyclerview_layout.setOnRefreshListener(new HJSwipRefreshListener(this));
        HJRecyclerViewScrollListener hjRecyclerViewScrollListener=new HJRecyclerViewScrollListener(this);
        hjRecyclerViewScrollListener.setHasDataScrollListener(new HJRecyclerViewScrollListener.HasDataScrollListener() {
            @Override
            public void scrollUp() {
                noData_footerView_hj_recyclerView_layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void scrollDown() {
                noData_footerView_hj_recyclerView_layout.setVisibility(View.GONE);
            }
        });
        recyclerView_hj_recyclerView_layout.addOnScrollListener(hjRecyclerViewScrollListener);

        addView(view);
    }
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_hj_recyclerView_layout.setLayoutManager(linearLayoutManager);
    }

    /**
     * GridLayoutManager
     */

    public void setGridLayout(int spanCount) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_hj_recyclerView_layout.setLayoutManager(gridLayoutManager);
    }


    /**
     * StaggeredGridLayoutManager
     */

    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        recyclerView_hj_recyclerView_layout.setLayoutManager(staggeredGridLayoutManager);
    }
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            recyclerView_hj_recyclerView_layout.setAdapter(adapter);
        }
    }
    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView_hj_recyclerView_layout.getLayoutManager();
    }
    public void setRefreshData(){
        hjRefreshAndLoadMoreListener.onRefresh();
    }
    public void setLoadingData(){
        if(isLoading()){
           loading_footerView_hj_recyclerView_layout.setVisibility(View.VISIBLE);
            hjRefreshAndLoadMoreListener.onLoadMore();
        }
    }
    public void setLoadingComplete(boolean hasData){
        if(isRefresh()){
            setRefresh(false);
            refreshLayout_hj_recyclerview_layout.setRefreshing(isRefresh());
            if(hasData){
                setHasData(true);
            }else{
                setHasData(false);
            }
        }
        if(isLoading()){
            setLoading(false);
            if(hasData){
                setHasData(true);
                loading_footerView_hj_recyclerView_layout.setVisibility(View.GONE);
            }else{
                setHasData(false);
                loading_footerView_hj_recyclerView_layout.setVisibility(View.GONE);
            }
        }
        invalidate();
    }
    public interface HJRefreshAndLoadMoreListener{
        void onLoadMore();
        void onRefresh();
    }
}
