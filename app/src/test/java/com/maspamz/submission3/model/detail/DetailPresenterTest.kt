package com.maspamz.submission3.model.detail

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
 *
 */
class DetailPresenterTest{
    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var getData: Gson

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, getData, TestContextProvider())
    }

    @Test
    fun testGetDetailMatchList(){

        val club : MutableList<League> = mutableListOf()
        val response = LeagueResponse(teams = club, events = club)
        val league = "4328"

        //Testing response get data from server api
        Mockito.`when`(getData.fromJson(apiRepository.doRequest(ApiTheSportDB.getClub(league)), LeagueResponse::class.java)).thenReturn(response)

        //Testing get data
        presenter.getDetailMatchView(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showDetailMatchList(club)
        Mockito.verify(view).hideLoading()

    }
}