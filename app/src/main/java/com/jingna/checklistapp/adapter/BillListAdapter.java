package com.jingna.checklistapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingna.checklistapp.R;

import java.util.List;

/**
 * Created by Administrator on 2019/12/27.
 */

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
    private ClickListener listener;
    public BillListAdapter(List<String> data,ClickListener listener){
        this.data = data;
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_list_bill, parent, false);
        BillListAdapter.ViewHolder holder = new BillListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    public interface ClickListener{
        void onItemClick(int pos);
    }
}
