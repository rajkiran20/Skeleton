package com.skeleton.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RestrictTo
import com.skeleton.presentation.R
import com.skeleton.presentation.ui.BaseActivity

@RestrictTo(RestrictTo.Scope.TESTS)
open class TestsOnlyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}
