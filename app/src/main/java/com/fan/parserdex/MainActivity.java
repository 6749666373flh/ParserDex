package com.fan.parserdex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fan.parserdex.utils.DexParser;
import com.fan.parserdex.utils.DexUtil;
import com.fan.parserdex.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    private static String HELLO_WORLD = "Hello World!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        test();
        try { // 读取assets目录下的Hello.dex文件
            DexParser dexParser = new DexParser(getAssets().open("Hello.dex"), DexUtil.getAssetsDexBytes("Hello.dex"));
            dexParser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test() {
        System.out.println("test");
        LogUtils.e("test");
    }
}