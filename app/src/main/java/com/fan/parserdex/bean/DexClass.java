package com.fan.parserdex.bean;

import androidx.annotation.NonNull;

/**
 * Created by luyao
 * on 2018/12/18 16:21
 */
public class DexClass {

    //    struct DexClassDef {
//        u4  classIdx;           /* index into typeIds for this class */
//        u4  accessFlags;
//        u4  superclassIdx;      /* index into typeIds for superclass */
//        u4  interfacesOff;      /* file offset to DexTypeList */
//        u4  sourceFileIdx;      /* index into stringIds for source file name */
//        u4  annotationsOff;     /* file offset to annotations_directory_item */
//        u4  classDataOff;       /* file offset to class_data_item */
//        u4  staticValuesOff;    /* file offset to DexEncodedArray */
//    };
    //classIdx : 指向 type\_ids ，表示类信息
    //accessFlags : 访问标识符
    //superclassIdx : 指向 type\_ids ，表示父类信息
    //interfacesOff : 指向 DexTypeList 的偏移量，表示接口信息
    //sourceFileIdx : 指向 string\_ids ，表示源文件名称
    //annotationOff : 注解信息
    //classDataOff : 指向 DexClassData 的偏移量，表示类的数据部分
    //staticValueOff :指向 DexEncodedArray 的偏移量，表示类的静态数据
    public int class_idx; // 指向 type_ids
    public int access_flags;
    public int superclass_idx; // 指向 type_ids
    public int interfaces_off; // 指向 type_ids
    public int source_file_idx; // 指向 string_ids
    public int annotations_off;
    public int class_data_off; // 指向 data 区
    public int staticValuesOff;

    public DexClass(int class_idx, int access_flags, int superclass_idx, int interfaces_off, int source_file_idx, int annotations_off, int class_data_off, int staticValuesOff) {
        this.class_idx = class_idx;
        this.access_flags = access_flags;
        this.superclass_idx = superclass_idx;
        this.interfaces_off = interfaces_off;
        this.source_file_idx = source_file_idx;
        this.annotations_off = annotations_off;
        this.class_data_off = class_data_off;
        this.staticValuesOff = staticValuesOff;
    }

    @NonNull
    @Override
    public String toString() {
        return "DexClass{" +
                "class_idx=" + class_idx +
                ", access_flags=" + access_flags +
                ", superclass_idx=" + superclass_idx +
                ", interfaces_off=" + interfaces_off +
                ", source_file_idx=" + source_file_idx +
                ", annotations_off=" + annotations_off +
                ", class_data_off=" + class_data_off +
                ", staticValuesOff=" + staticValuesOff +
                '}';
    }
}
