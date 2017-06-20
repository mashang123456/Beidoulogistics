package com.ue.beidoulogistics;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;
import com.ue.beidoulogistics.util.Utils;

public class DemoListActivity extends ListActivity {

    private BroadcastReceiver receiver;
    private ClassAndName[] datas = {
            new ClassAndName(MainActivity.class, "HelloBaiduMap"),

            new ClassAndName(MapLayerActivity.class, "地图图层"),
           new ClassAndName(CircelOverlayActivity.class, "圆形覆盖物"),
            new ClassAndName(TextOverlayActivity.class, "文字覆盖物"),
           new ClassAndName(MarkerOverlayActivity.class, "标志覆盖物"),
            new ClassAndName(SearchInBoundActivity.class, "在范围内搜索"),
     /*        new ClassAndName(SearchInCityActivity.class, "在城市内搜索"),
            new ClassAndName(SearchInNearbyActivity.class, "在周边内搜索"),
            new ClassAndName(DrivingSearchActivity.class, "驾车路线搜索"),
            new ClassAndName(TransitSearchActivity.class, "换乘路线搜索"),
            new ClassAndName(WalkingSearchActivity.class, "步行路线搜索"),
            new ClassAndName(LocationActivity.class, "定位"),*/
    };

    //主布局界面初始化
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerSDKCheckReceiver();
        //新建主list布局：系统布局
        ArrayAdapter<ClassAndName> adapter =
                new ArrayAdapter<DemoListActivity.ClassAndName>(this, android.R.layout.simple_list_item_1, datas);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView list, View v, int position, long id) {
        // 取出单击位置的ClassAndName:点击列表就会启用对应的活动
        ClassAndName classAndName = (ClassAndName) list.getItemAtPosition(position);
        startActivity(new Intent(this, classAndName.clazz));
    }

    class ClassAndName {
        public Class<?> clazz;  /** 用于保存Activity的字节码 */
        public String name;     /** 用于保存Activity对应的名字 */

        public ClassAndName(Class<?> cls, String name) {
            this.clazz = cls;
            this.name = name;
        }
        @Override
        public String toString() {
            return name;//返回值类
        }
    }

    private void registerSDKCheckReceiver() {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)) {
                    // showToast("网络错误");
                    Utils.showToast(DemoListActivity.this, "网络错误");
                } else if (SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)) {
                    Utils.showToast(DemoListActivity.this, "key验证失败");
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        // 监听网络错误
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        // 监听百度地图sdk 的key是否正确
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}

