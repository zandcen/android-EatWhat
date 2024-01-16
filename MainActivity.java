package whut.zhangzhishun.eatwhat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button mbtn1;//点击随机抽取今天吃什么
    private Button mbtn2;//重新抽
    private Button mbtn3;//点击自主选择今天吃什么
    private Button mbtn4;//自定义管理
    private Spinner spinner;
    private int  type=0;//初始数据库选择状态，0为系统内置，1为自定义
    private TextView mtv1;
    public static String str;//全局传递变量
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtn1=findViewById(R.id.btn_eatWhat);
        mbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            //实现随机吃什么
            public void onClick(View view) {
                mtv1=findViewById(R.id.textview_eat);
                if (type==0){
                    mtv1.setText(eatRandom_0());
                }else {
                    mtv1.setText(eatRandom_1());
                }
            }
        });
        mbtn2=findViewById(R.id.btn_again);
        mbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            //重新抽取
            public void onClick(View view) {
                mtv1=findViewById(R.id.textview_eat);
                if (type==0){
                    mtv1.setText(eatRandom_0());
                }else {
                    mtv1.setText(eatRandom_1());
                }
            }
        });
        mbtn3=findViewById(R.id.btn_eatWhat2);
        mbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            //跳转到选择列表界面
            public void onClick(View view) {
                if (type==0){
                    Intent intent=new Intent(MainActivity.this, ListViewActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(MainActivity.this, ListViewActivity3.class);
                    startActivity(intent);
                }
            }
        });
        //根据点击结果动态更新显示文本
        mtv1=findViewById(R.id.textview_eat);
        handler.sendEmptyMessageDelayed(0, 500);
        mbtn4=findViewById(R.id.btn_control);
        mbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            //跳转到自定义管理界面
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ListViewActivity2.class);
                startActivity(intent);
            }
        });
        spinner=findViewById(R.id.spinner_type);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=i;//根据下拉框选择内容切换状态
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type=0;
            }
        });
    }
    public String eatRandom_0() {
        //随机吃什么算法  系统内置数据
        Resources res = getResources();
        String[] array = res.getStringArray(R.array.eat_what);
        Random rand = new Random();
        int index = rand.nextInt(array.length);
        return array[index];
    }
    public String eatRandom_1() {
        //随机吃什么算法  自定义数据
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count", 0);
        Random rand = new Random();
        String string=null;
        if (count==0){
            string="请先添加自定义菜单！";
        }else {
            int index = rand.nextInt(count)+1;
            string=sharedPreferences.getString("name"+index,"出错啦~");
        }
        return string;
    }
    // 更新TextView的内容
    Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
         public boolean handleMessage(@NonNull Message message) {
                if (message.what == 0) {// 更新TextView的内容
                    if (str != null) {
                        mtv1.setText(str);
                        str=null;//防止重复更新内容
                    }
                    handler.sendEmptyMessageDelayed(0, 500);
                }
                return true;
         }
    });
}