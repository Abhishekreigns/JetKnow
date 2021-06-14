package com.reignscanary.jetknow

import android.content.Context
import android.util.ArrayMap
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*

var listOfLatLng = HashMap<LatLng,String>()
fun search(category: String,context : Context) :HashMap<LatLng,String> {

    val databaseInstance = FirebaseDatabase.getInstance()
    val  dataRef : DatabaseReference = databaseInstance.reference.child("Categories").child(category)

    val valueEventListener = object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            //to remove previously chosen services
            listOfLatLng.clear()
            for(keySnapshot : DataSnapshot in snapshot.children){

                val contr : Contributions? = keySnapshot.getValue(Contributions::class.java)

                if (contr != null) {

                   listOfLatLng.put(LatLng(contr.lat,contr.lng),contr.searchText)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,error.message, Toast.LENGTH_SHORT).show()
        }
    }
    dataRef.addValueEventListener(valueEventListener)

return listOfLatLng
}
