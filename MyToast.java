package whut.zhangzhishun.eatwhat;

import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast  {

    Toast toast;
    public  MyToast(Context context, String title){

        View layout = LayoutInflater.from(context).inflate(R.layout.my_toast, null);
        LinearLayout root = layout.findViewById(R.id.ll_root);
        TextView tv_title = layout.findViewById(R.id.tv_title);
        //设置控件的宽高
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtils.dp2px(context,217),
                DensityUtils.dp2px(context,45));
        root.setLayoutParams(lp);
        //设置数据
        tv_title.setText(TextUtils.isEmpty(title)?"":title);
        //设置toast
        toast= new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        //必须设置Gravity.FILL_HORIZONTAL 这个选项，布局文件的宽高才会正常显示
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,size.y / 6);
        toast.setView(layout);

    }
    public void show(){
        toast.show();
    }

}

