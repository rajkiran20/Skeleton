package com.skeleton.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.skeleton.R
import com.skeleton.databinding.ActivityLauncherBinding
import com.skeleton.presentation.ui.BaseActivity


class LauncherActivity : BaseActivity() {
    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_launcher
        )
    }

    private fun getFragmentTransaction(tag: String, fragment: Fragment): FragmentTransaction {
        return supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, tag)
    }

}
