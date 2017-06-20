package com.ue.beidoulogistics;

import com.baidu.mapapi.map.TextOptions;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class TextOverlayActivity extends BaseActivity {

    @Override
    public void init() {
        TextOptions options = new TextOptions();
        options.position(whPos)			// 位置
                .text("武汉长江大桥")			// 文字内容
                .fontSize(35)			// 文字大小
                .fontColor(0XFF000000)	// 文字颜色
                .bgColor(0X55FF0000)	// 背景颜色
                .rotate(30);			// 旋转：可以不用旋转
        baiduMap.addOverlay(options);
    }

}
