package com.example.loginwithfacebook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException


class MainActivity : AppCompatActivity() {
    private lateinit var callbackManager: CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = getColor(R.color.white)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        callbackManager = CallbackManager.Factory.create()
        loginButton.setPermissions(listOf("email","public_profile"))
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("asdfasdf","onSuccess: ${loginResult.accessToken.token}")
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })

        val accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        var isLoggedIn = accessToken != null && !accessToken!!.isExpired

        if(isLoggedIn){
            if (accessToken != null) {
                loadUserProfile(accessToken)
            }
        }else{
            Log.d("asdfasdf","isLoggedIn false")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadUserProfile(newAccessToken: AccessToken){
        val request = GraphRequest.newMeRequest(newAccessToken, GraphRequest.GraphJSONObjectCallback { `object`, response ->
            try {
                val firstName = `object`.getString("first_name")
                val lastName = `object`.getString("last_name")
                val email = `object`.getString("email")
                val id = `object`.getString("id")
                Log.d("asdfasdf"," firstName: $firstName\n  lastName: $lastName\n email: $email\n id: $id")
            }catch (exception : JSONException){
                exception.printStackTrace()
            }
        })
        val parameters = Bundle()
        parameters.putString("fields","first_name,last_name,email,id")
        request.parameters = parameters
        request.executeAsync()
    }




}
