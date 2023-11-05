package com.kozhun.commitmessagetemplate.settings

import com.intellij.openapi.options.ConfigurableWithId
import com.kozhun.commitmessagetemplate.settings.ui.CommitMessageTemplateSettingsPage
import javax.swing.JComponent

class CommitMessageTemplateSettings : ConfigurableWithId {

    private var page: CommitMessageTemplateSettingsPage? = null

    override fun createComponent(): JComponent? {
        page = CommitMessageTemplateSettingsPage()
        page?.run {
            initUI()
        }
        return page?.getRootPanel()
    }

    override fun isModified(): Boolean {
        return true
    }

    override fun apply() {
        TODO("Not yet implemented")
    }

    override fun disposeUIResources() {
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
