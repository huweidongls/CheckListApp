package com.jingna.checklistapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jingna.checklistapp.R;

/**
 * Created by Administrator on 2020/7/13.
 */

public class DialogQdSuccess extends Dialog {

    private Context context;
    private ImageView ivClose;

    public DialogQdSuccess(@NonNull Context context) {
        super(context, R.style.RoundCornerDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_qd_success, null);
        setContentView(view);

        ivClose = view.findViewById(R.id.iv_close);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
