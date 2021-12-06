package com.hayeseve.randomapp.error

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.hayeseve.randomapp.activity.MainActivity

object ErrorHandle {

    fun handleError(context : Context, message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        context.startActivity(Intent(context, MainActivity::class.java));
    }

    fun handleAPIError(context : Context, code : Int) {
        Toast.makeText(context, "API Failure, Code : $code", Toast.LENGTH_SHORT).show();
        context.startActivity(Intent(context, MainActivity::class.java));
    }
}