package com.reignscanary.jetknow


import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.reignscanary.jetknow.composables.HostOfComposables
import com.reignscanary.jetknow.composables.Screen
import com.reignscanary.jetknow.myTheme.JetKnowTheme


lateinit var googleSignInOptions: GoogleSignInOptions
private lateinit var firebaseAuth: FirebaseAuth
lateinit var locationManager: LocationManager
class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
                    Toast.makeText(this,"Welcome back ${user.displayName}!!",Toast.LENGTH_SHORT).show()

        }
        else{

            googleAuthenticate()
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setContent {
                    JetKnowTheme {
                        Surface {
                            Screen {
                                HostOfComposables(
                                            savedInstanceState = savedInstanceState
                                )

                            }
                        }
                    }


            }

    }

    private fun googleAuthenticate() {
        Toast.makeText(this, "Google accounts choose", Toast.LENGTH_SHORT).show()

        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SplashActivity.RC_SIGN_IN)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val intent = Intent(this, SplashActivity::class.java)
        startActivityForResult(intent, SplashActivity.LOCATION_PERMISSION)
    }





    //usin user google account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SplashActivity.RC_SIGN_IN)
        { val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {


        try {
            val account = task.getResult(ApiException::class.java)
            Toast.makeText(this, "Hi ${account?.displayName}", Toast.LENGTH_SHORT).show()
            firebaseGoogleAuth(account)
            Toast.makeText(this, "$account", Toast.LENGTH_LONG).show()
            if (account != null) {
                firebaseGoogleAuth(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
            // firebaseGoogleAuth(null)
        }


    }

    private fun firebaseGoogleAuth(account: GoogleSignInAccount?) {




        val authCredential : AuthCredential = GoogleAuthProvider.getCredential(account?.idToken,null)
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this, "Authenticated with firebase too :)", Toast.LENGTH_LONG).show()




            }


        }





    }



    companion object {




    }

}












