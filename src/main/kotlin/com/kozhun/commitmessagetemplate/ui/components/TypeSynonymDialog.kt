package com.kozhun.commitmessagetemplate.ui.components

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel
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

class TypeSynonymDialog : DialogWrapper(true) {
    private val synonymPairs = mutableListOf<SynonymPair>()
    private val tableModel = ListTableModel(
        arrayOf(
            SynonymColumnInfo("Value", { it.key }, { item, value -> item.key = value }),
            SynonymColumnInfo("Synonym", { it.value }, { item, value -> item.value = value })
        ),
        synonymPairs
    )
    private val table = TableView(tableModel)

    init {
        title = "Synonym Configuration"
        init()
    }

    override fun createCenterPanel(): JComponent {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)

        val tablePanel = ToolbarDecorator.createDecorator(table)
            .setAddAction { addSynonym() }
            .setRemoveAction { removeSelectedSynonym() }
            .disableUpAction()
            .disableDownAction()
            .createPanel()

        return JBScrollPane(tablePanel)
    }

    private fun addSynonym() {
            synonymPairs.add(SynonymPair("", ""))
            tableModel.fireTableDataChanged()
    }

    private fun removeSelectedSynonym() {
        val selectedRow = table.selectedObject
        if (selectedRow != null) {
            synonymPairs.remove(selectedRow)
            tableModel.fireTableDataChanged()
        }
    }

    fun getSynonyms(): Map<String, String> {
        return synonymPairs.filter { it.key.isNotBlank() && it.value.isNotBlank() }
            .associate { it.key to it.value }
    }
}
