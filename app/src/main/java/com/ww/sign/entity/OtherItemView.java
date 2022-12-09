package com.ww.sign.entity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ww.sign.R;

public class OtherItemView extends RecyclerView.ViewHolder {

    public TextView tvOtherName = itemView.findViewById(R.id.tv_other_name);
    public TextView tvOtherNum = itemView.findViewById(R.id.tv_other_num);
    public LinearLayout llItemOther = itemView.findViewById(R.id.ll_item_other);

    public OtherItemView(View inflate) {
        super(inflate);
    }
}
