package org.niralmehta.camerauploadexample.utils.fragment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.niralmehta.camerauploadexample.R

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun FragmentManager.isInBackStack(screenTag: String): Boolean {
    findFragmentByTag(screenTag) ?: return false
    return true
}

fun AppCompatActivity.addScreen(
    fragment: Fragment,
    screenTag: String? = null,
    addBackStack: Boolean = false,
    popBackStack: Boolean = false
) {
    when {
        screenTag == null -> {
            supportFragmentManager.inTransaction {
                setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                replace(R.id.container_main, fragment)
            }
        }

        addBackStack -> {
            val inBackStack = supportFragmentManager.isInBackStack(screenTag)
            if (!inBackStack) {
                supportFragmentManager.inTransaction {
                    setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    replace(R.id.container_main, fragment, screenTag)
                    addToBackStack(screenTag)
                }
            } else if (inBackStack && popBackStack) {
                supportFragmentManager.popBackStack(screenTag, 0)
            }
        }

        else -> {
            val inBackStack = supportFragmentManager.isInBackStack(screenTag)
            if (!inBackStack) {
                supportFragmentManager.inTransaction {
                    setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    replace(R.id.container_main, fragment, screenTag)
                }
            }
        }
    }
}

/*
 *  Fragment Util
 *  Re-use functionality of AppCompatActivity
 */
fun Fragment.addScreen(
    fragment: Fragment,
    screenTag: String? = null,
    addBackStack: Boolean = false,
    popBackStack: Boolean = false
) {
    (activity as AppCompatActivity).addScreen(fragment, screenTag, addBackStack, popBackStack)
}

/*
 *  Context Util
 *  Re-use functionality of AppCompatActivity
 */
fun Context.addScreen(
    fragment: Fragment,
    screenTag: String? = null,
    addBackStack: Boolean = false,
    popBackStack: Boolean = false
) {
    (this as AppCompatActivity).addScreen(fragment, screenTag, addBackStack, popBackStack)
}