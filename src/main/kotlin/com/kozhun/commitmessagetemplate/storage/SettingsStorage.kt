package com.kozhun.commitmessagetemplate.storage

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "commit-message-template")
class SettingsStorage : SimplePersistentStateComponent<SettingsState>(getDefaultState()) {
    companion object {
        @JvmStatic
        fun getInstance(project: Project): SettingsStorage = project.service<SettingsStorage>()
    }
}

private fun getDefaultState() = SettingsState()
