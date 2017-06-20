package com.ue.beidoulogistics;

import android.view.KeyEvent;

import com.baidu.mapapi.map.BaiduMap;

/**
 * Created by Administrator on 2017/6/20 0020.图层
 */

public class MapLayerActivity extends BaseActivity {

    @Override
    public void init() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_1:
                // 显示普通地图
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                baiduMap.setTrafficEnabled(false);
                break;
            case KeyEvent.KEYCODE_2:
                // 显示卫星图
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                baiduMap.setTrafficEnabled(false);
                break;
            case KeyEvent.KEYCODE_3:
                // 交通图
                baiduMap.setTrafficEnabled(true);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
