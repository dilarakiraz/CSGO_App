package com.dilara.csgo_app.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LanguageViewModel : ViewModel() {
    var selectedLanguage = mutableStateOf("tr")
} 