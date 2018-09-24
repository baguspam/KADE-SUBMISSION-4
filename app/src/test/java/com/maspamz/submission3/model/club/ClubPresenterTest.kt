package com.maspamz.submission3.model.club

import com.google.gson.Gson
import com.maspamz.submission3.TestContextProvider
import com.maspamz.submission3.config.api.ApiRepository
import com.maspamz.submission3.config.api.ApiTheSportDB
import com.maspamz.submission3.config.data.League
import com.maspamz.submission3.config.data.LeagueResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Maspamz on 14/09/2018.
 *
 */
class ClubPresenterTest{

    @Mock
    private lateinit var view: ClubView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var getData: Gson

    private lateinit var presenter: ClubPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = ClubPresenter(view, apiRepository, getData, TestContextProvider())
    }

    @Test
    fun testGetClubList(){

        val club : MutableList<League> = mutableListOf()
        val response = LeagueResponse(teams = club, events = club)
        val league = "English League Championship"

        //Testing response get data from server api
        `when`(getData.fromJson(apiRepository.doRequest(ApiTheSportDB.getClub(league)), LeagueResponse::class.java)).thenReturn(response)

        //Testing get data
        presenter.getClubList(league)

        verify(view).showLoading()
        verify(view).showClubList(club)
        verify(view).hideLoading()

    }

}
