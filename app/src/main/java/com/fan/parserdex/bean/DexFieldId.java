package com.fan.parserdex.bean;

import androidx.annotation.NonNull;

/**
 * Created by luyao
 * on 2018/12/18 15:49
 */
public class DexFieldId {

//    struct DexFieldId {
//        u2  classIdx;           /* index into typeIds list for defining class */
//        u2  typeIdx;            /* index into typeIds for field type */
//        u4  nameIdx;            /* index into stringIds for field name */
//    };
//    classIdx : 指向 class_idx ，表示字段所在类的信息
//    typeIdx : 指向 type_idx ，表示字段的类型信息
//    nameIdx : 指向 string_ids ，表示字段名称
    public int class_idx; // 指向 type_ids
    public int type_idx; // 指向 type_ids
    public int name_idx; // 指向 string_ids

    public DexFieldId(int class_idx, int type_idx, int name_idx) {
        this.class_idx = class_idx;
        this.type_idx = type_idx;
        this.name_idx = name_idx;
    }

    @NonNull
    @Override
    public String toString() {
        return "DexFieldId{" +
                "class_idx=" + class_idx +
                ", type_idx=" + type_idx +
                ", name_idx=" + name_idx +
                '}';
    }
}
