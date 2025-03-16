package com.kozhun.commitmessagetemplate.service.settings

import com.kozhun.commitmessagetemplate.enums.StringCase
import com.kozhun.commitmessagetemplate.storage.SettingsState
import kotlinx.serialization.Serializable

@Serializable
data class ExportableSettings(
    val pattern: String = "",
    val trimWhitespacesStart: Boolean = false,
    val trimWhitespacesEnd: Boolean = false,
    val unnecessaryWhitespaces: Boolean = false,

    val taskIdRegex: String? = null,
    val taskIdDefault: String? = null,
    val taskIdPostProcessor: String? = StringCase.NONE.label,

    val typeRegex: String? = null,
    val typeDefault: String? = null,
    val typeSynonyms: Map<String, String> = emptyMap(),
    val typePostprocessor: String? = StringCase.NONE.label,

    val scopeRegex: String? = null,
    val scopeDefault: String? = null,
    val scopeSeparator: String? = null,
    val scopePostprocessor: String? = StringCase.NONE.label
)

fun ExportableSettings.toSettingsState(): SettingsState {
    return SettingsState().also {
        it.pattern = pattern
        it.trimWhitespacesStart = trimWhitespacesStart
        it.trimWhitespacesEnd = trimWhitespacesEnd
        it.unnecessaryWhitespaces = unnecessaryWhitespaces

        it.taskIdRegex = taskIdRegex
        it.taskIdDefault = taskIdDefault
        it.taskIdPostProcessor = taskIdPostProcessor

        it.typeRegex = typeRegex
        it.typeDefault = typeDefault
        it.typePostprocessor = typePostprocessor
        it.typeSynonyms = typeSynonyms.toMutableMap()

        it.scopeRegex = scopeRegex
        it.scopeDefault = scopeDefault
        it.scopeSeparator = scopeSeparator
        it.scopePostprocessor = scopePostprocessor
    }
}
