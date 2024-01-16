package whut.zhangzhishun.eatwhat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewActivity3 extends AppCompatActivity {
    private String[] mListData ={"示例"};
    private ListView mLv1;
    private TextView mtv,mtv1;
    private ArrayAdapter<String> mAdapter = null;
    private List mList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview3);
        init();
        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //回传数据
//                Intent intent=new Intent(ListViewActivity3.this, MainActivity.class);
                View v = findView(position,mLv1);
                mtv=v.findViewById(R.id.lv_title);
                MainActivity.str= mtv.getText().toString();
//                startActivity(intent);
                finish();
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
        mAdapter = new ArrayAdapter<String>(ListViewActivity3.this, R.layout.activity_list_view_item2, mList);

        /* 获取ListView布局 */
        mLv1 = findViewById(R.id.listview3);

        /* Sets the data behind this ListView */
        mLv1.setAdapter(mAdapter);
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
}