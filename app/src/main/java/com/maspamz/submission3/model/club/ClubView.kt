package com.maspamz.submission3.model.club

import com.maspamz.submission3.config.data.League

/**
 * Created by Maspamz on 07/09/2018.
 *
 */

interface ClubView {

    fun showLoading()

    fun showClubList(data: List<League>)

    fun hideLoading()

}