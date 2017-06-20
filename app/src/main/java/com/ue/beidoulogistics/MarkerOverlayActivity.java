package com.ue.beidoulogistics;

import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

public class MarkerOverlayActivity extends BaseActivity {
    private View pop;
    private TextView tv_title;

    @Override
    public void init() {
        initMarker();
        baiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        baiduMap.setOnMarkerDragListener(mOnMarkerDragListener);
    }

    //点击图标显示提示信息pop
    BaiduMap.OnMarkerClickListener mOnMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            // 显示一个泡泡
            if (pop == null) {
                pop = View.inflate(MarkerOverlayActivity.this, R.layout.pop, null);
                tv_title = (TextView) pop.findViewById(R.id.tv_title);
                mMapView.addView(pop, createLayoutParams(marker.getPosition()));
            } else {
                mMapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
            }
            tv_title.setText(marker.getTitle());
            return true;
        }
    };

    /** 标志拖动监听器 */
    BaiduMap.OnMarkerDragListener mOnMarkerDragListener = new BaiduMap.OnMarkerDragListener() {
        /** 标志开始拖动 */
        @Override
        public void onMarkerDragStart(Marker marker) {
            mMapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
        }

        /** 标志拖动结束 */
        @Override
        public void onMarkerDragEnd(Marker marker) {
            mMapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
        }

        /** 标志正在拖动 */
        @Override
        public void onMarkerDrag(Marker marker) {
            mMapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
        }
    };

    /** 初始化标志 */
    private void initMarker() {
        MarkerOptions options = new MarkerOptions();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_eat);
        options.position(whPos)		// 位置
                .title("黑马")		// title
                .icon(icon)			// 图标
                .draggable(true);	// 设置图标可以拖动

        baiduMap.addOverlay(options);

        // 添加一个向北的标志
        options = new MarkerOptions().icon(icon)
                .title("向北")
                .position(new LatLng(whPos.latitude + 0.001, whPos.longitude))
                .draggable(true);
        baiduMap.addOverlay(options);

        // 添加一个向东的标志
        options = new MarkerOptions().icon(icon)
                .title("向东")
                .position(new LatLng(whPos.latitude, whPos.longitude + 0.001))
                .draggable(true);
        baiduMap.addOverlay(options);

        // 添加一个向西南的标志
        options = new MarkerOptions().icon(icon)
                .title("向西南")
                .position(new LatLng(whPos.latitude - 0.001, whPos.longitude - 0.001))
                .draggable(true);
        baiduMap.addOverlay(options);
    }

    /**
     * 创建一个布局参数
     * @param position
     * @return
     */
    private MapViewLayoutParams createLayoutParams(LatLng position) {
        MapViewLayoutParams.Builder buidler = new MapViewLayoutParams.Builder();
        buidler.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);	// 指定坐标类型为经纬度
        buidler.position(position);		// 设置标志的位置
        buidler.yOffset(-25);			// 设置View往上偏移
        MapViewLayoutParams params = buidler.build();
        return params;
    }

}