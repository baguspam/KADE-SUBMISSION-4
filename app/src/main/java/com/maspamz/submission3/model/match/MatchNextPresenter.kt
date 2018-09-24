package com.maspamz.submission3.model.match

import com.google.gson.Gson
import com.maspamz.submission3.config.api.ApiRepository
import com.maspamz.submission3.config.api.ApiTheSportDB
import com.maspamz.submission3.config.data.LeagueResponse
import com.maspamz.submission3.helper.CoroutinesContextProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by Maspamz on 07/09/2018.
 *
 */

class MatchNextPresenter(private val view: MatchView,
                         private val apiRepository: ApiRepository,
                         private val getData: Gson, private val context: CoroutinesContextProvider = CoroutinesContextProvider()){

    fun getMatchNextList(league: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                getData.fromJson(apiRepository
                        .doRequest(ApiTheSportDB.getMatchNext(league)),
                        LeagueResponse::class.java
                )
            }
            view.hideLoading()
            view.showMatchList(data.await().events)
        }
    }
}

