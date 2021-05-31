package com.reignscanary.jetknow


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng


class MainScreenViewModel : ViewModel() {

private val _searchText = MutableLiveData("")
    val searchText : LiveData<String> = _searchText
    private val _latLng = MutableLiveData(LatLng(-33.88,151.21))
    val latLng : LiveData<LatLng> = _latLng
    private val _selectedCategory = MutableLiveData("")
    val selectedCategory : LiveData<String> = _selectedCategory
    fun onSelectedCategoryChanged(newCategory : String){
        _selectedCategory.value = newCategory
        onSearchTextChange(newCategory)

    }

    fun onLatLngUpdate(newLatLng: LatLng){

        _latLng.value  = newLatLng
    }
    fun onSearchTextChange(newSearchText : String){

       _searchText.value = newSearchText
    }


}