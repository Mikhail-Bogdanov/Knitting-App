package com.qwertyuiop.white.ui.composables.main.mvi

sealed class MainSideEffect {

    data object NavigateToSettings : MainSideEffect()

}