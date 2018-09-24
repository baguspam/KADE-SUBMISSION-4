package com.maspamz.submission3.model.detail

import com.google.gson.Gson
import com.maspamz.submission3.config.api.ApiRepository
import com.maspamz.submission3.config.api.ApiTheSportDB
import com.maspamz.submission3.config.data.LeagueResponse
import com.maspamz.submission3.helper.CoroutinesContextProvider
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by Maspamz on 07/09/2018.
 *
 */

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val getData: Gson, private val context: CoroutinesContextProvider = CoroutinesContextProvider()){



    fun getDetailMatchView(league: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                getData.fromJson(apiRepository
                        .doRequest(ApiTheSportDB.getDetailMatch(league)),
                        LeagueResponse::class.java
                )
            }
            view.hideLoading()
            view.showDetailMatchList(data.await().events)
        }
    }

    fun getDetailClubView(league1: String?, league2: String?) {
        view.showLoading()
        async(context.main)  {
            val data1 = bg {getData.fromJson(apiRepository
                    .doRequest(ApiTheSportDB.getDetailClub(league1)),
                    LeagueResponse::class.java
                )
            }
            val data2 = bg {
                getData.fromJson(apiRepository
                        .doRequest(ApiTheSportDB.getDetailClub(league2)),
                        LeagueResponse::class.java
                )
            }
            view.hideLoading()
            view.showDetailClub(data1.await().teams, data2.await().teams)
        }
    }

}