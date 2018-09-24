package com.maspamz.submission3.model.detail

import com.maspamz.submission3.config.data.League

/**
 * Created by Maspamz on 07/09/2018.
 *
 */

interface DetailView {

    fun showLoading()

    fun showDetailMatchList(data: List<League>)

    fun showDetailClub(data1: List<League>, data2: List<League>)

   // fun addToFavorite(data4: List<League>)

    fun hideLoading()

}