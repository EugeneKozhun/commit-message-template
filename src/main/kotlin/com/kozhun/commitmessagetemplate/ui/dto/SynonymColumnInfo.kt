package com.kozhun.commitmessagetemplate.ui.dto

import com.intellij.util.ui.ColumnInfo

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
