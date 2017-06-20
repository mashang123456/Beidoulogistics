package com.ue.beidoulogistics;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Created by Administrator on 2017/6/20 0020.范围搜索有问题 PoiOverlay不支持
 */

public class SearchInBoundActivity extends BaseActivity implements OnGetPoiSearchResultListener {
    @Override
    public void init() {
        PoiSearch poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        poiSearch.searchInBound(getSearchParams());
    }

    private PoiBoundSearchOption getSearchParams(){
        PoiBoundSearchOption params = new PoiBoundSearchOption();
        //制定搜索范围：从西南到东北
        LatLngBounds bounds = new LatLngBounds.Builder().include(new LatLng(30.549289,114.287767))
                                                        .include(new LatLng(30.559289,114.297767)) .build();
        params.bound(bounds);     //制定搜索范围
        params.keyword("加油站");//指定搜索内容
        return params;
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /** 获取兴趣点详情信息 */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

    }

    /** 获取兴趣点信息 */
    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR){
            showToast("没有搜索到结果");
            return;
        }

        //PoiOverlay poiOverlay = new PoiOverlay(baiduMap);
        //poiOverlay.setData(result);
        //baiduMap.addToMap();
    }

}
