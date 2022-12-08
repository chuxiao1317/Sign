package com.ww.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ww.sign.entity.SubItemView;

import java.util.List;

public class SubListAdapter extends RecyclerView.Adapter<SubItemView> {
    private final Context mContext;
    private final List<PackageInfo> mSubList;

    public SubListAdapter(Context context, List<PackageInfo> list) {
        mContext = context;
        mSubList = list;
    }

    @NonNull
    @Override
    public SubItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubItemView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubItemView holder, int position) {
        if (mContext == null || mSubList == null || mSubList.isEmpty()) {
            return;
        }
        String packageName = mSubList.get(position).packageName;
        String tempSHA1 = MainActivity.getSingInfo(mContext, packageName, "SHA1");
        String tempSHA256 = MainActivity.getSingInfo(mContext, packageName, "SHA256");
        holder.tvItemPackageName.setText("包名：" + packageName);
        holder.tvItemSha1.setText("sha1：" + tempSHA1);
        holder.tvItemSha256.setText("sha256：" + tempSHA256);
    }

    @Override
    public int getItemCount() {
        return mSubList == null ? 0 : mSubList.size();
    }
}
