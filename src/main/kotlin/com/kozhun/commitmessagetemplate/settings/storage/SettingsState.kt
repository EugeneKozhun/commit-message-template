package com.kozhun.commitmessagetemplate.settings.storage

import com.intellij.openapi.components.BaseState

class SettingsState : BaseState() {
    var pattern by string("")
    var taskIdRegex by string("")
}
