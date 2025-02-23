package com.meronacompany.feature.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Repository 인터페이스를 일반화하여 여러 Repository에 사용할 수 있도록 함
class ViewModelFactory<VM : ViewModel, R>(
    private val viewModelClass: Class<VM>,
    private val repository: R,
    private val creator: (R) -> VM
) : ViewModelProvider.Factory {

    // ViewModel을 생성하는 일반화된 메서드
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            return creator(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}