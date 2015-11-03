package com.gzrijing.workassistant.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.User;
import com.gzrijing.workassistant.service.LoginService;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_pwd;
    private Button btn_login;
    private ImageView iv_user;
    private ImageView iv_pwd;
    private ImageView iv_memory;
    private boolean flag;
    private String isMemory; // isMemory变量用来判断SharedPreferences有没有数据
    private String file = "saveUserAndPwd";// 用于保存SharedPreferences的文件
    private SharedPreferences sp; // 声明一个SharedPreferences

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            User user = (User) msg.obj;
            if (user != null) {
                String userNo = user.getUserNo();
                if(userNo != null && !userNo.equals("Error")){
                    SharedPreferences app = getSharedPreferences("saveUser", MODE_PRIVATE);
                    Editor edit = app.edit();
                    edit.putString("userNo", user.getUserNo());
                    edit.putString("userName", user.getUserName());
                    edit.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("fragId", "0");
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "欢迎" + user.getUserName() + "登录",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "用户账号与密码不匹配",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this, "与服务器断开连接",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initData();
        setListeners();
    }

    private void initData() {
        getUserNamePwd();
    }

    private void getUserNamePwd() {
        sp = getSharedPreferences(file, MODE_PRIVATE);
        isMemory = sp.getString("isMemory", "no");
        // 进入界面时，这个if用来判断SharedPreferences里面user和pwd有没有数据，
        // 有的话则直接打在EditText上面
        if (isMemory.equals("yes")) {
            String user = sp.getString("user", "");
            String pwd = sp.getString("pwd", "");
            et_user.setText(user);
            et_pwd.setText(pwd);
            iv_memory.setImageResource(R.drawable.login_checkbox_on);
            flag = true;
        }
    }

    private void initViews() {
        et_user = (EditText) findViewById(R.id.login_user_et);
        iv_user = (ImageView) findViewById(R.id.login_user_iv);
        et_pwd = (EditText) findViewById(R.id.login_password_et);
        iv_pwd = (ImageView) findViewById(R.id.login_password_iv);
        btn_login = (Button) findViewById(R.id.login_login_btn);
        iv_memory = (ImageView) findViewById(R.id.login_checkbox_iv);
    }

    private void setListeners() {
        btn_login.setOnClickListener(this);
        iv_memory.setOnClickListener(this);

        et_user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iv_user.setImageResource(R.drawable.login_user_on);
                } else {
                    iv_user.setImageResource(R.drawable.login_user_off);
                }
            }
        });

        et_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iv_pwd.setImageResource(R.drawable.login_pwd_on);
                } else {
                    iv_pwd.setImageResource(R.drawable.login_pwd_off);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_btn:
                login();
                break;

            case R.id.login_checkbox_iv:
                if (flag) {
                    iv_memory.setImageResource(R.drawable.login_checkbox_off);
                } else {
                    iv_memory.setImageResource(R.drawable.login_checkbox_on);
                }
                flag = !flag;
                break;
        }
    }

    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userName = et_user.getText().toString().trim();
                String password = et_pwd.getText().toString().trim();
                LoginService service = new LoginService();
                User user = service.login(userName, password);
                Message msg = handler.obtainMessage();
                msg.obj = user;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    protected void onStop() {
        remember();
        super.onStop();
    }

    // remember方法用于判断是否记住密码，checkBox选中时，提取出EditText里面的内容，
    // 放到SharedPreferences里面的user和pwd中
    private void remember() {
        if (flag) {
            if (sp == null) {
                sp = getSharedPreferences(file, MODE_PRIVATE);
            }
            Editor edit = sp.edit();
            edit.putString("user", et_user.getText().toString().trim());
            edit.putString("pwd", et_pwd.getText().toString().trim());
            edit.putString("isMemory", "yes");
            edit.commit();
        } else {
            if (sp == null) {
                sp = getSharedPreferences(file, MODE_PRIVATE);
            }
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("isMemory", "no");
            edit.commit();
        }
    }

}
