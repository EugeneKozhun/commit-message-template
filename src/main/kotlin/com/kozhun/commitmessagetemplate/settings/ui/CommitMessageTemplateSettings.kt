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
import com.kozhun.commitmessagetemplate.service.replacer.impl.ProjectNameReplacer
import com.kozhun.commitmessagetemplate.settings.enums.StringCase
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import com.kozhun.commitmessagetemplate.settings.util.PatternEditorUtil
import com.kozhun.commitmessagetemplate.util.storage
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
    private lateinit var projectNameRegexField: JBTextField
    private lateinit var projectNameSeparatorField: JBTextField
    private lateinit var projectNamePostprocessorField: ComboBox<String>
    private lateinit var patternEditor: Editor

    override fun createComponent(): JComponent {
        settingsStorage = project.storage()
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
            group("Advanced Settings", false) {
                collapsibleGroup(resourceBundle.getString("settings.advanced.task-id.title")) {
                    row {
                        taskIdRegexField = expandableTextField()
                            .apply {
                                label(resourceBundle.getString("settings.advanced.common.label"))
                                comment(comment = "Default: ${BranchTaskIdReplacer.DEFAULT_REGEX}")
                                align(AlignX.FILL)
                            }.component
                    }
                }.apply {
                    expanded = !isUsedDefaultSettingsForTaskId()
                }.withoutGaps()
                collapsibleGroup(resourceBundle.getString("settings.advanced.type.title")) {
                    row {
                        typeRegexField = expandableTextField()
                            .apply {
                                label(resourceBundle.getString("settings.advanced.common.label"))
                                comment(comment = "Default: ${BranchTypeReplacer.DEFAULT_REGEX}")
                                align(AlignX.FILL)
                            }.component
                    }
                    row {
                        typePostprocessorField = comboBox(StringCase.values().map { it.label }, null)
                            .apply {
                                label(resourceBundle.getString("settings.advanced.common.postprocess"))
                            }.component
                    }
                }.apply {
                    expanded = !isUsedDefaultSettingsForType()
                }.withoutGaps()
                collapsibleGroup(resourceBundle.getString("settings.advanced.project-name.title")) {
                    row {
                        projectNameRegexField = expandableTextField()
                            .apply {
                                label(resourceBundle.getString("settings.advanced.common.label"))
                                comment(comment = "Default: ${ProjectNameReplacer.DEFAULT_REGEX}")
                                align(AlignX.FILL)
                            }.component
                    }
                    row {
                        projectNameSeparatorField = textField()
                            .apply {
                                label(resourceBundle.getString("settings.advanced.common.separator"))
                                comment(comment = "Default: ${ProjectNameReplacer.DEFAULT_SEPARATOR}")
                                align(AlignX.FILL)
                            }.component
                    }
                    row {
                        projectNamePostprocessorField = comboBox(StringCase.values().map { it.label }, null)
                            .apply {
                                label(resourceBundle.getString("settings.advanced.common.postprocess"))
                            }.component
                    }
                }.apply {
                    expanded = !isUsedDefaultSettingsForProjectName()
                }.withoutGaps()
            }
        }
    }

    override fun isModified(): Boolean {
        val state = settingsStorage.state
        return patternEditor.document.text != state.pattern.orEmpty() ||
                taskIdRegexField.text != state.taskIdRegex.orEmpty() ||
                typeRegexField.text != state.typeRegex.orEmpty() ||
                isTypePostprocessorModified() ||
                projectNameRegexField.text != state.projectNameRegex.orEmpty() ||
                projectNameSeparatorField.text != state.projectNameSeparator.orEmpty() ||
                isProjectNamePostprocessorModified()
    }

    override fun apply() {
        settingsStorage.apply {
            state.pattern = patternEditor.document.text

            state.taskIdRegex = taskIdRegexField.text

            state.typeRegex = typeRegexField.text
            state.typePostprocessor = typePostprocessorField.item

            state.projectNameRegex = projectNameRegexField.text
            state.projectNameSeparator = projectNameSeparatorField.text
            state.projectNamePostprocessor = projectNamePostprocessorField.item
        }.updateState()
    }

    override fun reset() {
        runWriteAction {
            settingsStorage.apply {
                patternEditor.document.setText(state.pattern.orEmpty())

                taskIdRegexField.text = state.taskIdRegex

                typeRegexField.text = state.typeRegex
                typePostprocessorField.item = state.typePostprocessor?.takeIf { it.isNotBlank() } ?: StringCase.NONE.label

                projectNameRegexField.text = state.projectNameRegex
                projectNameSeparatorField.text = state.projectNameSeparator
                projectNamePostprocessorField.item = state.projectNamePostprocessor?.takeIf { it.isNotBlank() } ?: StringCase.NONE.label
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

    private fun isTypePostprocessorModified(): Boolean {
        val postprocessor = settingsStorage.state.typePostprocessor
        val isEmptyState = postprocessor.isNullOrBlank()
        return (!isEmptyState && typePostprocessorField.item != postprocessor)
                || (isEmptyState && typePostprocessorField.item != StringCase.NONE.label)
    }

    private fun isProjectNamePostprocessorModified(): Boolean {
        val postprocessor = settingsStorage.state.projectNamePostprocessor
        val isEmptyState = postprocessor.isNullOrBlank()
        return (!isEmptyState && projectNamePostprocessorField.item != postprocessor)
                || (isEmptyState && projectNamePostprocessorField.item != StringCase.NONE.label)
    }

    private fun isUsedDefaultSettingsForTaskId(): Boolean {
        return settingsStorage.state.taskIdRegex.isNullOrBlank()
    }

    private fun isUsedDefaultSettingsForType(): Boolean {
        val state = settingsStorage.state
        return state.typeRegex.isNullOrBlank() &&
                (state.typePostprocessor.isNullOrBlank() || state.typePostprocessor == StringCase.NONE.label)
    }

    private fun isUsedDefaultSettingsForProjectName(): Boolean {
        val state = settingsStorage.state
        return state.projectNameRegex.isNullOrBlank() &&
                state.projectNameSeparator.isNullOrBlank() &&
                (state.projectNamePostprocessor.isNullOrBlank() || state.projectNamePostprocessor == StringCase.NONE.label)
    }

    companion object {
        private const val TEXT_AREA_HEIGHT = 125
    }
}

private fun Row.withoutGaps() = apply {
    topGap(TopGap.NONE)
    bottomGap(BottomGap.NONE)
}
