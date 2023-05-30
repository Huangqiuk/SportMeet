package com.example.administrator.sportmeet;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    EditText etPersonName,etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPersonName=(EditText) this.findViewById(R.id.etPersonName);
        etPassword=(EditText) this.findViewById(R.id.etPassword);

        btnLogin=(Button)this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                if("admin".equals(etPersonName.getText().toString()) &&
                        "123".equals(etPassword.getText().toString()))
                {
                    //提示“登录成功”，然后再跳转到第2屏
                    Toast.makeText(MainActivity.this, "登录成功",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,EntryActivity.class);
                    finish();
                    startActivity(i);
                }
                else
                {
                    //账号或密码错误，并清除EditText
                    Toast.makeText(MainActivity.this, "用户名或密码错误",Toast.LENGTH_SHORT).show();
                    etPersonName.setText("");
                    etPassword.setText("");
                }
            }});
    }
}
