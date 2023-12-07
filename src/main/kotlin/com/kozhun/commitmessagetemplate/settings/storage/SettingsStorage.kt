package com.kozhun.commitmessagetemplate.settings.storage

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.SimplePersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

/**
 * A class that provides storage and retrieval functionality for project settings.
 */
@Service(Service.Level.PROJECT)
@State(name = "commit-message-template")
class SettingsStorage : SimplePersistentStateComponent<SettingsState>(getDefaultState()) {

    /**
     * Sets the pattern used in the commit message template.
     *
     * @param pattern the pattern to set
     */
    fun setPattern(pattern: String) {
        state.pattern = pattern
        loadState(state)
    }

    /**
     * Sets the custom regular expression used for matching task ID from the current branch.
     *
     * @param regExp the regular expression to set
     */
    fun setTaskIdRegExp(regExp: String) {
        state.taskIdRegex = regExp
        loadState(state)
    }

    /**
     * Sets the custom regular expression used for matching a current branch type.
     *
     * @param regExp the regular expression to set
     */
    fun setTypeRegExp(regExp: String) {
        state.typeRegex = regExp
        loadState(state)
    }

    /**
     * Sets the postprocessor for the branch type.
     *
     * @param postprocessor the postprocessor to set
     */
    fun setTypePostprocessor(postprocessor: String) {
        state.typePostprocessor = postprocessor
        loadState(state)
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): SettingsStorage = project.service()
    }
}

private fun getDefaultState() = SettingsState()
