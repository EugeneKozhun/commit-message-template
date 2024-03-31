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
     * Represents a custom regular expression used for matching Project Name from the file path.
     */
    var projectNameRegex by string()

    /**
     * Represents a project/subproject name separator.
     */
    var projectNameSeparator by string()

    /**
     * Represents a string case postprocessor for a project name.
     */
    var projectNamePostprocessor by string(StringCase.NONE.label)

    fun isDefaultTaskFields(): Boolean {
        return taskIdRegex.isNullOrBlank()
    }

    fun isDefaultTypeFields(): Boolean {
        return typeRegex.isNullOrBlank() && typePostprocessor == StringCase.NONE.label
    }

    fun isDefaultProjectNameFields(): Boolean {
        return projectNameRegex.isNullOrBlank() &&
                (projectNameSeparator.isNullOrBlank() || projectNameSeparator == DEFAULT_SCOPE_SEPARATOR) &&
                (projectNamePostprocessor == StringCase.NONE.label)
    }
}
