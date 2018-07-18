package com.yh.bannerlibary.banner

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yh.bannerlibary.recyclerviewpager.LoopRecyclerViewPager

/**
 * Created by Clistery on 18-7-18.
 */
abstract class AbsBannerAdapter<T : IBannerInfo<*>, VH : AbsBannerHolder<T>>(ctx: Context, data: List<T>) : BaseQuickAdapter<T, VH>(data) {

    private val mCtx: Context = ctx
    private lateinit var mPager: LoopRecyclerViewPager

    fun setItemLayout(itemLayout: Int) {
        mLayoutResId = itemLayout
    }

    @Deprecated("replace to AbsBannerAdapter.bindToRecyclerView(recyclerView: LoopRecyclerViewPager)", ReplaceWith("throw CanNotCallThisException()", "com.yh.bannerlibary.banner.CanNotCallThisException"))
    override fun bindToRecyclerView(recyclerView: RecyclerView) {
        throw CanNotCallThisException()
    }

    fun bindToRecyclerView(recyclerView: LoopRecyclerViewPager) {
        super.bindToRecyclerView(recyclerView)
        mPager = recyclerView
    }

    abstract override fun createBaseViewHolder(view: View): VH

    override fun convert(helper: VH, item: T) {
        helper.loadBanner(item)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.clearBanner()
    }

    fun getContext(): Context {
        return mCtx
    }

}