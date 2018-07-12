package com.yh.bannerlibary.banner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.IdRes
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yh.bannerlibary.R
import com.yh.bannerlibary.recyclerviewpager.LoopRecyclerViewPager
import java.lang.ref.WeakReference


/**
 * Created by Clistery on 18-7-11.
 */

class BannerAdapter(private val mCtx: Context, mBannerData: ArrayList<BannerInfo>, private val mBannerViewPager: LoopRecyclerViewPager) : BaseQuickAdapter<BannerInfo, BannerAdapter.ViewHolder>(R.layout.item_banner, mBannerData) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerAdapter.ViewHolder {
        val holder = super.onCreateViewHolder(parent, viewType)
        holder.set(this@BannerAdapter, mBannerViewPager)
        return holder
    }

    override fun convert(helper: ViewHolder, item: BannerInfo) {
        val inputStream = mCtx.resources.openRawResource(item.data)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
//        options.inSampleSize = 10   //width，hight设为原来的十分一
        val btp = BitmapFactory.decodeStream(inputStream, null, options)
        helper.setBanner(btp)
        helper.addOnClickListener(R.id.img_banner)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clearBanner()
    }

    data class ViewHolder(private val view: View) : BaseViewHolder(view) {
        private val mBanner: ImageView = view.findViewById(R.id.img_banner)
        private var mWRbmp: WeakReference<Bitmap?>? = null

        private lateinit var mAdapter: BannerAdapter
        private lateinit var mBannerViewPager: LoopRecyclerViewPager

        fun set(adapter: BannerAdapter, bannerViewPager: LoopRecyclerViewPager) {
            mAdapter = adapter
            mBannerViewPager = bannerViewPager
        }

        fun clearBanner() {
            if (null != mWRbmp) {
                val bmp: Bitmap? = mWRbmp?.get()
                if (null != bmp && !bmp.isRecycled) {
                    bmp.recycle()
                    mWRbmp?.clear()
                }
            }
            mWRbmp = null
        }

        fun setBanner(btp: Bitmap?) {
            mBanner.setImageBitmap(btp)
            mWRbmp = WeakReference(btp)
        }

        override fun addOnClickListener(@IdRes viewId: Int): BaseViewHolder {
            childClickViewIds.add(viewId)
            val view = getView<View>(viewId)
            if (view != null) {
                if (!view.isClickable) {
                    view.isClickable = true
                }
                view.setOnClickListener { v ->
                    if (mAdapter.onItemChildClickListener != null) {
                        mAdapter.onItemChildClickListener!!.onItemChildClick(mAdapter, v, mBannerViewPager.actualCurrentPosition)
                    }
                }
            }

            return this
        }
    }
}
