package com.yh.banner

import android.content.Context
import android.view.View
import com.yh.bannerlibary.banner.AbsBannerAdapter
import java.util.*

/**
 * Created by Clistery on 18-7-18.
 */
class BannerAdapter(context: Context, data: ArrayList<BannerDataInfo>) : AbsBannerAdapter<BannerDataInfo, BannerViewHolder>(context, data) {
    override fun createBaseViewHolder(view: View): BannerViewHolder {
        return BannerViewHolder(view)
    }
}
