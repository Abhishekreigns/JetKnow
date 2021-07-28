package com.reignscanary.jetknow.backend

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.reignscanary.jetknow.activities.MainActivity

fun updateDetails(
    context: Context, searchText: String, name: String,
    number: String,
    address: String,
    contributeLatLng: LatLng
) {

    val databaseInstance = FirebaseDatabase.getInstance()
    val user = FirebaseAuth.getInstance()
    val  dataRef : DatabaseReference = databaseInstance.reference
    val id = dataRef.push().key.toString()
    val contribution = Contributions(id,contributeLatLng.latitude,contributeLatLng.longitude,name,number,address,searchText)

    if(number.isBlank() || name.isBlank() || searchText.isBlank() || address.isBlank()){

        Toast.makeText(context,"Enter all the details", Toast.LENGTH_SHORT).show()
    }
    else{
        dataRef.child("Categories").child(searchText).child(contribution.number)
            .setValue(contribution).addOnCompleteListener {
                Toast.makeText(context, "Done!!", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, MainActivity::class.java))
            }

        user.uid?.let { dataRef.child("Contributors").child(it).setValue(contribution.id)
            .addOnSuccessListener {

                Toast.makeText(context,"Thanks for contributing!!", Toast.LENGTH_SHORT).show()
            }
        }



    }


}
