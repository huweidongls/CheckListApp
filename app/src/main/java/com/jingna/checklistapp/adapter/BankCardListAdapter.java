package com.jingna.checklistapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.bean.BillBean;
import com.jingna.checklistapp.bean.BnakListBean;

import java.util.List;

/**
 * Created by Administrator on 2020/1/2.
 */

public class BankCardListAdapter extends RecyclerView.Adapter<BankCardListAdapter.ViewHolder>{
    private Context context;
    private List<BnakListBean.DataBean> data;
    private ClickListener listener;
    public BankCardListAdapter(List<BnakListBean.DataBean> data,ClickListener listener){
        this.data = data;
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_bank_list, parent, false);
        BankCardListAdapter.ViewHolder holder = new BankCardListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_bank_name.setText(data.get(position).getCardName());
        String phone = data.get(position).getCardPhone();
        phone = phone.substring(phone.length()-4, phone.length());
        holder.tv_phonenum.setText("手机尾号"+phone);
        String card = data.get(position).getCardNumber();
        card = card.substring(card.length()-4, card.length());
        holder.tv_bank_card.setText(card);
        final String finalCard = card;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position,data.get(position).getCardName(),finalCard);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_bank_name;
        private TextView tv_phonenum;
        private TextView tv_bank_card;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_bank_name = itemView.findViewById(R.id.tv_bank_name);
            tv_phonenum = itemView.findViewById(R.id.tv_phonenum);
            tv_bank_card = itemView.findViewById(R.id.tv_bank_card);
        }
    }
    public interface ClickListener{
        void onItemClick(int pos, String bankName, String card);
    }
}
