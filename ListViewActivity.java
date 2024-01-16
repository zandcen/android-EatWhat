package whut.zhangzhishun.eatwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {
    private ListView mLv1;
    private TextView mtv;

    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initData();//载入数据
        mLv1 = findViewById(R.id.listview1);
        mLv1.setAdapter(new Myadapter(ListViewActivity.this, list));
        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //回传数据
//                Intent intent=new Intent(ListViewActivity.this, MainActivity.class);
                View v = findView(position,mLv1);
                mtv=v.findViewById(R.id.lv_title);
                MainActivity.str=mtv.getText().toString();
//                startActivity(intent);
                finish();
            }
        });
    }
    //  得到每一个不同的Item所对应的 View
    private View findView(int position, ListView listView) {
        int firstListItemPosition = listView.getFirstVisiblePosition();
        int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    //自定义数据
    private void initData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv1);
        map.put("title", "小炒牛肉");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv2);
        map.put("title", "回锅肉");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv3);
        map.put("title", "豆腐蒸鸡腿");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv4);
        map.put("title", "红肠炒牛心菜");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv5);
        map.put("title", "酸菜咸梅焖乌鱼");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv6);
        map.put("title", "黑椒牛肉煲");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv7);
        map.put("title", "蒜蓉粉丝年糕蒸纽龙");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv8);
        map.put("title", "红烧大骨");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv9);
        map.put("title", "芹菜豆腐");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv10);
        map.put("title", "茄汁虾球");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv11);
        map.put("title", "圆椒拌笔管鱼");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv12);
        map.put("title", "牛肉哨子豆腐");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv13);
        map.put("title", "肉丝炒芥兰");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv14);
        map.put("title", "清蒸鲈鱼");
        list.add(map);;
        map = new HashMap<String, Object>();
        map.put("img", R.drawable.lv15);
        map.put("title", "雪里红炒豆腐");
        list.add(map);;
    }
}
