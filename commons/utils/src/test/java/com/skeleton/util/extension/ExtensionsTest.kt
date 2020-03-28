package com.skeleton.util.extension

import android.view.View
import androidx.annotation.IntegerRes
import com.skeleton.util.R
import com.skeleton.util.ui.activity.TestsOnlyActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExtensionsTest {

    private lateinit var testActivity: TestsOnlyActivity

    @Before
    fun setUp() {
        testActivity = mock(TestsOnlyActivity::class.java)
    }

    @Test
    fun shouldFindViewByIdForGivenContext() {
        val dummyView = mock(View::class.java)
        @IntegerRes val someResource = R.id.textWatcher
        `when`(testActivity.findViewById<View>(anyInt())).thenReturn(dummyView)
        testActivity.findView<View>(someResource)
        verify(testActivity).findViewById<View>(someResource)
    }
}
