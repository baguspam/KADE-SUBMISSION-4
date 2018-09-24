package com.maspamz.submission3.helper

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Maspamz on 16/09/2018.
 *
 */
open class CoroutinesContextProvider {
    open val main: CoroutineContext by lazy { UI }
}