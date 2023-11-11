package com.kozhun.commitmessagetemplate.settings.page

import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import java.util.ResourceBundle
import javax.swing.JComponent

class CommitMessageTemplateSettings(
    private val project: Project
) : ConfigurableWithId {

    private var settingsStorage: SettingsStorage? = null
    private var settingsPage: DialogPanel? = null
    private var patternField: JBTextArea? = null
    private var resourceBundle: ResourceBundle? = null

    override fun createComponent(): JComponent? {
        settingsStorage = SettingsStorage.getInstance(project)
        resourceBundle = ResourceBundle.getBundle("messages")
        settingsPage = panel {
            row {
                patternField = textArea()
                    .apply {
                        label(resourceBundle!!.getString("settings.message-pattern-label"), LabelPosition.TOP)
                        comment(comment = resourceBundle!!.getString("settings.message-pattern-notes"))
                        horizontalAlign(HorizontalAlign.FILL)
                    }
                    .component
            }
        }
        return settingsPage
    }

    override fun isModified(): Boolean {
        return patternField?.text != settingsStorage?.state?.pattern
    }

    override fun apply() {
        patternField?.apply {
            settingsStorage?.setPattern(text)
        }
    }

    override fun reset() {
        settingsStorage?.apply {
            patternField?.text = state.pattern
        }
    }

    override fun disposeUIResources() {
        settingsStorage = null
        settingsPage = null
        patternField = null
        resourceBundle = null
    }

    override fun getDisplayName(): String {
        return "Commit Message Template";
    }

    override fun getId(): String {
        return "preferences.CommitMessageTemplateConfigurable";
    }
}
