package com.jingna.checklistapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.bean.BillBean;

import java.util.List;

/**
 * Created by Administrator on 2019/12/27.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>{
    private Context context;
    private List<BillBean.DataBean> data;
    public BillAdapter(List<BillBean.DataBean> data) {
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_bill_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(data.get(position).getOperatingDescribe());
        holder.tv_money.setText("￥"+data.get(position).getOperatingRecord());
        holder.tv_time.setText(data.get(position).getCreateTime());
        holder.tv_user_money.setText("余额"+data.get(position).getBalance());
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_money;
        private TextView tv_time;
        private TextView tv_user_money;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_user_money = itemView.findViewById(R.id.tv_user_money);
        }
    }
}
