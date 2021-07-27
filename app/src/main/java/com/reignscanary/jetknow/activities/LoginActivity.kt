package com.reignscanary.jetknow.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.reignscanary.jetknow.R
import com.reignscanary.jetknow.composables.LoginPage
import com.reignscanary.jetknow.myTheme.JetKnowTheme

lateinit var googleSignInOptions: GoogleSignInOptions
private lateinit var firebaseAuth: FirebaseAuth
class LoginActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()
        val latlng: LatLng? = intent.extras?.getParcelable("latlng")

        //Composing the login screen
        setContent {
            JetKnowTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginPage(latlng)
                }
            }
        }
        googleAuthenticate()
    }


    //Authenticating with google
    private fun googleAuthenticate() {
        Toast.makeText(this, "Choose your google account", Toast.LENGTH_SHORT).show()

        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SplashActivity.RC_SIGN_IN)
    }

    //using user google account
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

}

