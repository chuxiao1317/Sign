package com.ww.sign.entity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ww.sign.R;

public class SubItemView extends RecyclerView.ViewHolder {

    public TextView tvItemPackageName = itemView.findViewById(R.id.tv_item_package_name);
    public TextView tvItemSha1 = itemView.findViewById(R.id.tv_item_sha1);
    public TextView tvItemSha256 = itemView.findViewById(R.id.tv_item_sha256);

    public SubItemView(View inflate) {
        super(inflate);
    }
}
