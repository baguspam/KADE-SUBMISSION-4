package com.maspamz.submission3

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.maspamz.submission3.config.api.ApiRepository
import com.maspamz.submission3.config.db.database
import com.maspamz.submission3.config.data.FavoriteClub
import com.maspamz.submission3.config.data.League
import com.maspamz.submission3.model.detail.DetailPresenter
import com.maspamz.submission3.model.detail.DetailView
import kotlinx.android.synthetic.main.activity_detail_club.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

/**
* Created by Maspamz on 9/7/2018.
*
*/

class DetailActivityClub : AppCompatActivity(), DetailView {

    //TODO 1 Declaration for variable, view access in all private in here, presenter, and menu favorite in action bar
    private var name: String = ""
    private var desc: String = ""
    private var img: String = ""
    private var teamID: String = ""
    private lateinit var scrollView: ScrollView
    private lateinit var progressBar: ProgressBar

    private lateinit var presenter: DetailPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_club)

        //TODO 2 for support menu like favorite in action bar
        supportActionBar?.title = "Detail Club"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = l_progressBar
        scrollView = l_scroll


        val intent = intent
        teamID = intent.getStringExtra("ID_ITEM")
        Log.d("Data ID CLUB", teamID)

        //Get data
        val request = ApiRepository()
        val getData = Gson()
        presenter = DetailPresenter(this, request, getData)


        //Request Get data for api with one ID
        presenter.getDetailClubView(teamID, teamID)

        //Check id favorite, What id in detail, like such as id from db ?
        checkFavorite()


    }


    override fun showDetailMatchList(data: List<League>) {
        //null because not get data from events
    }

    //TODO 3 Get data from api and show hide loading
    override fun showDetailClub(data1: List<League>, data2: List<League>) {
        name = data1.get(index = 0).teamName.toString()
        img = data1.get(index = 0).teamBadge.toString()
        desc = data1.get(index = 0).teamDescription.toString()

        Glide.with(this).load(img).into(img_club)
        tv_name_club.text = name
        tv_description.text = desc
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    //TODO 4 Make menu and function addFavorite, removeFavorite, and checkFavorite
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            /*android.R.id.home -> {
                finish()
                true
            }*/
            R.id.add_to_favorite -> {
                if (isFavorite) {
                    removeFavorite()
                } else{
                    addFavorite()
                }

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addFavorite(){
        try {
            database.use {
                insert(FavoriteClub.tb_favorite_club,
                        FavoriteClub.team_id to teamID,
                        FavoriteClub.team_name to name,
                        FavoriteClub.team_badge to img,
                        FavoriteClub.team_description to desc
                )
            }
            snackbar(scrollView, R.string.add_favorite).show()
        }catch (e: SQLiteConstraintException){
            snackbar(scrollView, e.localizedMessage).show()
        }
    }

    private fun removeFavorite(){
        try {
            database.use {
                delete(FavoriteClub.tb_favorite_club, "(team_id = {team_id})",
                        "team_id" to teamID)
            }
            snackbar(scrollView, R.string.remove_favorite).show()
        } catch (e: SQLiteConstraintException){
            snackbar(scrollView, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
    }

    private fun checkFavorite() {
        database.use {
            val result = select(FavoriteClub.tb_favorite_club)
                    .whereArgs("(team_id = {team_id})", "team_id" to teamID)
            val favorite = result.parseList(classParser<FavoriteClub>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}