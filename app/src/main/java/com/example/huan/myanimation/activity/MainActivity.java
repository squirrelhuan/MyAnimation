package com.example.huan.myanimation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.huan.myanimation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lv_animation;
    private String[] stringList = {"水平百叶窗", "垂直百叶窗","盒状收缩","盒状展开","横向棋盘","纵向棋盘","水平梳理","垂直梳理","溶解","新闻快报"
    ,"随机水平线","随机垂直线","圆形展开","圆形关闭","菱形展开","菱形关闭","十字交叉展开","十字交叉关闭","开门效果","关门效果",
    "阶梯状左上方展开","阶梯状左下方展开","阶梯状右上方展开","阶梯状右下方展开","弹跳小球","撕裂效果","卷轴效果"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_animation = (ListView) findViewById(R.id.lv_animation);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (String name : stringList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", name);
            maps.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, maps, R.layout.layout_item, new String[]{"name"}, new int[]{R.id.tv_name});
        lv_animation.setAdapter(simpleAdapter);
        lv_animation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("AnimationType", i + 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
