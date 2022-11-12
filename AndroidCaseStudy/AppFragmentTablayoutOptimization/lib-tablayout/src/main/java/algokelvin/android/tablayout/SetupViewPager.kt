package algokelvin.android.tablayout

import com.google.android.material.tabs.TabLayout

object SetupViewPager {
    fun TabLayout.setTextNameTabs(vararg tabs: String) {
        for (x in tabs.indices) {
            this.getTabAt(x)?.text = tabs[x]
        }
    }
}