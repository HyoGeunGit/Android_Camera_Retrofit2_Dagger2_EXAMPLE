package org.niralmehta.camerauploadexample.utils.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_app_bar.*
import org.niralmehta.camerauploadexample.R


/**
 * Collection of animation methods for floating action menu
 */

fun setFabIcon(activity: FragmentActivity?, drawable: Int, fragment: Fragment) =
    activity?.bottom_app_bar_fab?.setImageDrawable(fragment.getResDrawable(drawable))

fun getAppBarFab(activity: FragmentActivity?): FloatingActionButton? =
    activity?.bottom_app_bar_fab

fun fabOpen(context: Context): Animation =
    AnimationUtils.loadAnimation(context, R.anim.fab_open)

fun fabClose(context: Context): Animation =
    AnimationUtils.loadAnimation(context, R.anim.fab_close)

fun rotateForwards(context: Context): Animation =
    AnimationUtils.loadAnimation(context, R.anim.rotate_forward)

fun rotateBackwards(context: Context): Animation =
    AnimationUtils.loadAnimation(context, R.anim.rotate_backward)

fun FloatingActionButton.animate(animation: Animation) =
    this.startAnimation(animation)

/**
 * Collection of Resource methods
 */

fun Context.getResDrawable(resId: Int) = ContextCompat.getDrawable(this, resId)

fun Fragment.getResDrawable(resId: Int) = context?.getResDrawable(resId)

fun Context.getResColor(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Fragment.getResColor(colorRes: Int) = context?.getResColor(colorRes)

/**
 * Collection of Snackbar methods
 */
fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.newStyle(this.context)
    val mainTextView =
        snack.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    mainTextView.setTextColor(snack.context.getResColor(R.color.lightAccentWhite))
    snack.show()
}

fun Snackbar.newStyle(context: Context) {
    val params = this.view.layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(16, 16, 16, 16)
    params.height = 128
    this.view.layoutParams = params
    this.view.background = context.getResDrawable(R.drawable.bg_snackbar)
    ViewCompat.setElevation(this.view, 6f)
}