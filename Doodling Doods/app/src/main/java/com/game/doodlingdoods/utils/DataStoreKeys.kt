package com.game.doodlingdoods.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//object DataStoreKeys {
//    val HAS_LOGGED_IN = booleanPreferencesKey("has_logged_in")
//    val USER_NAME = stringPreferencesKey("user_name")
//}
//
//class DataStoreManager(val context: Context) {
//
//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
//
//    val hasLoggedIn: Flow<Boolean>
//        get() = context.dataStore.data.map { preferences ->
//            preferences[DataStoreKeys.HAS_LOGGED_IN] ?: false
//        }
//
//    val userName: Flow<String>
//        get() =  context.dataStore.data.map { preferences ->
//            preferences[DataStoreKeys.USER_NAME] ?: ""
//        }
//
//
//    suspend fun saveHasLoggedIn(hasLoggedIn: Boolean) {
//        context.dataStore.edit { preferences ->
//            preferences[DataStoreKeys.HAS_LOGGED_IN] = hasLoggedIn
//        }
//    }
//
//    suspend fun saveUserName(userName: String) {
//        context.dataStore.edit { preferences ->
//            preferences[DataStoreKeys.USER_NAME] = userName
//        }
//    }
//}