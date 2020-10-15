package com.example.colornoteclone.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.colornoteclone.BuildConfig

fun Context.showToast(toastMessage:String){
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}



fun Context.showLog(tagValue:String,message:String){
    Log.d(tagValue, message)
}