package com.tharwa.solid.tharwa.Base

/**
 * Created by LE on 18/03/2018.
 */

import android.os.Bundle
import android.support.annotation.CallSuper

/**
 * Created by renarosantos on 08/08/16.
 */
abstract class BasePresenter  {
    protected constructor()

    @CallSuper
    fun onCreate(savedInstanceState: Bundle?) {
    }

    @CallSuper
    fun onResume() {
    }

    @CallSuper
    fun onPause() {
    }

    @CallSuper
    fun onSaveInstanceState(outState: Bundle) {
    }

    @CallSuper
    fun onDestroy() {
    }
}
