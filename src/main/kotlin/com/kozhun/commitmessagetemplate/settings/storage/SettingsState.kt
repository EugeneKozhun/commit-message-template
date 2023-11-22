package com.kozhun.commitmessagetemplate.settings.storage

import com.intellij.openapi.components.BaseState

/**
 * Represents the state of the settings in the application.
 */
class SettingsState : BaseState() {
    var pattern by string("")
    var taskIdRegex by string("")
}
