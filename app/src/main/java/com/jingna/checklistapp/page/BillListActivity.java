package com.jingna.checklistapp.page;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jingna.checklistapp.R;
import com.jingna.checklistapp.adapter.BillAdapter;
import com.jingna.checklistapp.adapter.BillListAdapter;
import com.jingna.checklistapp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillListActivity extends AppCompatActivity {
    private Context context = BillListActivity.this;
    private List<String> mList;
    private BillListAdapter adapter;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.serche_order)
    Button serche_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        ButterKnife.bind(BillListActivity.this);
        initData();
    }
    public void showShareTable(final int id){
        View contentview = LayoutInflater.from(this).inflate(R.layout.activity_writeoff_info, null);
        Button btn_order = contentview.findViewById(R.id.btn_sub);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(context, id+"");
            }
        });
        ButtomDialogView dialogView=new ButtomDialogView(this,contentview,true,true);
        dialogView.show();
    }
    private void initData() {
        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        adapter = new BillListAdapter(mList, new BillListAdapter.ClickListener() {
            @Override
            public void onItemClick(int pos) {
                showShareTable(pos);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public class ButtomDialogView extends Dialog {
        private boolean iscancelable;//控制点击dialog外部是否dismiss
        private boolean isBackCancelable;//控制返回键是否dismiss
        private boolean isBackCanCelable;//
        private View view;
        private Context context;
        public ButtomDialogView(Context context, View view, boolean isCancelable,boolean isBackCancelable) {
            super(context, R.style.MyDialog);
            this.context = context;
            this.view = view;
            this.iscancelable = isCancelable;
            this.isBackCanCelable=isBackCancelable;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(view);//这行一定要写在前面
            setCancelable(iscancelable);//点击外部不可dismiss
            setCanceledOnTouchOutside(isBackCanCelable);
            Window window = this.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = 1000;//WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

    }
}
