package com.kozhun.commitmessagetemplate.settings.storage

import com.intellij.openapi.components.BaseState

/**
 * Represents the state of the settings in the application.
 */
class SettingsState : BaseState() {
    /**
     * Represents a message pattern used in template.
     */
    var pattern by string("")

    /**
     * Represents a custom regular expression used for matching task ID from the current branch.
     */
    var taskIdRegex by string("")

    /**
     * Represents a custom regular expression used for matching a current branch type.
     */
    var typeRegex by string("")

    /**
     * Represents a string case postprocessor for a branch type.
     */
    var typePostprocessor by string("")

    /**
     * Represents a custom regular expression used for matching Project Name from the file path.
     */
    var projectNameRegex by string("")

    /**
     *
     */
    var projectNameSeparator by string("")

    /**
     * Represents a string case postprocessor for a project name.
     */
    var projectNamePostprocessor by string("")
}
