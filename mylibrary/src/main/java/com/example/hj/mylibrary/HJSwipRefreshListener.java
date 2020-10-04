package com.example.hj.mylibrary;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HJSwipRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
    private HJRecyclerView hjRecyclerView;
    public HJSwipRefreshListener(HJRecyclerView hjRecyclerView){
        this.hjRecyclerView=hjRecyclerView;
    }
    @Override
    public void onRefresh() {
        if(!hjRecyclerView.isRefresh()){
            hjRecyclerView.setRefresh(true);
            hjRecyclerView.setRefreshData();
        }
    }
}
