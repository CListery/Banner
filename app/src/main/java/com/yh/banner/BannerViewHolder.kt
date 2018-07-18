@file:JvmName("BannerViewHolder")
@file:JvmMultifileClass

package com.yh.banner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.yh.bannerlibary.banner.AbsBannerHolder
import java.lang.ref.WeakReference

data class BannerViewHolder(private val view: View) : AbsBannerHolder<BannerDataInfo>(view) {
    private val mBanner: ImageView = view.findViewById(R.id.img_banner)
    private var mWRbmp: WeakReference<Bitmap?>? = null

    override fun loadBanner(item: BannerDataInfo) {
        val inputStream = BannerApp.get().resources.openRawResource(item.getBannerImg())
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
        val btp = BitmapFactory.decodeStream(inputStream, null, options)
        setBanner(btp)
    }

    override fun clearBanner() {
        if (null != mWRbmp) {
            val bmp: Bitmap? = mWRbmp?.get()
            if (null != bmp && !bmp.isRecycled) {
                bmp.recycle()
                mWRbmp?.clear()
            }
        }
        mWRbmp = null
    }

    private fun setBanner(btp: Bitmap?) {
        mBanner.setImageBitmap(btp)
        mWRbmp = WeakReference(btp)
    }

}