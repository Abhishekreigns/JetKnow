package com.reignscanary.jetknow

import android.content.Context
import android.util.ArrayMap
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*

var listOfLatLng = HashMap<LatLng,String>()
fun search(category: String,context : Context)  : HashMap<LatLng,String> {

    val databaseInstance = FirebaseDatabase.getInstance()
    val  dataRef : DatabaseReference = databaseInstance.reference.child("Categories").child(category)
    val valueEventListener = object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            //to remove previously chosen services
            listOfLatLng.clear()
            for(keySnapshot : DataSnapshot in snapshot.children){

                val contr : Contributions? = keySnapshot.getValue(Contributions::class.java)

                if (contr != null) {

                   listOfLatLng.put(LatLng(contr.lat,contr.lng),contr.number)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    }
    dataRef.addValueEventListener(valueEventListener)
    //updating in the viewmodel

    return  listOfLatLng
}
