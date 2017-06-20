package com.ue.beidoulogistics;

import android.util.Log;
import android.view.KeyEvent;

import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Beidou";

    @Override
    public void init() {
        showToast("初始化完成");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 5.	更新地图状态
        MapStatusUpdate mapStatusUpdate = null;
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                mapStatusUpdate = MapStatusUpdateFactory.zoomOut(); // 		1)	缩小
                break;
            case KeyEvent.KEYCODE_2:
                mapStatusUpdate = MapStatusUpdateFactory.zoomIn();// 		2)	放大
                break;
            case KeyEvent.KEYCODE_3:
                // 		3)	旋转（0 ~ 360），每次在原来的基础上再旋转30度
                MapStatus currentMapStatus = baiduMap.getMapStatus();	// 获取地图当前的状态
                float rotate = currentMapStatus.rotate + 30;
                Log.i(TAG, "rotate = " + rotate);
                MapStatus mapStatus = new MapStatus.Builder().rotate(rotate).build();
                mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                break;
            case KeyEvent.KEYCODE_4:
                // 		4)	俯仰（0 ~ -45），每次在原来的基础上再俯仰-5度
                currentMapStatus = baiduMap.getMapStatus();	// 获取地图当前的状态
                float overlook = currentMapStatus.overlook - 5;
                Log.i(TAG, "overlook = " + overlook);
                mapStatus = new MapStatus.Builder().overlook(overlook).build();
                mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

                break;
            case KeyEvent.KEYCODE_5:
                // 		5)	移动
                mapStatusUpdate = MapStatusUpdateFactory.newLatLng(czPos);//瞬间移动
                baiduMap.animateMapStatus(mapStatusUpdate, 2000);//以动画的方式慢慢移动
                return super.onKeyDown(keyCode, event);
        }
        baiduMap.setMapStatus(mapStatusUpdate);
        return super.onKeyDown(keyCode, event);
    }

}