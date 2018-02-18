package com.apps.jmm.sportskotlinretrofittdd.ui.activity

import android.os.Bundle
import com.apps.jmm.sportskotlinretrofittdd.R
import com.apps.jmm.sportskotlinretrofittdd.app.replaceFragment
import com.apps.jmm.sportskotlinretrofittdd.ui.fragment.PlayerListFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(PlayerListFragment(), R.id.mFragment)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}
