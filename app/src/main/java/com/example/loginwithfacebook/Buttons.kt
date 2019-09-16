package com.example.loginwithfacebook

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.size
import com.lamudi.phonefield.PhoneEditText
import com.lamudi.phonefield.PhoneInputLayout
import kotlinx.android.synthetic.main.activity_main.view.*

class Buttons @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : PhoneEditText(context, attrs, defStyleAttr) {

    init {
        setPadding(0,0,0,0)
    }
}