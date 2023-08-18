package com.fan.parserdex.bean;

import com.fan.parserdex.utils.LogUtils;
import com.fan.parserdex.utils.Reader;
import com.fan.parserdex.utils.TransformUtils;

import java.io.IOException;

public class DexHeader {
    /*struct DexHeader{
        u1 magic[ 8];      // 魔数
        u4 checksum;      // adler 校验值
        u1 signature[ kSHA1DigestLen]; // sha1 校验值
        u4 fileSize;      // DEX 文件大小
        u4 headerSize;     // DEX 文件头大小
        u4 endianTag;     // 字节序
        u4 linkSize;      // 链接段大小
        u4 linkOff;      // 链接段的偏移量
        u4 mapOff;       // DexMapList 偏移量
        u4 stringIdsSize;   // DexStringId 个数
        u4 stringIdsOff;    // DexStringId 偏移量
        u4 typeIdsSize;    // DexTypeId 个数
        u4 typeIdsOff;     // DexTypeId 偏移量
        u4 protoIdsSize;    // DexProtoId 个数
        u4 protoIdsOff;    // DexProtoId 偏移量
        u4 fieldIdsSize;    // DexFieldId 个数
        u4 fieldIdsOff;    // DexFieldId 偏移量
        u4 methodIdsSize;   // DexMethodId 个数
        u4 methodIdsOff;    // DexMethodId 偏移量
        u4 classDefsSize;   // DexCLassDef 个数
        u4 classDefsOff;    // DexClassDef 偏移量
        u4 dataSize;      // 数据段大小
        u4 dataOff;      // 数据段偏移量
    }*/
    private Reader reader;
    public String magic;
    public long checkSum;
    public String signature;
    public int file_size;
    public int header_size;
    public int endian_tag;
    public int link_size;
    public int link_off;
    public int map_off;
    public int string_ids__size;
    public int string_ids_off;
    public int type_ids__size;
    public int type_ids_off;
    public int proto_ids__size;
    public int proto_ids_off;
    public int field_ids__size;
    public int field_ids_off;
    public int method_ids_size;
    public int method_ids_off;
    public int class_defs_size;
    public int class_defs_off;
    public int data_size;
    public int data_off;

    public DexHeader(Reader reader) {
        this.reader = reader;
    }

    public void parse() {
        try {
            LogUtils.e("\n===========parse DexHeader===========");
            this.magic = TransformUtils.bytes2String(reader.readOrigin(8));
            LogUtils.e("magic: %s", magic);

            this.checkSum = reader.readUnsignedInt();
            LogUtils.e("checkSum: %d", checkSum);

            this.signature = TransformUtils.byte2HexStr(reader.readOrigin(20));
            LogUtils.e("signature: %s", signature);

            this.file_size = reader.readInt();
            LogUtils.e("file_size: %d", file_size);

            this.header_size = reader.readInt();
            LogUtils.e("header_size: %d", header_size);

            this.endian_tag = reader.readInt();
            LogUtils.e("endian_tag: 0x%x", endian_tag);

            this.link_size = reader.readInt();
            LogUtils.e("link_size: %d", link_size);

            this.link_off = reader.readInt();
            LogUtils.e("link_off: %d", link_off);

            this.map_off = reader.readInt();
            LogUtils.e("map_off: %d", map_off);

            this.string_ids__size = reader.readInt();
            LogUtils.e("string_ids__size: %d", string_ids__size);

            this.string_ids_off = reader.readInt();
            LogUtils.e("string_ids_off: %d", string_ids_off);

            this.type_ids__size = reader.readInt();
            LogUtils.e("type_ids__size: %d", type_ids__size);

            this.type_ids_off = reader.readInt();
            LogUtils.e("type_ids_off: %d", type_ids_off);

            this.proto_ids__size = reader.readInt();
            LogUtils.e("proto_ids__size: %d", proto_ids__size);

            this.proto_ids_off = reader.readInt();
            LogUtils.e("proto_ids_off: %d", proto_ids_off);

            this.field_ids__size = reader.readInt();
            LogUtils.e("field_ids__size: %d", field_ids__size);

            this.field_ids_off = reader.readInt();
            LogUtils.e("field_ids_off: %d", field_ids_off);

            this.method_ids_size = reader.readInt();
            LogUtils.e("method_ids_size: %d", method_ids_size);

            this.method_ids_off = reader.readInt();
            LogUtils.e("method_ids_off: %d", method_ids_off);

            this.class_defs_size = reader.readInt();
            LogUtils.e("class_defs_size: %d", class_defs_size);

            this.class_defs_off = reader.readInt();
            LogUtils.e("class_defs_off: %d", class_defs_off);

            this.data_size = reader.readInt();
            LogUtils.e("data_size: %d", data_size);

            this.data_off = reader.readInt();
            LogUtils.e("data_off: %d", data_off);

        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("parse dex header error!");
        }
    }
}
