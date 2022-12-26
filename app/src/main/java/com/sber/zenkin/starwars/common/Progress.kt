package com.sber.zenkin.starwars.common

import android.view.View
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.CircularProgressIndicator

interface Progress {

    fun showProgress(show: Boolean)

    abstract class Abstract(private val circularProgressIndicator: CircularProgressIndicator) :
        Progress {
        override fun showProgress(show: Boolean) {
            if (show) {
                circularProgressIndicator.showAnimationBehavior = BaseProgressIndicator.SHOW_OUTWARD
                circularProgressIndicator.visibility = View.VISIBLE
            } else {
                circularProgressIndicator.setVisibilityAfterHide(View.INVISIBLE)
                circularProgressIndicator.hideAnimationBehavior = BaseProgressIndicator.HIDE_OUTWARD
                circularProgressIndicator.hide()
            }
        }
    }
    class DefaultProgress(circularProgressIndicator: CircularProgressIndicator) :
        Progress.Abstract(circularProgressIndicator)
}



