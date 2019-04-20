package org.niralmehta.camerauploadexample.utils.fragment

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Fix to re-subscribe observer, therefore avoiding duplicates
 * Ref: #link https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
 * GitHub issue: https://github.com/googlesamples/android-architecture-components/issues/47
 */
fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}
