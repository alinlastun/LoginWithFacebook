package com.example.loginwithfacebook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_fragment.*
import kotlinx.android.synthetic.main.toolbar_fragment.view.*
import org.json.JSONException

open class Fragment1 : Fragment() {
    open val hasToolbarBackButton = true
    private lateinit var callbackManager: CallbackManager
    open val hasMenuItems = false
    var toolbar: Toolbar? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (hasToolbarBackButton || hasMenuItems) {
            setHasOptionsMenu(true)
        }

        val view = inflater.inflate(R.layout.toolbar_fragment, container, false)
        view.layoutStub.layoutResource = R.layout.toolbar_fragment
        val childView = view.layoutStub.inflate()
        val childToolbar = childView.findViewById<Toolbar>(R.id.toolbar)
        if (childToolbar == null) {
            view.toolbarStub.layoutResource = R.layout.toolbar
            val toolbarView = view.toolbarStub.inflate()

            toolbar = toolbarView.findViewById(R.id.toolbar)
        } else {
            toolbar = childToolbar
        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        facebookLogin(loginButton)
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity?.setSupportActionBar(toolbar)
        /*appCompatActivity?.setTitle(titleRes)*/
    }


    private fun facebookLogin(facebookLoginButton: LoginButton) {
        callbackManager = CallbackManager.Factory.create()
        facebookLoginButton.setPermissions(listOf("email", "public_profile"))
        facebookLoginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("asdfasdf"," onSuccess")
                }

                override fun onCancel() {
                    Log.d("asdfasdf"," onCancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.d("asdfasdf"," onError: ${exception.message}")
                }
            })

        val accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken!!.isExpired

        if (isLoggedIn) {
            if (accessToken != null) {
                loadUserProfile(accessToken)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        Log.d("asdf34f","daaaaaaaaaaaaaa")
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