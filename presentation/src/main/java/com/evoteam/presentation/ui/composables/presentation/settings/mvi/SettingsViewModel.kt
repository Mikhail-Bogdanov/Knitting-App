package com.evoteam.presentation.ui.composables.presentation.settings.mvi

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.core.os.LocaleListCompat.forLanguageTags
import com.evoteam.core.mviViewModel.MviViewModel
import com.evoteam.domain.useCases.theme.GetSavedThemeUseCase
import com.evoteam.domain.useCases.theme.SaveThemeUseCase
import com.evoteam.presentation.ui.composables.presentation.settings.mvi.SettingsEvent.BackButtonClicked
import com.evoteam.presentation.ui.composables.presentation.settings.mvi.SettingsEvent.Initialize
import com.evoteam.presentation.ui.composables.presentation.settings.mvi.SettingsEvent.LanguageClicked
import com.evoteam.presentation.ui.composables.presentation.settings.mvi.SettingsEvent.ThemeClicked
import com.evoteam.presentation.ui.composables.presentation.settings.mvi.SettingsSideEffect.NavigateToWelcome
import com.evoteam.presentation.ui.composables.presentation.settings.mvi.SettingsSideEffect.PopBackStack
import com.evoteam.presentation.ui.composables.presentation.settings.utils.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class SettingsViewModel(
    private val saveThemeUseCase: SaveThemeUseCase,
    private val getSavedThemeUseCase: GetSavedThemeUseCase
) : MviViewModel<SettingsState, SettingsSideEffect, SettingsEvent>(
    initialState = SettingsState()
) {
    init {
        dispatch(Initialize)
    }

    override fun dispatch(event: SettingsEvent) {
        when (event) {
            BackButtonClicked -> backButtonClicked()
            is LanguageClicked -> languageClicked(event.language)
            is ThemeClicked -> themeClicked(event.darkTheme)
            Initialize -> initialize()
            SettingsEvent.ExpandLanguagesClicked -> expandLanguagesClicked()
            SettingsEvent.WatchTutorialAgainClicked -> watchTutorialAgainClicked()
        }
    }

    private fun watchTutorialAgainClicked() = intent {
        postSideEffect(NavigateToWelcome)
    }

    private fun expandLanguagesClicked() = intent {
        reduce { state.copy(isLanguagesExpanded = !state.isLanguagesExpanded) }
    }

    private fun initialize() = intent {
        getSavedThemeUseCase().collect { isDarkTheme ->
            reduce { state.copy(isDarkTheme = isDarkTheme) }
        }
    }

    private fun themeClicked(darkTheme: Boolean) = intent {
        saveThemeUseCase(darkTheme)
    }

    private fun backButtonClicked() = intent {
        postSideEffect(PopBackStack)
    }

    private fun languageClicked(language: Language) = intent {
        withContext(Dispatchers.Main) {
            setApplicationLocales(forLanguageTags(language.tag))
        }
        reduce { state.copy(isLanguagesExpanded = false) }
    }
}