package com.kozhun.commitmessagetemplate.settings.ui

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import com.kozhun.commitmessagetemplate.settings.util.PatternEditorUtil
import java.awt.Dimension
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
    private lateinit var taskIdRegexField: JBTextField
    private lateinit var patternEditor: Editor

    override fun createComponent(): JComponent {
        settingsStorage = SettingsStorage.getInstance(project)
        patternEditor = PatternEditorUtil.createEditor(project)
        val resourceBundle = ResourceBundle.getBundle("messages")
        return panel {
            row {
                cell(patternEditor.component)
                    .apply {
                        align(AlignX.FILL)
                        comment(comment = resourceBundle.getString("settings.message-pattern-notes"))
                    }
                    .applyToComponent {
                        preferredSize = Dimension(preferredSize.width, TEXT_AREA_HEIGHT)
                    }
            }
            collapsibleGroup(resourceBundle.getString("settings.settings.title")) {
                row {
                    taskIdRegexField = expandableTextField()
                        .apply {
                            label(resourceBundle.getString("settings.settings.task-id.label"))
                            comment(comment = "Default: ${BranchTaskIdReplacer.DEFAULT_TASK_ID_REGEX}")
                            align(AlignX.FILL)
                        }
                        .component
                }
            }.apply {
                topGap(TopGap.NONE)
                expanded = isNotDefaultSettingsApplied()
            }
        }
    }

    override fun isModified(): Boolean = patternEditor.document.text != settingsStorage.state.pattern.orEmpty() ||
            taskIdRegexField.text != settingsStorage.state.taskIdRegex.orEmpty()

    override fun apply() {
        settingsStorage.apply {
            patternEditor.document.also { setPattern(it.text) }
            taskIdRegexField.also { setTaskIdRegExp(it.text) }
        }
    }

    override fun reset() {
        runWriteAction {
            settingsStorage.apply {
                patternEditor.document.setText(state.pattern.orEmpty())
                taskIdRegexField.text = state.taskIdRegex
            }
        }
    }

    override fun disposeUIResources() {
        super.disposeUIResources()
        PatternEditorUtil.dispose(patternEditor)
    }

    override fun getDisplayName(): String {
        return "Commit Message Template"
    }

    override fun getId(): String {
        return "preferences.CommitMessageTemplateConfigurable"
    }

    private fun isNotDefaultSettingsApplied() = settingsStorage.state.taskIdRegex?.isNotBlank() ?: false

    companion object {
        private const val TEXT_AREA_HEIGHT = 125
    }
}
