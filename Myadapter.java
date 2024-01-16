package whut.zhangzhishun.eatwhat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class Myadapter extends BaseAdapter {
    private Context mContest;
    private LayoutInflater mLayoutInflater;
    private List <Map<String,Object>>datas;
    public Myadapter(Context context , List <Map<String,Object>>datas){
        this.datas=datas;//需要绑定到控件上的数据
        this.mContest=context;
        mLayoutInflater=LayoutInflater.from(mContest);
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
        //返回再list中指定位置的数据的内容
    }

    @Override
    public long getItemId(int position) {
        return position;
        //返回数据再list中所在的位置
    }

    static class ViewHolder{
        public ImageView imageView;
        public TextView tvTitle;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view==null){
            view=mLayoutInflater.inflate(R.layout.activity_list_view_item,null);
            holder=new ViewHolder();
            holder.imageView=view.findViewById(R.id.lv_image);
            holder.tvTitle=view.findViewById(R.id.lv_title);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        //从外部传数据再给控件赋值
        holder.imageView.setImageResource((Integer)datas.get(position).get("img"));
        holder.tvTitle.setText(datas.get(position).get("title").toString());
        return view;
    }
}