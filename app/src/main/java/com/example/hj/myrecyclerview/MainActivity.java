package com.example.hj.myrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hj.mylibrary.HJRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HJRecyclerView hjRecyclerView;
    private int index=0;
    private List<String> list=new ArrayList<>();
    int time=1;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hjRecyclerView=findViewById(R.id.recyclerView);
        for(int i=0;i<10;i++){
            index++;
            list.add(""+index);
        }
        myRecyclerViewAdapter=new MyRecyclerViewAdapter(list);
        hjRecyclerView.setLinearLayout();
        hjRecyclerView.setAdapter(myRecyclerViewAdapter);
        hjRecyclerView.setHjRefreshAndLoadMoreListener(new HJRecyclerView.HJRefreshAndLoadMoreListener() {
            @Override
            public void onLoadMore() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(time<=2){
                                for(int i=0;i<10;i++){
                                    index++;
                                    list.add(""+index);
                                }
                                myRecyclerViewAdapter.notifyDataSetChanged();
                            }
                            time++;
                                if(time==2){
                                    hjRecyclerView.setLoadingComplete(false);
                                }else{
                                    hjRecyclerView.setLoadingComplete(true);
                                }

                        }
                    },1500);
            }

            @Override
            public void onRefresh() {
                list.clear();
                index=0;
                for(int i=0;i<10;i++){
                    index++;
                    list.add(""+index);
                }
                myRecyclerViewAdapter.notifyDataSetChanged();
                hjRecyclerView.setLoadingComplete(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.linear:
                hjRecyclerView.setLinearLayout();
                hjRecyclerView.setAdapter(myRecyclerViewAdapter);
                break;
            case R.id.grid:
                hjRecyclerView.setGridLayout(2);
                hjRecyclerView.setAdapter(myRecyclerViewAdapter);
                break;
            case R.id.stragger:
                hjRecyclerView.setStaggeredGridLayout(3);
                hjRecyclerView.setAdapter(myRecyclerViewAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}