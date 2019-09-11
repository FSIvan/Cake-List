package com.ivanwooll.cakelist.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> AppCompatActivity.createViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}