package com.kozhun.commitmessagetemplate.settings

import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.settings.ui.CommitMessageTemplateSettingsPage
import javax.swing.JComponent

class CommitMessageTemplateSettings(
    private val project: Project
) : ConfigurableWithId {

    private var page: CommitMessageTemplateSettingsPage? = null

    override fun createComponent(): JComponent? {
        page = CommitMessageTemplateSettingsPage()
        page?.also {
            it.initialize(project)
        }
        return page?.getRootPanel()
    }

    override fun isModified(): Boolean {
        return page?.isModified() ?: false
    }

    override fun apply() {
        page?.saveSettings()
    }

    override fun disposeUIResources() {
        // TODO: is needed here?
        page = null
    }

    override fun getDisplayName(): String {
        return "Commit Message Template";
    }

    override fun getId(): String {
        return "preferences.CommitMessageTemplateConfigurable";
    }

    override fun getHelpTopic(): String {
        return "Some help info"
    }
}
