package com.jingna.checklistapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.dialog.DialogQdSuccess;
import com.jingna.checklistapp.page.QdOrderDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2020/7/13.
 */

public class FragmentQiangOrderAdapter extends RecyclerView.Adapter<FragmentQiangOrderAdapter.ViewHolder> {

    private Context context;
    private List<String> data;

    public FragmentQiangOrderAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_fragment_qiang_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.btnQd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogQdSuccess dialogQdSuccess = new DialogQdSuccess(context);
                dialogQdSuccess.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, QdOrderDetailsActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private Button btnQd;

        public ViewHolder(View itemView) {
            super(itemView);
            btnQd = itemView.findViewById(R.id.btn_qd);
        }
    }

}
