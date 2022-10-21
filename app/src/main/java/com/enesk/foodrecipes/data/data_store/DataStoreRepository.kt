package com.enesk.foodrecipes.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.enesk.foodrecipes.data.data_store.model.MealAndDietType
import com.enesk.foodrecipes.util.Constants.DEFAULT_DIET_TYPE
import com.enesk.foodrecipes.util.Constants.DEFAULT_MEAL_TYPE
import com.enesk.foodrecipes.util.Constants.PREFERENCES_DIET_TYPE
import com.enesk.foodrecipes.util.Constants.PREFERENCES_DIET_TYPE_ID
import com.enesk.foodrecipes.util.Constants.PREFERENCES_MEAL_TYPE
import com.enesk.foodrecipes.util.Constants.PREFERENCES_MEAL_TYPE_ID
import com.enesk.foodrecipes.util.Constants.PREFERENCES_NAME
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    private val context: Context
) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferenceKeys.selectedMealType] = mealType
            mutablePreferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            mutablePreferences[PreferenceKeys.selectedDietType] = dietType
            mutablePreferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0

            MealAndDietType(
                selectedMealType = selectedMealType,
                selectedMealTypeId = selectedMealTypeId,
                selectedDietType = selectedDietType,
                selectedDietTypeId = selectedDietTypeId
            )
        }
}