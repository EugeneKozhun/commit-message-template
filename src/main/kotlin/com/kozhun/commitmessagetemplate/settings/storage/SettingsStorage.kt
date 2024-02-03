package com.kozhun.commitmessagetemplate.settings.storage

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

/**
 * A class that provides storage and retrieval functionality for project settings.
 */
@Service(Service.Level.PROJECT)
@State(name = "commit-message-template")
class SettingsStorage : SimplePersistentStateComponent<SettingsState>(getDefaultState()) {

    fun updateState() {
        loadState(state)
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): SettingsStorage = project.service<SettingsStorage>()
    }
}

private fun getDefaultState() = SettingsState()
