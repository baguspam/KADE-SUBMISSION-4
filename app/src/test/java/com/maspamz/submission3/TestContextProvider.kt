package com.maspamz.submission3

import com.maspamz.submission3.helper.CoroutinesContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Maspamz on 16/09/2018.
 *
 */
class TestContextProvider : CoroutinesContextProvider() {
    override val main: CoroutineContext = Unconfined
}