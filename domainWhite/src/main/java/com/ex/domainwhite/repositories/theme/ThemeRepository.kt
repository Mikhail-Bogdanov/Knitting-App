package com.ex.domainwhite.repositories.theme

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    fun getSavedTheme(): Flow<Boolean>

    suspend fun saveTheme(darkTheme: Boolean)

}