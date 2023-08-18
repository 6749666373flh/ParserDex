package com.fan.parserdex.bean;

import androidx.annotation.NonNull;

/**
 * Created by luyao
 * on 2018/12/18 15:53
 */
public class DexMethodId {

//    struct DexMethodId {
//        u2  classIdx;           /* index into typeIds list for defining class */
//        u2  protoIdx;           /* index into protoIds for method prototype */
//        u4  nameIdx;            /* index into stringIds for method name */
//    };
//    classIdx : 指向 class_idx ，表示类的类型
//    protoIdx : 指向 type_ids ，表示方法声明
//    nameIdx : 指向 string_ids ，表示方法名
    public int class_idx; // 指向 type_ids
    public int proto_idx; // 指向 proto_ids
    public int name_idx; // 指向 string_ids

    public DexMethodId(int class_idx, int proto_idx, int name_idx) {
        this.class_idx = class_idx;
        this.proto_idx = proto_idx;
        this.name_idx = name_idx;
    }

    @NonNull
    @Override
    public String toString() {
        return "DexMethodId{" +
                "class_idx=" + class_idx +
                ", proto_idx=" + proto_idx +
                ", name_idx=" + name_idx +
                '}';
    }
}
