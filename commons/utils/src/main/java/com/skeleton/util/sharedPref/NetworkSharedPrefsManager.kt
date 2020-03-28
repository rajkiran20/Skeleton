package com.skeleton.util.sharedPref

import com.skeleton.util.constant.NetworkConstants.Companion.PROD_URL
import android.content.Context
import androidx.core.content.edit

private const val NETWORK_PREF = "NETWORK_PREF_FILE"
private const val ENVIRONMENT = "ENVIRONMENT"
private const val NETWORK_HOST = "NETWORK_PREF_HOST"
private const val AUTH_TOKEN = "AUTH_TOKEN"

fun Context.setNetworkPref(environmentIndex: Int, endpoint: String) {
    val sharedPreferences = getSharedPreferences(NETWORK_PREF, Context.MODE_PRIVATE)
    sharedPreferences.edit {
        putInt(ENVIRONMENT, environmentIndex)
        putString(NETWORK_HOST, endpoint)
    }
}

fun Context.setAuthToken(authToken: String) {
    val sharedPreferences = getSharedPreferences(NETWORK_PREF, Context.MODE_PRIVATE)
    sharedPreferences.edit { putString(AUTH_TOKEN, authToken) }
}

fun Context.getAuthToken(): String {
    val sharedPreferences = getSharedPreferences(NETWORK_PREF, Context.MODE_PRIVATE)
    return sharedPreferences.getString(AUTH_TOKEN, "") ?: ""
}

fun Context.getBaseUrl(): String {
    return getSharedPreferences(NETWORK_PREF, Context.MODE_PRIVATE)
        .getString(NETWORK_HOST, PROD_URL) ?: PROD_URL
}

fun Context.getEndpointIndex(): Int {
    return getSharedPreferences(NETWORK_PREF, Context.MODE_PRIVATE).getInt(ENVIRONMENT, 0)
}
