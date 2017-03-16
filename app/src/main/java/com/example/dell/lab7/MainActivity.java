package com.example.dell.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText pass;
    private EditText passCon;
    private Button ok;
    private Button clear;

    //定义访问模式
    public static  int MODE = MODE_PRIVATE;
    //定义SharedPreferences的名称
    public static  final String PREFERENCE_NAME = "SavePass";
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到相关控件
        pass = (EditText)findViewById(R.id.myPas);
        passCon = (EditText)findViewById(R.id.myPasCon);
        ok = (Button)findViewById(R.id.myOk);
        clear = (Button)findViewById(R.id.myClear);

        //根据模式和名称创建sharedPreferences对象
        final SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME,MODE);

        //取得当前sharedPreferences对象的Boolean值，默认为true
        isFirst=sharedPreferences.getBoolean("first",true);
     //   Toast.makeText(MainActivity.this,String.valueOf(isFirst), Toast.LENGTH_SHORT).show();

        //如果是第一次进入应用
        if(isFirst) {
            // 点击ok按钮
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //没有输入新密码
                    if (pass.getText().toString().length() == 0) {
                        Toast.makeText(MainActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                    //两次输入密码不相同
                    else if (!pass.getText().toString().equals(passCon.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Password Mismatch.", Toast.LENGTH_SHORT).show();
                    }
                    //两次输入密码相同
                    else if (pass.getText().toString().equals(passCon.getText().toString())) {
                        String pas = pass.getText().toString();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //将密码存入sharedPreferences对象
                        editor.putString("Pas", pas);
                        //这里置为了false，所以除非再put，否则一直为false
                        editor.putBoolean("first",false);
                        //isFirst=sharedPreferences.getBoolean("first",true);
                        //Toast.makeText(MainActivity.this,String.valueOf(isFirst), Toast.LENGTH_SHORT).show();
                        //采用异步方式写入文件
                        editor.apply();
                        //然后进入文件编辑页面
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, SaveDoc.class);
                        MainActivity.this.startActivity(intent);
                    }
                }
            });
            //点击clear按钮，清空文本框
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pass.setText("");
                    passCon.setText("");
                }
            });
        }
        // 如果不是第一次进入应用
        // 且密码设置成功
        else{
            // 隐藏一个文本框，并改变另一个文本框的HINT
            pass.setVisibility(View.GONE);
            passCon.setHint("Password");
            // 点击OK按钮
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 取出sharedPrefereces中保存的密码
                    String correctPa = sharedPreferences.getString("Pas","");
                    //Toast.makeText(MainActivity.this,correctPa, Toast.LENGTH_SHORT).show();
                    // 获得当前输入的密码
                    String inputPa = passCon.getText().toString();
                    //如果两次文本相同，跳转如文本编辑页面
                    if(inputPa.equals(correctPa)){
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, SaveDoc.class);
                        MainActivity.this.startActivity(intent);
                    }
                    //否则提示密码不正确
                    else{
                        Toast.makeText(MainActivity.this,"Invalid Password.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
