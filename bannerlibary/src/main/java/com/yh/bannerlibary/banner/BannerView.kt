@file:JvmName("BannerView")
@file:JvmMultifileClass

package com.yh.bannerlibary.banner

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yh.bannerlibary.R
import com.yh.bannerlibary.indicator.MagicIndicator
import com.yh.bannerlibary.indicator.buildins.navigator.ScaleCircleNavigator
import com.yh.bannerlibary.recyclerviewpager.LoopRecyclerViewPager
import com.yh.bannerlibary.recyclerviewpager.RecyclerViewPager

/**
 * Created by Clistery on 18-7-11.
 */

class BannerView : FrameLayout {

    private val mDuration: Long = 2700

    private val mCtx: Context

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

    private lateinit var mBannerView: ConstraintLayout

    private lateinit var mBannerViewPager: LoopRecyclerViewPager
    private var mBannerClickListener: BaseQuickAdapter.OnItemChildClickListener? = null
    private val mBannerData: ArrayList<BannerInfo> = ArrayList()

    private lateinit var mBannerIndicator: MagicIndicator
    private lateinit var mScaleCircleNavigator: ScaleCircleNavigator

    private val mLoopRunnable = object : Runnable {
        override fun run() {
            mBannerViewPager.smoothScrollToPosition(mBannerViewPager.currentPosition + 1)
            this@BannerView.postDelayed(this, mDuration)
        }
    }

    private fun init() {
        mBannerView = View.inflate(mCtx, R.layout.layout_banner, null) as ConstraintLayout
        mBannerViewPager = mBannerView.findViewById(R.id.banner_viewpager)
        mBannerIndicator = mBannerView.findViewById(R.id.banner_indicator)

        mBannerViewPager.flingFactor = 0f
        mBannerViewPager.layoutManager = LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false)
        mBannerViewPager.setHasFixedSize(true)
        mBannerViewPager.isLongClickable = false

        mScaleCircleNavigator = ScaleCircleNavigator(mCtx)
        mScaleCircleNavigator.setNormalCircleColor(Color.LTGRAY)
        mScaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY)
        mBannerIndicator.navigator = mScaleCircleNavigator
        bind()
        addView(mBannerView)
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

    fun setData(data: ArrayList<BannerInfo>) {
        mBannerData.clear()
        mBannerData.addAll(data)
        mScaleCircleNavigator.setCircleCount(mBannerData.size)
        mScaleCircleNavigator.notifyDataSetChanged()
        val adapter = BannerAdapter(mCtx, mBannerData, mBannerViewPager)
        mBannerViewPager.adapter = adapter
        internalSetClickListener(adapter)
        if (!mBannerData.isEmpty()) {
            postDelayed(mLoopRunnable, mDuration)
        } else {
            removeCallbacks(mLoopRunnable)
        }
    }

    fun setBannerClickListener(listener: BaseQuickAdapter.OnItemChildClickListener) {
        mBannerClickListener = listener
        val adapter = mBannerViewPager.adapter
        internalSetClickListener(adapter)
    }

    private fun internalSetClickListener(adapter: RecyclerView.Adapter<*>?) {
        if (null == mBannerClickListener) {
            return
        }
        when (adapter) {
            is BaseQuickAdapter<*, *> -> {
                adapter.onItemChildClickListener = mBannerClickListener
            }
        }
    }

}
