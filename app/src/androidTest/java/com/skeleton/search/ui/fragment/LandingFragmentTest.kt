package com.skeleton.search.ui.fragment

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.skeleton.testUtil.MockServerDispatcher
import com.skeleton.ui.activity.TestsOnlyActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LandingFragmentTest {

    @get:Rule
    var activityRule: IntentsTestRule<TestsOnlyActivity> =
        IntentsTestRule(TestsOnlyActivity::class.java, true, true)

    private lateinit var webServer: MockWebServer

    @Before
    @Throws(Exception::class)
    fun setup() {
        webServer = MockWebServer()
        webServer.start(8080)

        webServer.dispatcher = MockServerDispatcher().RequestDispatcher(activityRule.activity.applicationContext)

//        val fragment = YourFragmentName()
//        activityRule.activity.addFragment(searchFragment)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer.shutdown()
    }
}