package com.reignscanary.jetknow.composables


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MainScreenViewModel : ViewModel() {

private val _searchText = MutableLiveData("")
    val searchText : LiveData<String> = _searchText
    private val _latLng = MutableLiveData(LatLng(-33.88,151.21))
    val latLng : LiveData<LatLng> = _latLng

    fun onLatLngUpdate(newLatLng: LatLng){

        _latLng.value  = newLatLng
    }
    fun onSearchTextChange(newSearchText : String){

       _searchText.value = newSearchText
    }


}