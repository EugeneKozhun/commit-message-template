package com.kozhun.commitmessagetemplate.settings.ui

import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import java.util.ResourceBundle
import javax.swing.JComponent

/**
 * Represents the settings for the Commit Message Template plugin.
 *
 * @param project The current project.
 */
class CommitMessageTemplateSettings(
    private val project: Project
) : ConfigurableWithId {

    private lateinit var settingsStorage: SettingsStorage
    private lateinit var patternField: JBTextArea
    private lateinit var taskIdRegexField: JBTextField

    override fun createComponent(): JComponent {
        settingsStorage = SettingsStorage.getInstance(project)
        val resourceBundle = ResourceBundle.getBundle("messages")
        return panel {
            row {
                patternField = textArea()
                    .apply {
                        label(resourceBundle.getString("settings.message-pattern-label"), LabelPosition.TOP)
                        comment(comment = resourceBundle.getString("settings.message-pattern-notes"))
                        horizontalAlign(HorizontalAlign.FILL)
                    }
                    .component
            }
            collapsibleGroup(resourceBundle.getString("settings.settings.title")) {
                row {
                    taskIdRegexField = expandableTextField()
                        .apply {
                            label(resourceBundle.getString("settings.settings.task-id.label"))
                            comment(comment = "Default: ${BranchTaskIdReplacer.DEFAULT_TASK_ID_REGEX}")
                            horizontalAlign(HorizontalAlign.FILL)
                        }
                        .component
                }
            }.apply {
                expanded = settingsStorage.state.taskIdRegex?.isNotBlank() ?: false
            }
        }
    }

    override fun isModified(): Boolean = patternField.text != settingsStorage.state.pattern.orEmpty() ||
            taskIdRegexField.text != settingsStorage.state.taskIdRegex.orEmpty()

    override fun apply() {
        settingsStorage.apply {
            patternField.also { setPattern(it.text) }
            taskIdRegexField.also { setTaskIdRegExp(it.text) }
        }
    }

    override fun reset() {
        settingsStorage.apply {
            patternField.text = state.pattern
            taskIdRegexField.text = state.taskIdRegex
        }
    }

    override fun getDisplayName(): String {
        return "Commit Message Template"
    }

    override fun getId(): String {
        return "preferences.CommitMessageTemplateConfigurable"
    }
}
