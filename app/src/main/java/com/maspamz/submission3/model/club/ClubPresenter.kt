package com.maspamz.submission3.model.club

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

class ClubPresenter(private val view: ClubView,
                    private val apiRepository: ApiRepository,
                    private val getData: Gson, private val context: CoroutinesContextProvider = CoroutinesContextProvider()){

    fun getClubList(league: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                getData.fromJson(apiRepository
                        .doRequest(ApiTheSportDB.getClub(league)),
                        LeagueResponse::class.java
                )
            }

            view.hideLoading()
            view.showClubList(data.await().teams)

        }
    }
}