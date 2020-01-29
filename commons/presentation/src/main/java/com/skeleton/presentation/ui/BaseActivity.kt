package com.skeleton.presentation.ui

import com.skeleton.presentation.BuildConfig
import com.skeleton.presentation.R
import com.skeleton.presentation.databinding.NetworkPrefDialogBinding
import com.skeleton.presentation.ui.fragment.BaseFragment
import com.skeleton.util.constant.NetworkConstants.Companion.MOCKOON_URL
import com.skeleton.util.constant.NetworkConstants.Companion.PROD_URL
import com.skeleton.util.constant.NetworkConstants.Companion.TEST_URL
import com.skeleton.util.sharedPref.NetworkSharedPrefsManager.Companion.getBaseUrl
import com.skeleton.util.sharedPref.NetworkSharedPrefsManager.Companion.getEndpointIndex
import com.skeleton.util.sharedPref.NetworkSharedPrefsManager.Companion.setAuthToken
import com.skeleton.util.sharedPref.NetworkSharedPrefsManager.Companion.setNetworkPref
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var networkPrefDialogBinding: NetworkPrefDialogBinding

    private val okListener = DialogInterface.OnClickListener { _, _ ->
        networkPrefDialogBinding.apply {
            etEndpoint.text?.toString()?.let {
                setNetworkPref(this@BaseActivity, selectedEnvironmentIndex!!, it)
            }
            etAuthToken.text?.toString()?.let {
                setAuthToken(context = this@BaseActivity, authToken = etAuthToken.text.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)

        supportFragmentManager.apply {
            addOnBackStackChangedListener {
                if (fragments.isNotEmpty()) fragments.last {
                    (it as BaseFragment).onVisible()
                    return@last true
                }
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (BuildConfig.DEBUG) {
            if (KeyEvent.KEYCODE_VOLUME_UP == keyCode) {
                initNetworkDialog()
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun initNetworkDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        networkPrefDialogBinding = NetworkPrefDialogBinding.inflate(layoutInflater)

        networkPrefDialogBinding.apply {
            endpoint = getBaseUrl(this@BaseActivity)
            rgEnvironmentOptions.check(rgEnvironmentOptions.getChildAt(getEndpointIndex(this@BaseActivity)).id)
            selectedEnvironmentIndex = getEndpointIndex(this@BaseActivity)

            rgEnvironmentOptions.setOnCheckedChangeListener { _, _ ->
                selectedEnvironmentIndex = rgEnvironmentOptions.indexOfChild(
                    rgEnvironmentOptions.findViewById<RadioButton>(rgEnvironmentOptions.checkedRadioButtonId)
                )
                etEndpoint.setText(getUrlForSelectedEnvironment(selectedEnvironmentIndex!!))
            }
        }

        alertDialogBuilder.apply {
            setCancelable(false).setTitle("Network settings")
                .setCancelable(false).setPositiveButton(getString(android.R.string.ok), okListener)
                .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ -> dialog.cancel() }
            setView(networkPrefDialogBinding.root)
            show()
        }
    }

    private fun getUrlForSelectedEnvironment(selectedEnvironmentIndex: Int): String {
        return when (selectedEnvironmentIndex) {
            0 -> PROD_URL
            1 -> TEST_URL
            else -> MOCKOON_URL
        }
    }

    protected open fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment.javaClass.name).commit()
    }

    protected open fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment.javaClass.name).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) finish()
    }
}
