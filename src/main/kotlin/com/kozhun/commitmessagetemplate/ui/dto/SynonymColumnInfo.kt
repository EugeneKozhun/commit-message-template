package com.kozhun.commitmessagetemplate.ui.dto

import com.intellij.util.ui.ColumnInfo

data class SynonymPair(var key: String, var value: String)

class SynonymColumnInfo(
    name: String,
    private val getter: (SynonymPair) -> String
) : ColumnInfo<SynonymPair, String>(name) {
    override fun valueOf(item: SynonymPair): String = getter(item)
    override fun isCellEditable(item: SynonymPair): Boolean = false
}
