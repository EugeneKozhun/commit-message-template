package com.kozhun.commitmessagetemplate.storage

import com.intellij.openapi.components.BaseState
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_SCOPE_SEPARATOR
import com.kozhun.commitmessagetemplate.enums.StringCase

/**
 * Represents the state of the settings in the application.
 */
class SettingsState : BaseState() {
    /**
     * Represents a message pattern used in template.
     */
    var pattern by string()

    /**
     * Remove whitespace at the start of string.
     */
    var trimWhitespacesStart by property(false)

    /**
     * Remove whitespace at the end of string.
     */
    var trimWhitespacesEnd by property(false)

    /**
     * Remove duplicated whitespaces.
     */
    var unnecessaryWhitespaces by property(false)

    /**
     * Represents a custom regular expression used for matching task ID from the current branch.
     */
    var taskIdRegex by string()

    /**
     * Represents a default value for task-id.
     */
    var taskIdDefault by string()

    /**
     * Represents a string case postprocessor for a task id.
     */
    var taskIdPostProcessor by string(StringCase.NONE.label)

    /**
     * Represents a custom regular expression used for matching a current branch type.
     */
    var typeRegex by string()

    /**
     * Represents a default value for branch type.
     */
    var typeDefault by string()

    /**
     * Represents a branch type synonyms.
     */
    var typeSynonyms by map<String, String>()

    /**
     * Represents a string case postprocessor for a branch type.
     */
    var typePostprocessor by string(StringCase.NONE.label)

    /**
     * Represents a custom regular expression used for matching Scope from the file path.
     */
    var scopeRegex by string()

    /**
     * Default scope value.
     */
    var scopeDefault by string()

    /**
     * Represents a scopes separator.
     */
    var scopeSeparator by string()

    /**
     * Represents a string case postprocessor for a scope.
     */
    var scopePostprocessor by string(StringCase.NONE.label)

    fun isDefaultTaskFields(): Boolean {
        return taskIdRegex.isNullOrEmpty() &&
                taskIdDefault.isNullOrEmpty() &&
                taskIdPostProcessor == StringCase.NONE.label
    }

    fun isDefaultTypeFields(): Boolean {
        return typeRegex.isNullOrEmpty() &&
                typeDefault.isNullOrEmpty() &&
                typePostprocessor == StringCase.NONE.label
    }

    fun isDefaultScopeFields(): Boolean {
        return scopeRegex.isNullOrEmpty() &&
                scopeDefault.isNullOrEmpty() &&
                (scopeSeparator.isNullOrEmpty() || scopeSeparator == DEFAULT_SCOPE_SEPARATOR) &&
                (scopePostprocessor == StringCase.NONE.label)
    }
}
