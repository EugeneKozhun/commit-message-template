package com.kozhun.commitmessagetemplate.ui.page

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.options.ConfigurableWithId
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.ContextHelpLabel
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.toMutableProperty
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_SCOPE_SEPARATOR
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_TASK_ID_REGEX
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_TYPE_REGEX
import com.kozhun.commitmessagetemplate.enums.StringCase
import com.kozhun.commitmessagetemplate.storage.SettingsStorage
import com.kozhun.commitmessagetemplate.ui.components.PatternEditorBuilder
import com.kozhun.commitmessagetemplate.ui.util.bindNullableText
import com.kozhun.commitmessagetemplate.util.storage
import java.util.ResourceBundle
import javax.swing.JComponent
import javax.swing.ListSelectionModel

data class SynonymPair(var key: String, var value: String)

class SynonymColumnInfo(
    name: String,
    private val getter: (SynonymPair) -> String,
    private val setter: (SynonymPair, String) -> Unit
) : ColumnInfo<SynonymPair, String>(name) {
    override fun valueOf(item: SynonymPair): String = getter(item)
    override fun isCellEditable(item: SynonymPair): Boolean = true
    override fun setValue(item: SynonymPair, value: String) = setter(item, value)
}

@Suppress("TooManyFunctions")
class CMTSettingsPage(
    private val project: Project
) : ConfigurableWithId {
    private lateinit var settingsStorage: SettingsStorage
    private lateinit var patternEditor: Editor
    private lateinit var panel: DialogPanel

    private lateinit var tableModel: ListTableModel<SynonymPair>
    private lateinit var table: TableView<SynonymPair>

    @Suppress("LongMethod")
    override fun createComponent(): JComponent {
        settingsStorage = project.storage()
        patternEditor = PatternEditorBuilder.buildEditor(project)

        tableModel = ListTableModel(
            arrayOf(
                SynonymColumnInfo("Value", { it.key }, { item, value -> item.key = value }),
                SynonymColumnInfo("Synonym", { it.value }, { item, value -> item.value = value })
            ),
            settingsStorage.state.typeSynonyms
                .map { SynonymPair(it.key, it.value) }
                .toMutableList()
        )

        table = TableView(tableModel)
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)

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
            group("Variable settings", false) {
                collapsibleGroup(resourceBundle.getString("settings.advanced.task-id.title")) {
                    row {
                        expandableTextField()
                            .label(resourceBundle.getString("settings.advanced.common.label"), LabelPosition.TOP)
                            .comment(comment = "Default regex: $DEFAULT_TASK_ID_REGEX")
                            .align(AlignX.FILL)
                            .resizableColumn()
                            .bindNullableText(settingsStorage.state::taskIdRegex)
                        cell(ContextHelpLabel.create("Value extracted from current branch name")).align(AlignX.RIGHT)
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
                            .comment(comment = "Default regex: $DEFAULT_TYPE_REGEX")
                            .align(AlignX.FILL)
                            .resizableColumn()
                            .bindNullableText(settingsStorage.state::typeRegex)
                        cell(ContextHelpLabel.create("Value extracted from current branch name")).align(AlignX.RIGHT)
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
                    group("\$TYPE Synonyms", indent = false) {
                        row {
                            cell(createSynonymTablePanel()).align(AlignX.FILL)
                        }.resizableRow()
                    }.withoutGaps()
                }.apply {
                    expanded = !settingsStorage.state.isDefaultTypeFields()
                }.withoutGaps()
                collapsibleGroup(resourceBundle.getString("settings.advanced.scope.title")) {
                    row {
                        expandableTextField()
                            .label(resourceBundle.getString("settings.advanced.common.label"), LabelPosition.TOP)
                            .align(AlignX.FILL)
                            .resizableColumn()
                            .bindNullableText(settingsStorage.state::scopeRegex)
                        cell(ContextHelpLabel.create("Value extracted from the modified file's path"))
                            .align(AlignX.RIGHT)
                    }
                    row {
                        textField()
                            .label(resourceBundle.getString("settings.advanced.scope.default-value"), LabelPosition.TOP)
                            .comment("When the scope isn't defined.")
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
        return panel.isModified() || settingsStorage.state.typeSynonyms != getSynonymsMap()
    }

    override fun apply() {
        settingsStorage.state.typeSynonyms = getSynonymsMap().toMutableMap()
        panel.apply()
    }

    override fun reset() {
        tableModel.items = settingsStorage.state.typeSynonyms
            .map { SynonymPair(it.key, it.value) }
            .toMutableList()
        tableModel.fireTableDataChanged()
        panel.reset()
    }

    private fun getSynonymsMap(): Map<String, String> {
        return tableModel.items
            .filter { it.key.isNotBlank() && it.value.isNotBlank() }
            .associate { it.key to it.value }
    }

    override fun disposeUIResources() {
        super.disposeUIResources()
        PatternEditorBuilder.dispose(patternEditor)
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

    private fun createSynonymTablePanel(): JComponent {
        val tablePanel = ToolbarDecorator.createDecorator(table)
            .setAddAction { addSynonym() }
            .setRemoveAction { removeSelectedSynonym() }
            .disableUpAction()
            .disableDownAction()
            .createPanel()

        return tablePanel
    }

    private fun addSynonym() {
        tableModel.addRow(SynonymPair("", ""))
    }

    private fun removeSelectedSynonym() {
        val selectedRow = table.selectedRow
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow)
        }
    }
}

private fun Row.withoutGaps() = apply {
    topGap(TopGap.NONE)
    bottomGap(BottomGap.NONE)
}
