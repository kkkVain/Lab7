package com.example.dell.lab7;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by dell on 2016/11/13.
 */
public class SaveDoc extends AppCompatActivity {
    //声明各控件
    private Button save;
    private Button load;
    private Button clear;
    private EditText myEdit;

    //声明文件输入输出流
    FileOutputStream outputStream;
    FileInputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document);

        //找到相关控件
        myEdit = (EditText) findViewById(R.id.myText);
        load = (Button)findViewById(R.id.myLoad);
        save = (Button)findViewById(R.id.mySave);
        clear = (Button)findViewById(R.id.secClear);
      //  File sd = Environment.getExternalStorageDirectory();
      //  boolean can_write = sd.canWrite();

      /*  if(can_write==true)
            Toast.makeText(SaveDoc.this, "001", Toast.LENGTH_SHORT).show();
        else if(can_write==false)
            Toast.makeText(SaveDoc.this, "331", Toast.LENGTH_SHORT).show();*/


        //存储数据的文件名
        final String filename = "myfile.txt";

        //点击存储按钮
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前文本框的内容
                String myFile = myEdit.getText().toString();
                //将文本内容写入文件
                writeFileData(filename, myFile);
            }
        });
        //点击加载按钮
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取文件中的数据并加载在文本框中
                String readData = readFileData(filename);
                //读取成功，弹出Toast消息
                if(!readData.equals("Fail")) {
                    Toast.makeText(SaveDoc.this, "Load successfully.", Toast.LENGTH_SHORT).show();
                    myEdit.setText(readData);
                }
                //设置光标一直位于文本的最后
                myEdit.setSelection(myEdit.getText().length());
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEdit.setText("");
            }
        });
    }
    //将字符串content写入文件filename中
     public void writeFileData(String filename, String content){
        try{
            //打开一个与应用程序关联的私有文件输入流
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            //把字符串以字节方式写入文件中
            byte[] bytes = content.getBytes();
            outputStream.write(bytes);
            //写入成功，弹出Toast消息
            Toast.makeText(SaveDoc.this,"Save successfully.", Toast.LENGTH_SHORT).show();
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFileData(String filename){
        String result="";
        try {
            //打开一个与应用程序关联的私有文件输入流
            inputStream  = openFileInput(filename);
            //获取文件长度
            int len = inputStream.available();
            byte[] contents = new byte[len];
            inputStream.read(contents);
            //将byte数组转换成指定格式的字符串
            result = new String(contents);
        } catch (Exception e) {
            Log.e("TAG","Fail to read");
            //加载失败
            Toast.makeText(SaveDoc.this,"Fail to load file.", Toast.LENGTH_SHORT).show();
            return "Fail";
        }
        //返回取出的字符串
        return result;
    }
}



