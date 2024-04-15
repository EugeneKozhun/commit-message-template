package com.kozhun.commitmessagetemplate.settings.ui

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.toMutableProperty
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_SCOPE_SEPARATOR
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_TASK_ID_REGEX
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_TYPE_REGEX
import com.kozhun.commitmessagetemplate.settings.enums.StringCase
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import com.kozhun.commitmessagetemplate.settings.util.PatternEditorUtil
import com.kozhun.commitmessagetemplate.settings.util.bindNullableText
import com.kozhun.commitmessagetemplate.util.storage
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
    private lateinit var patternEditor: Editor
    private lateinit var panel: DialogPanel

    @Suppress("LongMethod")
    override fun createComponent(): JComponent {
        settingsStorage = project.storage()
        patternEditor = PatternEditorUtil.createEditor(project)
        val resourceBundle = ResourceBundle.getBundle("messages")

        panel = panel {
            row {
                cell(patternEditor.component)
                    .align(AlignX.FILL)
                    .comment(comment = resourceBundle.getString("settings.message-pattern-notes"))
                    .bind(
                        { _ -> patternEditor.document.text.ifBlank { null } },
                        { _, value: String? -> runWriteAction { patternEditor.document.setText(value.orEmpty()) } },
                        settingsStorage.state::pattern.toMutableProperty()
                    )
            }
            row {
                checkBox(resourceBundle.getString("settings.trim-whitespaces-start"))
                    .bindSelected(settingsStorage.state::trimWhitespacesStart)
                checkBox(resourceBundle.getString("settings.trim-whitespaces-end"))
                    .bindSelected(settingsStorage.state::trimWhitespacesEnd)
                checkBox(resourceBundle.getString("settings.duplicated-whitespaces"))
                    .bindSelected(settingsStorage.state::unnecessaryWhitespaces)
            }
            group("Advanced Settings", false) {
                collapsibleGroup(resourceBundle.getString("settings.advanced.task-id.title")) {
                    row {
                        expandableTextField()
                            .label(resourceBundle.getString("settings.advanced.common.label"), LabelPosition.TOP)
                            .comment(comment = "Default: $DEFAULT_TASK_ID_REGEX")
                            .align(AlignX.FILL)
                            .bindNullableText(settingsStorage.state::taskIdRegex)
                    }
                    row {
                        textField()
                            .label(resourceBundle.getString("settings.advanced.task-id.default.value"), LabelPosition.TOP)
                            .comment(resourceBundle.getString("settings.advanced.task-id.default.comment"))
                            .align(AlignX.FILL)
                            .bindNullableText(settingsStorage.state::taskIdDefault)
                        comboBox(StringCase.values().map { it.label })
                            .label(resourceBundle.getString("settings.advanced.common.postprocess"), LabelPosition.TOP)
                            .bindItem(settingsStorage.state::taskIdPostProcessor)
                    }
                }.apply {
                    expanded = !settingsStorage.state.isDefaultTaskFields()
                }.withoutGaps()
                collapsibleGroup(resourceBundle.getString("settings.advanced.type.title")) {
                    row {
                        expandableTextField()
                            .label(resourceBundle.getString("settings.advanced.common.label"), LabelPosition.TOP)
                            .comment(comment = "Default: $DEFAULT_TYPE_REGEX")
                            .align(AlignX.FILL)
                            .bindNullableText(settingsStorage.state::typeRegex)
                    }
                    row {
                        textField()
                            .label(resourceBundle.getString("settings.advanced.type.default-type.value"), LabelPosition.TOP)
                            .comment(resourceBundle.getString("settings.advanced.type.default-type.comment"))
                            .align(AlignX.FILL)
                            .bindNullableText(settingsStorage.state::typeDefault)
                        comboBox(StringCase.values().map { it.label }, null)
                            .label(resourceBundle.getString("settings.advanced.common.postprocess"), LabelPosition.TOP)
                            .bindItem(settingsStorage.state::typePostprocessor)
                    }
                }.apply {
                    expanded = !settingsStorage.state.isDefaultTypeFields()
                }.withoutGaps()
                collapsibleGroup(resourceBundle.getString("settings.advanced.scope.title")) {
                    row {
                        expandableTextField()
                            .label(resourceBundle.getString("settings.advanced.common.label"), LabelPosition.TOP)
                            .align(AlignX.FILL)
                            .bindNullableText(settingsStorage.state::scopeRegex)
                    }
                    row {
                        textField()
                            .label(resourceBundle.getString("settings.advanced.scope.default-value"), LabelPosition.TOP)
                            .comment("When the scope isn't defined.<br/>Default: ${project.name}")
                            .align(AlignX.FILL)
                            .bindNullableText(settingsStorage.state::scopeDefault)
                        textField()
                            .label(resourceBundle.getString("settings.advanced.common.separator"), LabelPosition.TOP)
                            .comment(comment = "If there are multiple scopes.<br/>Default: $DEFAULT_SCOPE_SEPARATOR")
                            .align(AlignX.FILL)
                            .bindNullableText(settingsStorage.state::scopeSeparator)
                        comboBox(StringCase.values().map { it.label })
                            .label(resourceBundle.getString("settings.advanced.common.postprocess"), LabelPosition.TOP)
                            .bindItem(settingsStorage.state::scopePostprocessor)
                    }
                }.apply {
                    expanded = !settingsStorage.state.isDefaultScopeFields()
                }.withoutGaps()
            }
        }

        return panel
    }

    override fun isModified(): Boolean {
        return panel.isModified()
    }

    override fun apply() {
        panel.apply()
    }

    override fun reset() {
        panel.reset()
    }

    override fun disposeUIResources() {
        super.disposeUIResources()
        PatternEditorUtil.dispose(patternEditor)
    }

    override fun getDisplayName(): String {
        return DISPLAY_NAME
    }

    override fun getId(): String {
        return ID
    }

    companion object {
        private const val DISPLAY_NAME = "Commit Message Template"
        private const val ID = "preferences.CommitMessageTemplateConfigurable"
    }
}

private fun Row.withoutGaps() = apply {
    topGap(TopGap.NONE)
    bottomGap(BottomGap.NONE)
}
