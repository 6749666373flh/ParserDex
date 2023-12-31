package com.fan.parserdex.bean;

import androidx.annotation.NonNull;

/**
 * Created by luyao
 * on 2018/12/18 15:40
 */
public class DexProtoId {

//    struct DexProtoId {
//        u4  shortyIdx;          /* index into stringIds for shorty descriptor */
//        u4  returnTypeIdx;      /* index into typeIds list for return type */
//        u4  parametersOff;      /* file offset to type_list for parameter types */
//    };

//    shortyIdx : 指向 string_ids ，表示方法声明的字符串
//    returnTypeIdx : 指向 type_ids ，表示方法的返回类型
//    parametersOff ： 方法参数列表的偏移量
    public int shorty_idx; // 指向 string_ids
    public int return_type_idx; // 指向 types_ids
    public int parameters_off;


    public DexProtoId(int shorty_idx, int return_type_idx, int parameters_off) {
        this.shorty_idx = shorty_idx;
        this.return_type_idx = return_type_idx;
        this.parameters_off = parameters_off;
    }

    @NonNull
    @Override
    public String toString() {
        return "DexProtoId{" +
                "shorty_idx=" + shorty_idx +
                ", return_type_idx=" + return_type_idx +
                ", parameters_off=" + parameters_off +
                '}';
    }
}
