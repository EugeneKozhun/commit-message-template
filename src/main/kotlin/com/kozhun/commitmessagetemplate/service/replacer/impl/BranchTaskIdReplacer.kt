package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import com.kozhun.commitmessagetemplate.util.toNotBlankRegex

/**
 * Replaces a predefined anchor in a given message with the task ID of the current Git branch.
 *
 * @param project The IntelliJ IDEA project.
 */
@Service(Service.Level.PROJECT)
class BranchTaskIdReplacer(
    project: Project
) : BranchReplacer(project) {

    /**
     * Replaces the occurrence of TASK_ID_ANCHOR in the given message with the task ID from the current branch.
     *
     * @param message the original message that may contain TASK_ID_ANCHOR.
     * @return the message with TASK_ID_ANCHOR replaced by the task ID from the current branch.
     */
    override fun replace(message: String): String {
        return message.replace(TASK_ID_ANCHOR, getTaskIdFromCurrentBranch())
    }

    private fun getTaskIdFromCurrentBranch(): String {
        return getCurrentBranchName()
            ?.let { getTaskIdRegex().find(it)?.value }
            .orEmpty()
    }

    private fun getTaskIdRegex(): Regex {
        val settingsStorage = SettingsStorage.getInstance(project)
        return settingsStorage.state.taskIdRegex?.toNotBlankRegex() ?: DEFAULT_TASK_ID_REGEX
    }

    companion object {
        const val TASK_ID_ANCHOR = "\$TASK_ID"
        val DEFAULT_TASK_ID_REGEX = "[a-zA-Z0-9]+-\\d+".toRegex()

        @JvmStatic
        fun getInstance(project: Project): BranchTaskIdReplacer = project.service()
    }
}
