package com.reignscanary.jetknow


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainScreenViewModel : ViewModel() {

private val _searchText = MutableLiveData("")
    val searchText : LiveData<String> = _searchText
    private val _latLng = MutableLiveData(LatLng(20.8021, 78.24813))
    val latLng : LiveData<LatLng> = _latLng
    private val _selectedCategory = MutableLiveData("")
    val selectedCategory : LiveData<String> = _selectedCategory
private val _dialogStatus = MutableLiveData(false)
val dialogStatus : LiveData<Boolean> = _dialogStatus
    private val _contributeLatLng = MutableLiveData(LatLng(0.0,0.0))
    val contributeLatLng : LiveData<LatLng> = _contributeLatLng
private val _infoDialog = MutableLiveData(false)
    val infoDialog : LiveData<Boolean> = _infoDialog
private val _listOfLatlng = MutableLiveData(HashMap<LatLng,String>())
    val listOfLatlng : LiveData<HashMap<LatLng,String>> = _listOfLatlng
    private val _isLoading = MutableLiveData(false)
val isLoading : LiveData<Boolean> = _isLoading
    private val _listOfLatlngChanged = MutableLiveData(false)
    val listOfLatlngChanged : LiveData<Boolean> = _listOfLatlngChanged
    private val _selectedCategoryChanged = MutableLiveData(false)
    val selectedCategoryChanged : LiveData<Boolean> = _selectedCategoryChanged

    fun onSelectedChange(newSelectionStatus : Boolean) {
        _selectedCategoryChanged.value = newSelectionStatus
    }
    fun onListOfLatLngChangedStatus(newStatusOfList : Boolean){

        _listOfLatlngChanged.value = newStatusOfList
    }
    fun onListOfLatLngChanged(newListOfLatLng :HashMap<LatLng,String>)
    {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1000)
            _listOfLatlng.value = newListOfLatLng
            _isLoading.value = false
        }


    }

    fun onInfoDialogStatusChanged(newInfoDialogStatus : Boolean) {

        _infoDialog.value = newInfoDialogStatus


    }
     fun onNewContributeLatLng(newContributeLatLng: LatLng)
     {
         _contributeLatLng.value = newContributeLatLng


     }


    fun onDialogStatusChanged(newDialogStatus : Boolean){

                _dialogStatus.value = newDialogStatus
    }

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