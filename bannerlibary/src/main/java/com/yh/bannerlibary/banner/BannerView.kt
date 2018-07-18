@file:JvmName("BannerView")
@file:JvmMultifileClass

package com.yh.bannerlibary.banner

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.yh.bannerlibary.R
import com.yh.bannerlibary.indicator.MagicIndicator
import com.yh.bannerlibary.indicator.buildins.navigator.ScaleCircleNavigator
import com.yh.bannerlibary.recyclerviewpager.LoopRecyclerViewPager
import com.yh.bannerlibary.recyclerviewpager.RecyclerViewPager

/**
 * Created by Clistery on 18-7-11.
 */

class BannerView<Adapter : AbsBannerAdapter<*, *>> : FrameLayout {
    private val sTAG: String = "BannerView"

    private val mCtx: Context
    private var mDuration = 2700L

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mCtx = context
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        mCtx = context
        init()
    }

    private lateinit var mBannerViewPager: LoopRecyclerViewPager
    private var mAdapter: Adapter? = null

    private lateinit var mBannerIndicator: MagicIndicator
    private lateinit var mScaleCircleNavigator: ScaleCircleNavigator

    @LayoutRes
    private var mDefaultItemLayout = R.layout.item_banner

    private val mLoopRunnable = Runnable {
        mBannerViewPager.smoothScrollToPosition(mBannerViewPager.currentPosition + 1)
        play()
    }

    private fun init() {
        var bannerView = View.inflate(mCtx, R.layout.layout_banner, null) as ConstraintLayout
        mBannerViewPager = bannerView.findViewById(R.id.banner_viewpager)
        mBannerIndicator = bannerView.findViewById(R.id.banner_indicator)

        mBannerViewPager.flingFactor = 0f
        mBannerViewPager.layoutManager = LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false)
        mBannerViewPager.setHasFixedSize(true)
        mBannerViewPager.isLongClickable = false

        mScaleCircleNavigator = ScaleCircleNavigator(mCtx)
        mScaleCircleNavigator.setNormalCircleColor(Color.LTGRAY)
        mScaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY)
        mBannerIndicator.navigator = mScaleCircleNavigator
        bind()
        addView(bannerView)
    }

    private fun bind() {
        mBannerViewPager.addOnPageChangedListener(object : RecyclerViewPager.OnPageChangedListener {
            override fun onPageChanged(oldPosition: Int, newPosition: Int) {
                val page = mBannerViewPager.transformToActualPosition(newPosition)
                mBannerIndicator.onPageSelected(page)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                mBannerIndicator.onPageScrolled(mBannerViewPager.actualCurrentPosition, positionOffset, positionOffsetPixels)
            }
        })
    }

    fun setItemLayout(@LayoutRes itemLayout: Int) {
        mDefaultItemLayout = itemLayout
    }

    fun setBannerDuration(duration: Long) {
        mDuration = duration
    }

    fun bindAdapter(adapter: Adapter) {
        mAdapter = adapter
        mAdapter!!.setItemLayout(mDefaultItemLayout)
        updateIndicator()
        mAdapter!!.bindToRecyclerView(mBannerViewPager)
        play()
    }

    fun updateIndicator() {
        mScaleCircleNavigator.setCircleCount(mAdapter!!.getData().size)
        mScaleCircleNavigator.notifyDataSetChanged()
    }

    fun play() {
        Log.d(sTAG, "play")
        if (null != mAdapter && !mAdapter!!.getData().isEmpty()) {
            removeCallbacks(mLoopRunnable)
            postDelayed(mLoopRunnable, mDuration)
        } else {
            stop()
        }
    }

    fun stop() {
        Log.d(sTAG, "stop")
        removeCallbacks(mLoopRunnable)
    }

}
