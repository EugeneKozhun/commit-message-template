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
     * Represents a Postprocessor for branch type.
     */
    var typePostprocessor by string("")
}
