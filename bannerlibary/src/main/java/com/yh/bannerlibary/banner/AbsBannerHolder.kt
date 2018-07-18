@file:JvmName("AbsBannerHolder")
@file:JvmMultifileClass

package com.yh.bannerlibary.banner

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by Clistery on 18-7-13.
 */
abstract class AbsBannerHolder<T : IBannerInfo<*>>(view: View) : BaseViewHolder(view) {

    /**
     * the method to implement the loading of banner content.
     *
     * you can do some online requests and other actions here.
     * @param item T
     */
    abstract fun loadBanner(item: T)

    /**
     * You can do some memory recycling in this function.
     *
     * not must override.
     */
    open fun clearBanner() {

    }

}