package com.yxw.downselect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private EditText et;
    private ImageView iv;
    private List<String> list;
    private ListView lv;
    private MyAdapter adapter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        click();
    }

    private void initView() {
        et = findViewById(R.id.et);
        iv = findViewById(R.id.iv);
        list = new ArrayList<>();
        lv = new ListView(this);
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            list.add("0000" + i);
        }
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setVerticalScrollBarEnabled(false);
    }

    /**
     * 点击事件
     */
    private void click() {
        //点击下拉箭头，popupWindow显示
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(lv, et.getWidth(), 500);
                    popupWindow.setOutsideTouchable(true);
                }
                if (!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(et);
                }
            }
        });
        //点击EditText，popupWindow隐藏
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
        //点击条目
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et.setText(list.get(position));
                popupWindow.dismiss();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View v;
            ViewHolder vh;
            if (convertView == null) {
                v = View.inflate(MainActivity.this, R.layout.item_list, null);
                vh = new ViewHolder();
                vh.tv = v.findViewById(R.id.tv);
                vh.iv = v.findViewById(R.id.iv);
                v.setTag(vh);
            } else {
                v = convertView;
                vh = (ViewHolder) v.getTag();
            }
            vh.tv.setText(list.get(position));
            vh.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    if (list.size() ==0){
                        popupWindow.dismiss();
                    }
                }
            });
            return v;
        }

        class ViewHolder {
            TextView tv;
            ImageView iv;
        }
    }
}
