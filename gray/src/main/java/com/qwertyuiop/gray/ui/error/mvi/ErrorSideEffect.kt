package com.qwertyuiop.gray.ui.error.mvi


sealed class ErrorSideEffect {
    data object NavigateToGray : ErrorSideEffect()

    data class ShowSnackBar(
        val message: String
    ) : ErrorSideEffect()
}