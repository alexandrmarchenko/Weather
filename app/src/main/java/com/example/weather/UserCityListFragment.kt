package com.example.weather

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_city_list.*


class UserCityListFragment : Fragment() {

    private val VERTICAL_ITEM_SPACE = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_city_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillCityList()
        setBackBtnListener()
        setAddCityBtnListener()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun fillCityList() {


        val listener = { position: Int -> showCityWeatherForecast(position)}

        var userCityListAdapter = UserCityListAdapter(listener)

        user_city_list.layoutManager = LinearLayoutManager(context)
        user_city_list.adapter = userCityListAdapter
        user_city_list.addItemDecoration(VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE))


        userCityListAdapter.dataList = MainActivity.citiesData

    }

    class VerticalSpaceItemDecoration(var verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            if (parent.getChildAdapterPosition(view) != (parent.getAdapter()?.getItemCount() ?: 0) - 1
            ) {
                outRect.bottom = verticalSpaceHeight;
            }
        }

    }

    private fun showCityWeatherForecast(position: Int) {

        var mainFragment = MainFragment.create(MainActivity.citiesData[position])

        var ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.fragment_container, mainFragment)
        ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft?.commit()
    }

    private fun setBackBtnListener() {
        topAppBar.setNavigationOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun setAddCityBtnListener() {
        add_city.setOnClickListener {
            var addCityFragment = AddCityFragment()

            var ft = fragmentManager?.beginTransaction()
            ft?.add(R.id.fragment_container, addCityFragment)
            ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft?.addToBackStack("");
            ft?.commit()
        }
    }

}
