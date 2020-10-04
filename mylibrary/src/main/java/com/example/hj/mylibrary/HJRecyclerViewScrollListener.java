package com.example.hj.mylibrary;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class HJRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private HJRecyclerView hjRecyclerView;
    private HasDataScrollListener hasDataScrollListener;
    public int getLastItem() {
        return lastItem;
    }

    private int lastItem=0;
    public HJRecyclerViewScrollListener(HJRecyclerView hjRecyclerView){
        this.hjRecyclerView=hjRecyclerView;
    };
    public void setHasDataScrollListener(HasDataScrollListener hasDataScrollListener){
        this.hasDataScrollListener=hasDataScrollListener;
    }
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastItem = 0;
        int firstItem = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            firstItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
            //Position to find the final item of the current LayoutManager
            lastItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1) lastItem = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            firstItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItem == -1) lastItem = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            // since may lead to the final item has more than one StaggeredGridLayoutManager the particularity of the so here that is an array
            // this array into an array of position and then take the maximum value that is the last show the position value
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            lastItem = findMax(lastPositions);
            firstItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
        }
        if (firstItem == 0 || firstItem == RecyclerView.NO_POSITION) {
            if (!hjRecyclerView.isRefresh()){
                hjRecyclerView.setSwipeRefreshEnable(true);
            }
        } else {
            hjRecyclerView.setSwipeRefreshEnable(false);
        }
        if (hjRecyclerView.isHasData()&&!hjRecyclerView.isRefresh()
                && (lastItem == totalItemCount - 1)
                && !hjRecyclerView.isLoading()
                && (dx > 0 || dy > 0)) {
            hjRecyclerView.setLoading(true);
            hjRecyclerView.setLoadingData();
        }
        if(!hjRecyclerView.isHasData()&&dy>0&&(lastItem == totalItemCount - 1)){
            hasDataScrollListener.scrollUp();
        }else if(!hjRecyclerView.isHasData()&&dy<0){
            hasDataScrollListener.scrollDown();
        }
    }
    //To find the maximum value in the array
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    public interface HasDataScrollListener{
        void scrollUp();
        void scrollDown();
    }
}
