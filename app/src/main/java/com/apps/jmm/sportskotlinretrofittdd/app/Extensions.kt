package com.apps.jmm.sportskotlinretrofittdd.app

import android.R
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import es.voghdev.chucknorrisjokes.model.AbsError
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

fun <T> coroutine(function: () -> T): Deferred<T> {
    return async(CommonPool) { function() }
}

fun Pair<Any?, AbsError?>.success(): Boolean {
    return first != null
}

fun Pair<Any?, AbsError?>.failure(): Boolean {
    return second != null
}

fun Pair<List<Any>?, AbsError?>.hasResults(): Boolean {
    return first != null && first?.isNotEmpty() ?: false
}

fun Pair<List<Any>?, AbsError?>.hasNoResults(): Boolean {
    return first != null && first?.isEmpty() ?: false
}

fun ImageView.loadUrl(url: String, draw: Int) = Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(draw).error(draw))
        .into(this)


fun ViewGroup.inflate(itemHolder: Int): View = LayoutInflater.from(context).inflate(itemHolder, this, false)

fun Spinner.configureDefaultAdapter(values: List<String>) {
    adapter = ArrayAdapter<String>(context, R.layout.simple_spinner_item, values)
}

fun Context.toast(message: String, timeShowing: Int = Toast.LENGTH_LONG) = Toast.makeText(this, message, timeShowing).show()

fun Context.hideSoftKeyboard(v: View) {
    val imm: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm?.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
}

fun Context.showSoftKeyboard(v: View) {
    val imm: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm?.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}


fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}