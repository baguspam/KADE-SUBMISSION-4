package com.maspamz.submission3.model.match

import com.google.gson.Gson
import com.maspamz.submission3.TestContextProvider
import com.maspamz.submission3.config.api.ApiRepository
import com.maspamz.submission3.config.api.ApiTheSportDB
import com.maspamz.submission3.config.data.League
import com.maspamz.submission3.config.data.LeagueResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Maspamz on 16/09/2018.
 */
class MatchNextPresenterTest{
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var getData: Gson

    private lateinit var presenter: MatchNextPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = MatchNextPresenter(view, apiRepository, getData, TestContextProvider())
    }

    @Test
    fun testGetMatchList(){

        val club : MutableList<League> = mutableListOf()
        val response = LeagueResponse(teams = club, events = club)
        val league = "English League Championship"

        //Testing response get data from server api
        Mockito.`when`(getData.fromJson(apiRepository.doRequest(ApiTheSportDB.getClub(league)), LeagueResponse::class.java)).thenReturn(response)

        //Testing get data
        presenter.getMatchNextList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showMatchList(club)
        Mockito.verify(view).hideLoading()

    }
}

