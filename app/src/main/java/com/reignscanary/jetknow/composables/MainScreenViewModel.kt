package com.reignscanary.jetknow.composables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {

private val _searchText = MutableLiveData("")
    val searchText : LiveData<String> = _searchText

    fun onSearchTextChange(newSearchText : String){

       _searchText.value = newSearchText
    }


}