package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseEvent

sealed class HomeEvent : BaseEvent {

    data class TestEvent(val test: String) : HomeEvent()
}
