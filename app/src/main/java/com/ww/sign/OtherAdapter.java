package com.ww.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ww.sign.entity.OtherEntity;
import com.ww.sign.entity.OtherItemView;
import com.ww.sign.entity.SubListEntity;

import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherItemView> {
    private final Context mContext;
    private final List<OtherEntity> mOtherSignList;

    public OtherAdapter(Context context, List<OtherEntity> list) {
        mContext = context;
        mOtherSignList = list;
    }

    @NonNull
    @Override
    public OtherItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OtherItemView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_sign, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OtherItemView holder, int position) {
        if (mContext == null || mOtherSignList == null || mOtherSignList.isEmpty()) {
            return;
        }
        List<PackageInfo> typeList = mOtherSignList.get(position).typeList;
        holder.tvOtherName.setText("自动分类签名" + (position + 1));
        holder.tvOtherNum.setText(typeList.size() + "");
        if (mOtherSignList.get(position).isOtherSingle) {
            holder.tvOtherName.setText("其它独立签名");
        }
        holder.llItemOther.setOnClickListener(view -> {
            if (typeList.size() <= 0) {
                Toast.makeText(mContext, "该项无内容", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(mContext, SubListActivity.class);
            intent.putExtra(Constant.IntentTAG.SUB_LIST, new SubListEntity(typeList));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mOtherSignList == null ? 0 : mOtherSignList.size();
    }
}
