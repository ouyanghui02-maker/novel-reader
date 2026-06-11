package com.novel.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class SettingsState(
    val fontSize: Int = 16,
    val lineSpacing: Float = 1.8f,
    val pageAnimation: String = "仿真",
    val isDarkMode: Boolean = false,
    val followSystem: Boolean = true
)

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    
    private val _settings = MutableStateFlow(SettingsState())
    val settings: StateFlow<SettingsState> = _settings
    
    fun updateFontSize(size: Int) {
        _settings.value = _settings.value.copy(fontSize = size)
    }
    
    fun updateLineSpacing(spacing: Float) {
        _settings.value = _settings.value.copy(lineSpacing = spacing)
    }
    
    fun updatePageAnimation(animation: String) {
        _settings.value = _settings.value.copy(pageAnimation = animation)
    }
    
    fun updateDarkMode(enabled: Boolean) {
        _settings.value = _settings.value.copy(isDarkMode = enabled)
    }
    
    fun updateFollowSystem(enabled: Boolean) {
        _settings.value = _settings.value.copy(followSystem = enabled)
    }
}