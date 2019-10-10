package com.example.weibo.utils.listener

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class OnWBTabSelectedListener(pager: ViewPager) : TabLayout.OnTabSelectedListener {
    private val viewPager = pager

    override fun onTabReselected(p0: TabLayout.Tab?) {
        if (p0 != null) {
            viewPager.currentItem = p0.position
        }
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        if (p0 != null) {
            viewPager.currentItem = p0.position
        }
    }
}

class OnWBPageChangeListener(tabLayout: TabLayout) : ViewPager.OnPageChangeListener {
    private val layout = tabLayout

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        layout.getTabAt(position)?.select()
    }

}