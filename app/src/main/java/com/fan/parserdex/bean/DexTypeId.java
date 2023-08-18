package com.fan.parserdex.bean;

import androidx.annotation.NonNull;

/**
 * Created by luyao
 * on 2018/12/19 10:09
 */
public class DexTypeId {

    /*
      struct DexTypeId {
        u4  descriptorIdx;
      };
     */

    public int descriptor_idx; // 指向 string_ids 中的内容
    public String string_data;

    public DexTypeId(int descriptor_idx, String string_data) {
        this.descriptor_idx = descriptor_idx;
        this.string_data = string_data;
    }

    @NonNull
    @Override
    public String toString() {
        return "DexTypeId{" +
                "descriptor_idx=" + descriptor_idx +
                ", string_data='" + string_data + '\'' +
                '}';
    }
}
