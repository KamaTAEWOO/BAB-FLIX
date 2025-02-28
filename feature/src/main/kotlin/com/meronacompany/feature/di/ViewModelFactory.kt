package com.meronacompany.feature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

class ViewModelFactory<T : ViewModel>(
    private val viewModelClass: KClass<T>,
    private val creator: () -> T
) : ViewModelProvider.Factory {

    override fun <U : ViewModel> create(modelClass: Class<U>): U {
        if (modelClass.isAssignableFrom(viewModelClass.java)) {
            @Suppress("UNCHECKED_CAST")
            return creator() as U
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}