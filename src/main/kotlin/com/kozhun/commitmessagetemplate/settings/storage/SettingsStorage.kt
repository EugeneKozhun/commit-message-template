package com.kozhun.commitmessagetemplate.settings.storage

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State

@Service(Service.Level.PROJECT)
@State(name = "commit-message-template")
class SettingsStorage : SimplePersistentStateComponent<SettingsState>(getDefaultState()) {

    fun setPattern(pattern: String) {
        state.pattern = pattern
        loadState(state)
    }
}

private fun getDefaultState() = SettingsState()
