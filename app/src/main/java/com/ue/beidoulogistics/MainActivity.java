package com.ue.beidoulogistics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import static com.baidu.mapapi.SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Beidou";
    protected LatLng whPos = new LatLng(30.549289,114.287767);/* 武汉长江大桥坐标 */
    protected LatLng hmPos = new LatLng(40.050513, 116.30361);/** 黑马坐标（北京市海淀区东北旺南路45号）*/
    protected LatLng czPos = new LatLng(40.065817,116.349902);/** 传智坐标 */
    protected LatLng tamPos = new LatLng(39.915112,116.403963);/** 天安门坐标 */

    MapView mMapView = null;
    BaiduMap baiduMap = null;
    BroadcastReceiver receiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取地图控件引用，默认显示天安门
        mMapView = (MapView) findViewById(R.id.bmapView);

        baiduMap = mMapView.getMap();//获取地图控制器
        //1.	隐藏缩放按钮、比例尺
        //mMapView.showZoomControls(false);//缩放按钮：默认显示，这里设为隐藏
        //mMapView.showScaleControl(false);//比例尺：默认显示，这里设为隐藏

        //2.	获取获取最小（3）、最大缩放级别（20）
        float maxZoomLevel = baiduMap.getMaxZoomLevel();//获取地图最大缩放级别
        float minZoomLevel = baiduMap.getMinZoomLevel();//获取地图最小缩放级别
        Log.i(TAG,"maxZoomLevel = " + maxZoomLevel + ",minZoomLevel = "+ minZoomLevel);

        //3.	设置地图中心点为黑马
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(whPos);//设置纬度,经度
        baiduMap.setMapStatus(mapStatusUpdate);

        //4.	设置地图显示级别缩放为15，显示区域比默认小一些
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15);
        baiduMap.setMapStatus(mapStatusUpdate);





    }
    //5.	更新地图状态
    //1)	缩小
    //2)	放大
    //3)	旋转（0 ~ 360），每次在原来的基础上再旋转30度
    //4)	俯仰（0 ~ -45），每次在原来的基础上再俯仰-5度
    //5)	移动
    //6.	获取地图Ui控制器：隐藏指南针
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





    //检查：注册一个广播检查网络错误和Key错误
    private void registerSDKCheckRecevier(){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)) {
                    showToast("网络错误");
                }else if(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)){
                    showToast("key验证失败");
                    System.out.println("key验证失败");
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        //监听网络错误
        filter.addAction(SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        //监听百度地图SDK的Key是否正确
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        //注册与注销要配对
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        unregisterReceiver(receiver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}