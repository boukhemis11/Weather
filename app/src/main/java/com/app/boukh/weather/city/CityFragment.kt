package com.app.boukh.weather.city


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.app.boukh.weather.App
import com.app.boukh.weather.Database

import com.app.boukh.weather.R

class CityFragment : Fragment(), CityAdapter.CityItemListener {


    private lateinit var cities: MutableList<City>
    private lateinit var database: Database
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        cities = mutableListOf()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        recyclerView = view.findViewById(R.id.cities_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cities = database.getAllCities()
        adapter = CityAdapter(cities, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_create_city -> {
                showCreacteCityDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCreacteCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener = object : CreateCityDialogFragment.CreateCityDialogListener{
            override fun onDialogNegativeClick() {

            }

            override fun onDialogPositiveClick(cityName: String) {
                saveCity(City(cityName))
            }

        }
        createCityFragment.show(fragmentManager, "CreateCityDialogFragment")
    }
    private fun saveCity(city: City){
        if (database.createCity(city)) {
            cities.add(city)
            adapter.notifyDataSetChanged()
        }else {
            Toast.makeText(context, "Could not creat city", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCitySelected(city: City) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCityDeleted(city: City) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
