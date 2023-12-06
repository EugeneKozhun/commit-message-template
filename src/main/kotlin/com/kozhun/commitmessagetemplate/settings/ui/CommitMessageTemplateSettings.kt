package com.kozhun.commitmessagetemplate.settings.ui

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTypeReplacer
import com.kozhun.commitmessagetemplate.settings.enums.BranchTypePostprocessor
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
    private lateinit var typeRegexField: JBTextField
    private lateinit var typePostprocessorField: ComboBox<String>
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
            collapsibleGroup("Task ID Settings (\$TASK_ID)") {
                row {
                    taskIdRegexField = expandableTextField()
                        .apply {
                            label(resourceBundle.getString("settings.settings.task-id.label"))
                            comment(comment = "Default: ${BranchTaskIdReplacer.DEFAULT_TASK_ID_REGEX}")
                            align(AlignX.FILL)
                        }.component
                }
            }.apply {
                expanded = !usedDefaultSettingsForTaskId()
            }.withoutGaps()
            collapsibleGroup("Branch Type Settings (\$TYPE)") {
                row {
                    typeRegexField = expandableTextField()
                        .apply {
                            label(resourceBundle.getString("settings.settings.type.label"))
                            comment(comment = "Default: ${BranchTypeReplacer.DEFAULT_TYPE_REGEX}")
                            align(AlignX.FILL)
                        }.component
                }
                row {
                    typePostprocessorField = comboBox(BranchTypePostprocessor.values().map { it.label }, null)
                        .apply {
                            label("Postprocess:")
                        }.component
                }
            }.apply {
                expanded = !usedDefaultSettingsForType()
            }.withoutGaps()
        }
    }

    override fun isModified(): Boolean {
        val state = settingsStorage.state
        return patternEditor.document.text != state.pattern.orEmpty() ||
                taskIdRegexField.text != state.taskIdRegex.orEmpty() ||
                typeRegexField.text != state.typeRegex.orEmpty() ||
                typePostprocessorField.item != (state.typePostprocessor ?: BranchTypePostprocessor.NONE.label)
    }

    override fun apply() {
        settingsStorage.apply {
            patternEditor.document.also { setPattern(it.text) }
            taskIdRegexField.also { setTaskIdRegExp(it.text) }
            typeRegexField.also { setTypeRegExp(it.text) }
            typePostprocessorField.also { setTypePostprocessor(it.item) }
        }
    }

    override fun reset() {
        runWriteAction {
            settingsStorage.apply {
                patternEditor.document.setText(state.pattern.orEmpty())
                taskIdRegexField.text = state.taskIdRegex
                typeRegexField.text = state.typeRegex
                typePostprocessorField.item = state.typePostprocessor?.takeIf { it.isNotBlank() } ?: BranchTypePostprocessor.NONE.label
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

    private fun usedDefaultSettingsForTaskId() = settingsStorage.state.taskIdRegex.isNullOrBlank()

    private fun usedDefaultSettingsForType(): Boolean {
        val state = settingsStorage.state
        return state.typeRegex.isNullOrBlank() &&
                (state.typePostprocessor == null || state.typePostprocessor == BranchTypePostprocessor.NONE.label)
    }

    companion object {
        private const val TEXT_AREA_HEIGHT = 125
    }
}

private fun Row.withoutGaps() = apply {
    topGap(TopGap.NONE)
    bottomGap(BottomGap.NONE)
}
