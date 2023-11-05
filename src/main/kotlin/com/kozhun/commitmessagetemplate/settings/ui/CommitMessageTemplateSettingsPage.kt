package com.kozhun.commitmessagetemplate.settings.ui

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import javax.swing.JPanel
import javax.swing.JTextField


class CommitMessageTemplateSettingsPage {
    private lateinit var rootPanel: JPanel
    private lateinit var patternField: JTextField

    private var storage: SettingsStorage? = null

    fun initialize(project: Project) {
        storage = project.service<SettingsStorage>()
        storage?.also {
            patternField.text = it.state.pattern
        }
    }

    fun getRootPanel(): JPanel {
        return rootPanel
    }

    fun saveSettings() {
        storage?.also {
            println("Save settings")
            it.setPattern(patternField.text)
        }
    }

    fun isModified(): Boolean {
        return patternField.text != storage?.state?.pattern
    }
}
