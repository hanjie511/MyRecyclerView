package com.example.hj.myrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {
    private List<String> list;
    public MyRecyclerViewAdapter(List<String> list){
        this.list=list;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.text_recyclerview_item.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyHolder extends  RecyclerView.ViewHolder{
        TextView text_recyclerview_item;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            text_recyclerview_item=itemView.findViewById(R.id.text_recyclerview_item);
        }
    }
}
