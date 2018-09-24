package com.maspamz.submission3.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.maspamz.submission3.DetailActivityClub

import com.maspamz.submission3.R
import com.maspamz.submission3.adapter.ClubAdapter
import com.maspamz.submission3.config.api.ApiRepository
import com.maspamz.submission3.config.data.League
import com.maspamz.submission3.model.club.ClubPresenter
import com.maspamz.submission3.model.club.ClubView
import kotlinx.android.synthetic.main.fragment_club.view.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

/**
 * Created by Maspamz on 07/09/2018.
 *
 */

class ClubFragment : Fragment(),ClubView {

    private var teams: MutableList<League> = mutableListOf()
    private lateinit var presenter: ClubPresenter
    private lateinit var adapter: ClubAdapter
    private lateinit var leagueName: String
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner: Spinner
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_club, container, false)
        val activity = activity
        //RecycleView plus Adapter
        adapter = ClubAdapter(teams){
            startActivity(intentFor<DetailActivityClub>(
                    "ID_ITEM"  to it.teamId
            ))
        }

        listTeam = rootView.lrv_view_club
        listTeam.layoutManager = LinearLayoutManager(activity)
        listTeam.adapter = adapter


        spinner = rootView.l_spinner
        swipeRefreshLayout = rootView.l_swipe
        progressBar = rootView.l_progressBar

        val spinnerItems = resources.getStringArray(R.array.league)
        spinner.adapter =  ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getClubList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefreshLayout.onRefresh {
            presenter.getClubList(leagueName)
        }
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)

        //Get data
        val request = ApiRepository()
        val getData = Gson()
        presenter = ClubPresenter(this, request, getData)
        return rootView
    }


    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
       progressBar.visibility = View.INVISIBLE
    }

    override fun showClubList(data: List<League>) {
        swipeRefreshLayout.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }


}// Required empty public constructor
