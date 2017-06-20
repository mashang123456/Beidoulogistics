package com.ue.beidoulogistics;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.ue.beidoulogistics.util.Utils;

public abstract class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";
    protected LatLng whPos = new LatLng(30.549289,114.287767);/* 武汉长江大桥坐标 */
    //protected LatLng hmPos = new LatLng(40.050513, 116.30361);/** 黑马坐标（北京市海淀区东北旺南路45号）*/
    protected LatLng czPos = new LatLng(40.065817,116.349902);/** 传智坐标 */
    protected LatLng tamPos = new LatLng(39.915112,116.403963);/** 天安门坐标 */

    protected MapView mMapView;
    protected BaiduMap baiduMap;

    // 这里加final是为了不让子类覆盖，原因是为了预防这里的一些类还没初始化的时候就被子类调用
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
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

        //6.	获取地图Ui控制器：隐藏指南针
        UiSettings uiSettings = baiduMap.getUiSettings();
        uiSettings.setCompassEnabled(true);	//  显示指南针

        init();
    }
    /** 这个方法让子类实现 */
    public abstract void init();

    public void showToast(CharSequence text){
        Utils.showToast(this, text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

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
