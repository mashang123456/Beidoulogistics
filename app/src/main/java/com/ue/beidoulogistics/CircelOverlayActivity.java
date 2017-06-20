package com.ue.beidoulogistics;

import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.Stroke;

/**
 * Created by Administrator on 2017/6/20 0020.列表第三条圆形覆盖物
 */

public class CircelOverlayActivity extends BaseActivity {

    @Override
    public void init() {
        CircleOptions options = new CircleOptions();	// 创建一个圆形覆盖物的参数
        options.center(whPos)	// 圆心
                .radius(1000)	// 半径；单位米
                .stroke(new Stroke(20, 0x55FF0000))// 线条宽度、颜色
                .fillColor(0x5500FF00);	// 圆的填充颜色
        baiduMap.addOverlay(options);	// 添加一个覆盖物
    }

}
