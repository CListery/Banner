@file:JvmName("BannerDataInfo")
@file:JvmMultifileClass

package com.yh.banner

import android.support.annotation.DrawableRes
import com.yh.bannerlibary.banner.IBannerInfo

/**
 * Created by Clistery on 18-7-13.
 */
data class BannerDataInfo(@DrawableRes val bannerImg: Int) : IBannerInfo<Int> {

    override fun getBannerImg(): Int {
        return bannerImg
    }
}