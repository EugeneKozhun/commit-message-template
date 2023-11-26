package com.kozhun.commitmessagetemplate.settings.ui

import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.COLUMNS_SHORT
import com.intellij.ui.dsl.builder.DslComponentProperty
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.Gaps
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.util.ui.JBEmptyBorder
import com.intellij.util.ui.JBFont
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import java.awt.Dimension
import java.util.ResourceBundle
import javax.swing.BorderFactory
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
                patternField = JBTextArea().defaultUI()
                scrollCell(JBScrollPane(patternField))
                    .apply {
                        label(resourceBundle.getString("settings.message-pattern-label"), LabelPosition.TOP)
                        comment(comment = resourceBundle.getString("settings.message-pattern-notes"))
                        horizontalAlign(HorizontalAlign.FILL)
                    }
                    .applyToComponent {
                        border = BorderFactory.createEmptyBorder()
                        preferredSize = Dimension(preferredSize.width, TEXT_AREA_HEIGHT)
                    }
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
                expanded = isNotDefaultSettingsApplied()
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

    private fun isNotDefaultSettingsApplied() = settingsStorage.state.taskIdRegex?.isNotBlank() ?: false

    companion object {
        private const val TEXT_AREA_HEIGHT = 75
    }
}

/**
 * Sets the default UI properties for a JBTextArea.
 * Duplicated from textArea().
 */
private fun JBTextArea.defaultUI(): JBTextArea {
    border = JBEmptyBorder(3, 5, 3, 5)
    columns = COLUMNS_SHORT
    font = JBFont.regular()
    emptyText.setFont(JBFont.regular())
    putClientProperty(DslComponentProperty.VISUAL_PADDINGS, Gaps.EMPTY)
    return this
}
