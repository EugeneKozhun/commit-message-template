package com.kozhun.commitmessagetemplate.settings.storage

import com.intellij.openapi.components.BaseState
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_SCOPE_SEPARATOR
import com.kozhun.commitmessagetemplate.settings.enums.StringCase

/**
 * Represents the state of the settings in the application.
 */
class SettingsState : BaseState() {
    /**
     * Represents a message pattern used in template.
     */
    var pattern by string()

    /**
     * Represents a custom regular expression used for matching task ID from the current branch.
     */
    var taskIdRegex by string()

    /**
     * Represents a custom regular expression used for matching a current branch type.
     */
    var typeRegex by string()

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
     * Represents a scopes separator.
     */
    var scopeSeparator by string()

    /**
     * Represents a string case postprocessor for a scope.
     */
    var scopePostprocessor by string(StringCase.NONE.label)

    fun isDefaultTaskFields(): Boolean {
        return taskIdRegex.isNullOrBlank()
    }

    fun isDefaultTypeFields(): Boolean {
        return typeRegex.isNullOrBlank() && typePostprocessor == StringCase.NONE.label
    }

    fun isDefaultScopeFields(): Boolean {
        return scopeRegex.isNullOrBlank() &&
                (scopeSeparator.isNullOrBlank() || scopeSeparator == DEFAULT_SCOPE_SEPARATOR) &&
                (scopePostprocessor == StringCase.NONE.label)
    }
}
