package com.fan.parserdex.utils;

import com.fan.parserdex.bean.DexClass;
import com.fan.parserdex.bean.DexClassData;
import com.fan.parserdex.bean.DexCode;
import com.fan.parserdex.bean.DexFieldId;
import com.fan.parserdex.bean.DexHeader;
import com.fan.parserdex.bean.DexMethodId;
import com.fan.parserdex.bean.DexProtoId;
import com.fan.parserdex.bean.DexStringId;
import com.fan.parserdex.bean.DexTypeId;
import com.fan.parserdex.bean.EncodedField;
import com.fan.parserdex.bean.EncodedMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DexParser {

    public static int POSITION = 0;
    private Reader reader;
    private byte[] dexData;

    private DexHeader dexHeader;
    private List<DexStringId> dexStringIds = new ArrayList<>();
    private List<DexTypeId> dexTypeIds = new ArrayList<>();
    private List<DexProtoId> dexProtos = new ArrayList<>();
    private List<DexFieldId> dexFieldIds = new ArrayList<>();

    private List<DexMethodId> dexMethodIds = new ArrayList<>();
    private List<DexClass> dexClasses = new ArrayList<>();

    public DexParser(InputStream in, byte[] dexData) {
        this.dexData = dexData;
        reader = new Reader(in, true);

    }

    public void parse() {
        parseHeader();
        parseDexString();
        parseDexType();
        parseDexProto();
        parseDexField();
        parseDexMethod();
        parseDexClass();
    }

    public void parseHeader() {
        this.dexHeader = new DexHeader(reader);
        this.dexHeader.parse();
    }

    private void parseDexString() {
        LogUtils.e("\n===========parse DexStringId===========");
        try {
//            `string_ids` 是一个偏移量数组，`stringDataOff` 表示每个字符串在 data 区的偏移量。根据偏移量在 data 区拿到的数据中，
//            第一个字节表示的是字符串长度，后面跟着的才是字符串数据。这块逻辑比较简单，直接看一下代码：
            int stringIdsSize = this.dexHeader.string_ids__size;
            for (int i = 0; i < stringIdsSize; i++) {
                int string_data_off = reader.readInt();
                byte size = this.dexData[string_data_off]; // 第一个字节表示该字符串的长度，之后是字符串内容
                String string_data = new String(Utils.copy(dexData, string_data_off + 1, size));
                DexStringId string = new DexStringId(string_data_off, string_data);
                dexStringIds.add(string);
                LogUtils.e("string[%d] data: %s", i, string.string_data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexType() {
        LogUtils.e("\n===========parse DexTypeId===========");
        try {
//            `type_ids` 表示的是类型信息，`descriptorIdx` 指向 `string_ids` 中元素。根据索引直接在上一步读取到的字符串池即可解析对应的类型信息，代码如下：
            int typeIdsSize = this.dexHeader.type_ids__size;
            for (int i = 0; i < typeIdsSize; i++) {
                int descriptor_idx = reader.readInt();
                DexTypeId dexTypeId = new DexTypeId(descriptor_idx, dexStringIds.get(descriptor_idx).string_data);
                dexTypeIds.add(dexTypeId);
                LogUtils.e("type[%d] data: %s", i, dexTypeId.string_data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexProto() {
        LogUtils.e("\n===========parse DexProtoId===========");
        try {
            int protoIdsSize = this.dexHeader.proto_ids__size;
            for (int i = 0; i < protoIdsSize; i++) {
                int shorty_idx = reader.readInt();
                int return_type_idx = reader.readInt();
                int parameters_off = reader.readInt();

                DexProtoId dexProtoId = new DexProtoId(shorty_idx, return_type_idx, parameters_off);
                LogUtils.e("proto[%d]: %s %s %d", i, dexStringIds.get(shorty_idx).string_data,
                        dexTypeIds.get(return_type_idx).string_data, parameters_off);

                if (parameters_off > 0) {
                    parseDexProtoParameters(parameters_off);
                }

                dexProtos.add(dexProtoId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexProtoParameters(int parameters_off) {
        int size = TransformUtils.bytes2Int(Utils.copy(this.dexData, parameters_off, 4));
        for (int i = 0; i < size; i++) {
            int typeIdx = TransformUtils.bytes2UnsignedShort(Utils.copy(this.dexData, parameters_off + i * 2 + 4, 2));
            LogUtils.e("parameters[%d]: %s", i, dexTypeIds.get(typeIdx).string_data);
        }
    }

    private void parseDexField() {
        LogUtils.e("\n===========parse DexFieldId===========");
        try {
            int fieldIdsSize = this.dexHeader.field_ids__size;
            for (int i = 0; i < fieldIdsSize; i++) {
                int class_idx = reader.readUnsignedShort();
                int type_idx = reader.readUnsignedShort();
                int name_idx = reader.readInt();
                DexFieldId dexFieldId = new DexFieldId(class_idx, type_idx, name_idx);
//                LHello;->HELLO_WORLD:Ljava/lang/String;
                LogUtils.e("field[%d]: %s->%s;%s", i, dexTypeIds.get(class_idx).string_data,
                        dexStringIds.get(name_idx).string_data, dexTypeIds.get(type_idx).string_data);
                dexFieldIds.add(dexFieldId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexMethod() {
        LogUtils.e("\n===========parse DexMethodId===========");
        try {
            int methodIdsSize = this.dexHeader.method_ids_size;
            for (int i = 0; i < methodIdsSize; i++) {
                int class_idx = reader.readUnsignedShort();
                int proto_idx = reader.readUnsignedShort();
                int name_idx = reader.readInt();
                DexMethodId dexMethodId = new DexMethodId(class_idx, proto_idx, name_idx);
                LogUtils.e("method[%d]: %s proto[%d] %s", i, dexTypeIds.get(class_idx).string_data,
                        proto_idx, dexStringIds.get(name_idx).string_data);
                dexMethodIds.add(dexMethodId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseDexClass() {
        LogUtils.e("\n===========parse DexClass===========");
        try {
            int classDefsSize = this.dexHeader.class_defs_size;
            for (int i = 0; i < classDefsSize; i++) {
                int class_idx = reader.readInt();
                int access_flags = reader.readInt();
                int superclass_idx = reader.readInt();
                int interfaces_off = reader.readInt();
                int source_file_idx = reader.readInt();
                int annotations_off = reader.readInt();
                int class_data_off = reader.readInt();
                int staticValuesOff = reader.readInt();
                DexClass dexClass = new DexClass(class_idx, access_flags, superclass_idx,
                        interfaces_off, source_file_idx, annotations_off, class_data_off, staticValuesOff);
                LogUtils.e("class[%d]: %s", i, dexClass.toString());
                dexClasses.add(dexClass);

                LogUtils.e("classIdx: %s", dexTypeIds.get(class_idx).string_data);
                LogUtils.e("accessFlags: %d", access_flags);
                LogUtils.e("superClassIdx: %s", dexTypeIds.get(superclass_idx).string_data);
                LogUtils.e("interfaceOff: %d", interfaces_off);
                LogUtils.e("sourceFileIdx: %s", dexStringIds.get(source_file_idx).string_data);

                parseClassData(class_data_off);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseClassData(int class_data_off) {
        POSITION = class_data_off;
        int static_fields_size = Utils.readUnsignedLeb128(dexData, POSITION);
        int instance_fields_size = Utils.readUnsignedLeb128(dexData, POSITION);
        int direct_methods_size = Utils.readUnsignedLeb128(dexData, POSITION);
        int virtual_methods_size = Utils.readUnsignedLeb128(dexData, POSITION);

        DexClassData dexClassData = new DexClassData(static_fields_size, instance_fields_size, direct_methods_size, virtual_methods_size);
        LogUtils.e("classData: %s", dexClassData.toString());

        // static field
        for (int i = 0; i < static_fields_size; i++) {
            int field_idx = Utils.readUnsignedLeb128(dexData, POSITION);
            int access_flags = Utils.readUnsignedLeb128(dexData, POSITION);
            EncodedField encodedField = new EncodedField(field_idx, access_flags);
            DexFieldId dexFieldId = dexFieldIds.get(field_idx);
            LogUtils.e("static field[%d]: %s->%s;%s", i, dexTypeIds.get(dexFieldId.class_idx).string_data,
                    dexStringIds.get(dexFieldId.name_idx).string_data, dexTypeIds.get(dexFieldId.type_idx).string_data);
        }

        // instance field
        for (int i = 0; i < instance_fields_size; i++) {
            int field_idx = Utils.readUnsignedLeb128(dexData, POSITION);
            int access_flags = Utils.readUnsignedLeb128(dexData, POSITION);
            EncodedField encodedField = new EncodedField(field_idx, access_flags);
            DexFieldId dexFieldId = dexFieldIds.get(field_idx);
            LogUtils.e("   instance field[%d]: %s->%s;%s", i, dexTypeIds.get(dexFieldId.class_idx).string_data,
                    dexStringIds.get(dexFieldId.name_idx).string_data, dexTypeIds.get(dexFieldId.type_idx).string_data);
        }

        // direct method
        for (int i = 0; i < direct_methods_size; i++) {
            int method_idx = Utils.readUnsignedLeb128(dexData, POSITION);
            int access_flags = Utils.readUnsignedLeb128(dexData, POSITION);
            int code_off = Utils.readUnsignedLeb128(dexData, POSITION);

            EncodedMethod encodedMethod = new EncodedMethod(method_idx, access_flags, code_off);
            DexMethodId dexMethodId = dexMethodIds.get(method_idx);
            LogUtils.e("direct method[%d]: %s proto[%d] %s", i, dexTypeIds.get(dexMethodId.class_idx).string_data,
                    dexMethodId.proto_idx, dexStringIds.get(dexMethodId.name_idx).string_data);

            parseDexCode(code_off);
        }

        // virtual method
        for (int i = 0; i < virtual_methods_size; i++) {
            int method_idx = Utils.readUnsignedLeb128(dexData, POSITION);
            int access_flags = Utils.readUnsignedLeb128(dexData, POSITION);
            int code_off = Utils.readUnsignedLeb128(dexData, POSITION);

            EncodedMethod encodedMethod = new EncodedMethod(method_idx, access_flags, code_off);
            DexMethodId dexMethodId = dexMethodIds.get(method_idx);
            LogUtils.e("virtual method[%d]: %s proto[%d] %s", i, dexTypeIds.get(dexMethodId.class_idx).string_data,
                    dexMethodId.proto_idx, dexStringIds.get(dexMethodId.name_idx).string_data);

            parseDexCode(code_off);
        }
    }

    private void parseDexCode(int code_off) {
        int registers_size = TransformUtils.bytes2UnsignedShort(Utils.copy(dexData, code_off, 2));
        int ins_size = TransformUtils.bytes2UnsignedShort(Utils.copy(dexData, code_off + 2, 2));
        int outs_size = TransformUtils.bytes2UnsignedShort(Utils.copy(dexData, code_off + 4, 2));
        int tries_size = TransformUtils.bytes2UnsignedShort(Utils.copy(dexData, code_off + 6, 2));
        int debug_info_off = TransformUtils.bytes2Int(Utils.copy(dexData, code_off + 8, 4));
        int insns_size = TransformUtils.bytes2Int(Utils.copy(dexData, code_off + 12, 4));
        int[] insns = new int[insns_size];
        for (int i = 0; i < insns_size; i++) {
            int insns_ = TransformUtils.bytes2UnsignedShort(Utils.copy(dexData, code_off + 16 + i * 2, 2));
            insns[i] = insns_;
        }
        DexCode dexCode=new DexCode(registers_size,ins_size,outs_size,tries_size,debug_info_off,insns_size,insns);
        LogUtils.e("dexcode: %s",dexCode.toString());
    }
}
