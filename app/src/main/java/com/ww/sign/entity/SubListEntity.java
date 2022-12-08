package com.ww.sign.entity;

import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SubListEntity implements Parcelable {
    public List<PackageInfo> subList;

    public SubListEntity(List<PackageInfo> list) {
        subList = list;
    }

    protected SubListEntity(Parcel in) {
        subList = in.createTypedArrayList(PackageInfo.CREATOR);
    }

    public static final Creator<SubListEntity> CREATOR = new Creator<SubListEntity>() {
        @Override
        public SubListEntity createFromParcel(Parcel in) {
            return new SubListEntity(in);
        }

        @Override
        public SubListEntity[] newArray(int size) {
            return new SubListEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(subList);
    }
}
