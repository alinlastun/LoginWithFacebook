package com.example.loginwithfacebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.toolbar_fragment.view.*

class Fragment1 : Fragment() {
    open val hasToolbarBackButton = true
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
        //iew.layoutStub.layoutResource = getLayoutResource()
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

        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity?.setSupportActionBar(toolbar)
        /*appCompatActivity?.setTitle(titleRes)*/
    }

}