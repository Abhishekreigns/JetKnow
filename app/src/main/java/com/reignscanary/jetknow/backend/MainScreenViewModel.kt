package com.reignscanary.jetknow.backend


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import kotlinx.coroutines.*


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
    private val _onLocationFabClick = MutableLiveData(false)
    val onLocationFabClick : LiveData<Boolean> = _onLocationFabClick

        // This function exists just to clear the list of latlng on dismissing a category(should find alternatives)
    fun onListOfLatLngChanged(newListOfLatLng :HashMap<LatLng,String>)
    {
            _isLoading.value = true
            _listOfLatlng.value = newListOfLatLng
            _isLoading.value = false

}
    fun onLocationClicked(newLocationClickStatus : Boolean){

        _onLocationFabClick.value = newLocationClickStatus
    }

    fun onListOfLatLngChangedStatus(newStatusOfList : Boolean){

        _listOfLatlngChanged.value = newStatusOfList
    }

    suspend fun Search(category: String, context: Context) {


            val databaseInstance = FirebaseDatabase.getInstance()
            val dataRef: DatabaseReference = databaseInstance.reference.child("Categories").child(category)

        val listOfLatLng = HashMap<LatLng, String>()
        _isLoading.value = true
        val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            //to remove previously chosen services
            listOfLatLng.clear()
            for (keySnapshot: DataSnapshot in snapshot.children) {
                val contr: Contributions? = keySnapshot.getValue(Contributions::class.java)
                if (contr != null) {
                    listOfLatLng[LatLng(contr.lat, contr.lng)] = contr.number
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    }

    dataRef.addValueEventListener(valueEventListener)
        viewModelScope.launch {
            //delaying until the services are searched(should be done using a coroutine)
            delay(5000)
            _listOfLatlng.value = listOfLatLng
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