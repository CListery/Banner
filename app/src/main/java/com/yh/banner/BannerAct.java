package com.yh.banner;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yh.bannerlibary.banner.BannerView;

import java.util.ArrayList;

/**
 * Created by Clistery on 18-7-11.
 */
public class BannerAct extends Activity {
    
    private BannerView<BannerAdapter> mBanner;
    private ArrayList<BannerDataInfo> mData;
    private BannerAdapter mAdapter;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_banner);
        
        mBanner = findViewById(R.id.banner);
        
        mData = new ArrayList<BannerDataInfo>() {
            {
                add(new BannerDataInfo(R.drawable.cheese_1));
                add(new BannerDataInfo(R.drawable.cheese_2));
                add(new BannerDataInfo(R.drawable.cheese_3));
                add(new BannerDataInfo(R.drawable.cheese_4));
                add(new BannerDataInfo(R.drawable.cheese_5));
            }
        };
        
        mAdapter = new BannerAdapter(getApplicationContext(), mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(BannerAct.this, "click: " + mData.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        mBanner.bindAdapter(mAdapter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mBanner.stop();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mBanner.play();
    }
}
