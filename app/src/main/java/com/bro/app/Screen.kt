package com.bro.app

sealed class Screen {
    object List : Screen()
    data class Detail(val type: AnimationType) : Screen()
}
