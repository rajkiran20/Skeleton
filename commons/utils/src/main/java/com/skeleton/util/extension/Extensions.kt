package com.skeleton.util.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

fun <T> Context.startActivity(clazz: Class<T>) {
    startActivity(Intent(this, clazz))
}

fun <T> Fragment.startActivity(clazz: Class<T>) {
    context?.startActivity(Intent(context, clazz))
}

fun Fragment.setTitle(@StringRes resourceId: Int) {
    activity?.setTitle(resourceId)
}

inline fun <reified T : View> Activity.findView(id: Int): T = findViewById(id)

inline fun <reified T : View> View.findView(id: Int): T = findViewById(id)

fun View.getString(id: Int): String = context.getString(id)

val String.Companion.EMPTY: String get() = ""

fun Context.showLongToast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
fun Fragment.showLongToast(text: CharSequence) = context?.showLongToast(text)
fun Context.showShortToast(text: CharSequence) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Fragment.showShortToast(text: CharSequence) = context?.showShortToast(text)

public inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

fun <T> Observable<T>.getIo(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun reloadModules(vararg modules: Module) {
    unloadKoinModules(modules.toList())
    loadKoinModules(modules.toList())
}