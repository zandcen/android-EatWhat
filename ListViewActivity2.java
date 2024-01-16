package whut.zhangzhishun.eatwhat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity2 extends AppCompatActivity {
    private ListView mLv1;
    public  EditText medt1;

    private final String[] mListData ={"示例"};
    private ArrayAdapter<String> mAdapter = null;
    private List mList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view2);
        //添加菜品
        Button mbtn1 = findViewById(R.id.btn_add);
        //返回
        Button mbtn2 = findViewById(R.id.btn_back);
        //重置
        Button mbtn3 = findViewById(R.id.btn_init);
        init();
        mbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AlertDialog改成Dialog解决不能弹出软键盘
                //原因不能获取焦点，使用焦点监视器可以解决，但要点两下，有些不符合常理操作
                Dialog dialog = new Dialog(ListViewActivity2.this);
                View v = LayoutInflater.from(ListViewActivity2.this).inflate(R.layout.dialog, null);
                medt1=v.findViewById(R.id.edt_text);//绑定编辑框控件
                Button btnConfirm = v.findViewById(R.id.btn_confirm);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    //实现自定义添加
                    @Override
                    public void onClick(View view1) {
                        if(!medt1.getText().toString().isEmpty()){
                            //防止添加空白
                            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            int count=sharedPreferences.getInt("count",1);
                            editor.putInt("count",++count);//先加一再存，意为共有多少有效元素
                            editor.putString("name"+count, medt1.getText().toString());
                            editor.apply();
                            mAdapter.add(medt1.getText().toString());
                            MyToast mytoast = new MyToast(ListViewActivity2.this, medt1.getText().toString() + "已添加！");
                            mytoast.show();
                            dialog.dismiss();
                        }else {
                            MyToast mytoast = new MyToast(ListViewActivity2.this,  "请先输入菜名！");
                            mytoast.show();
                        }
                    }
                });
                Button btnCancel = v.findViewById(R.id.btn_cancel1);//取消按钮
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setContentView(v);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                int screenWidth = getResources().getDisplayMetrics().widthPixels;
                int dialogWidth = (int) (screenWidth * 0.8); // 计算80%的宽度
                layoutParams.width = dialogWidth;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // 设置高度为包裹内容
                dialog.getWindow().setAttributes(layoutParams);

            }
        });
        mbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//销毁当前活动，达成返回
            }
        });
        mbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出重置提示框
                View v1 = LayoutInflater.from(ListViewActivity2.this).inflate(R.layout.dialog3, null);
                Button btnConfirm = v1.findViewById(R.id.btn_confirm3);
                AlertDialog dialog = new AlertDialog.Builder(ListViewActivity2.this).create();
                dialog.show();
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setContentView(v1);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //实现按下后重置
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clear();
                        init();
                        MyToast mytoast = new MyToast(ListViewActivity2.this, "已重置！");
                        mytoast.show();
                        dialog.dismiss();
                    }
                });
                Button btnCancel = v1.findViewById(R.id.btn_cancel3);//取消按钮
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Button btnCancel2 = v1.findViewById(R.id.btn_cancel5);//取消按钮
                btnCancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        mLv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //长按弹出删除提示框
                View v1 = LayoutInflater.from(ListViewActivity2.this).inflate(R.layout.dialog2, null);
                Button btnConfirm = v1.findViewById(R.id.btn_confirm2);
                AlertDialog dialog = new AlertDialog.Builder(ListViewActivity2.this).create();
                dialog.show();
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setContentView(v1);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //实现按下后删除item
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        int count=sharedPreferences.getInt("count",1);
                        mAdapter.remove(sharedPreferences.getString("name"+position,""));
                        MyToast mytoast = new MyToast(ListViewActivity2.this, sharedPreferences.getString("name"+position,"") + "已删除！");
                        mytoast.show();
                        editor.putInt("count",--count);
                        editor.remove("name"+ position );
                        editor.apply();
                        sort();//重新排序，防止混乱
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                Button btnCancel = v1.findViewById(R.id.btn_cancel2);//取消按钮
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Button btnCancel2 = v1.findViewById(R.id.btn_cancel4);//取消按钮
                btnCancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
    }
    private void init() {
        /* 注意： 一定要将String[]转成List类型，否则不能动态增加和删除Item */
        mList = new ArrayList<>(Arrays.asList(mListData));
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        int count = 0;
        if(sharedPreferences.contains("count")) {
            count = sharedPreferences.getInt("count", 0);
        }else {
            //没有count，说明是第一次进入，就创造个count=0和name0=示例
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("count", 0);
            editor.putString("name0", "示例");
            editor.apply();
        }
        if (count > 0) {
            for (int i = 1; i <= count; i++) {
               mList.add(sharedPreferences.getString("name" + i, ""));
            }
        }
        /* 创建适配器实例 */
        mAdapter = new ArrayAdapter<String>(ListViewActivity2.this, R.layout.activity_list_view_item2, mList);
        /* 获取ListView布局 */
        mLv1 = findViewById(R.id.listview2);
        /* Sets the data behind this ListView */
        mLv1.setAdapter(mAdapter);
    }
    private void clear(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mList = new ArrayList<>(Arrays.asList(mListData));
        mAdapter = new ArrayAdapter<String>(ListViewActivity2.this, android.R.layout.simple_list_item_1, mList);
        mLv1 = findViewById(R.id.listview2);
        mLv1.setAdapter(mAdapter);
    }
    private void sort(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count", 0);
        if(count==0){
            finish();
            //没有元素了就退出当前活动，防止出错，下次进入自动初始化
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=1;i<=count;i++){
            if (!sharedPreferences.contains("name"+i)){
                //检测哪一位被删除了，并让后一位补上，重新排序
                //如果删得是最后一位，那么就当前不存在空位，还是有序的，无需额外考虑
                for (int I=i;I<=count;I++) {
                    int e=I+1;
                    editor.putString("name"+I, sharedPreferences.getString("name"+e, "出现此项说明程序错误请重置！！！"));
                    editor.apply();
                }
                break;
            }
        }
    }
}
