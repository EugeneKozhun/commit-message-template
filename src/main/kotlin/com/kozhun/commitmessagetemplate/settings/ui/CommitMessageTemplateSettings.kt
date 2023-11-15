package com.kozhun.commitmessagetemplate.settings.ui

import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import java.util.ResourceBundle
import javax.swing.JComponent

class CommitMessageTemplateSettings(
    private val project: Project
) : ConfigurableWithId {

    private var settingsStorage: SettingsStorage? = null
    private var settingsPage: DialogPanel? = null
    private var patternField: JBTextArea? = null
    private var taskIdRegexField: JBTextField? = null
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
            collapsibleGroup(resourceBundle!!.getString("settings.custom.title")) {
                row {
                    taskIdRegexField = expandableTextField()
                        .apply {
                            label(resourceBundle!!.getString("settings.custom.task-id.label"))
                            horizontalAlign(HorizontalAlign.FILL)
                            comment(comment = "Default: ${BranchTaskIdReplacer.DEFAULT_TASK_ID_REGEX}")
                        }
                        .component
                }
            }
        }
        return settingsPage
    }

    override fun isModified(): Boolean = patternField?.text != settingsStorage?.state?.pattern.orEmpty() ||
            taskIdRegexField?.text != settingsStorage?.state?.taskIdRegex.orEmpty()

    override fun apply() {
        settingsStorage?.apply {
            patternField?.also { setPattern(it.text) }
            taskIdRegexField?.also { setTaskIdRegExp(it.text) }
        }
    }

    override fun reset() {
        settingsStorage?.apply {
            patternField?.text = state.pattern
            taskIdRegexField?.text = state.taskIdRegex
        }
    }

    override fun disposeUIResources() {
        settingsStorage = null
        settingsPage = null
        patternField = null
        taskIdRegexField = null
        resourceBundle = null
    }

    override fun getDisplayName(): String {
        return "Commit Message Template";
    }

    override fun getId(): String {
        return "preferences.CommitMessageTemplateConfigurable";
    }
}
