package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_TASK_ID_REGEX
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import com.kozhun.commitmessagetemplate.util.branches
import com.kozhun.commitmessagetemplate.util.storage
import com.kozhun.commitmessagetemplate.util.toNotBlankRegex

/**
 * Replaces a predefined anchor in a given message with the task ID of the current Git branch.
 *
 * @param project The IntelliJ IDEA project.
 */
@Service(Service.Level.PROJECT)
class BranchTaskIdReplacer(
    private val project: Project
) : Replacer {

    /**
     * Replaces the occurrence of TASK_ID_ANCHOR in the given message with the task ID from the current branch.
     *
     * @param message the original message that may contain TASK_ID_ANCHOR.
     * @return the message with TASK_ID_ANCHOR replaced by the task ID from the current branch.
     */
    override fun replace(message: String): String {
        return message.replace(ANCHOR, getTaskIdFromCurrentBranch())
    }

    private fun getTaskIdFromCurrentBranch(): String {
        return project.branches().getCurrentBranch().name
            .let { getTaskIdRegex().find(it)?.value }
            .orEmpty()
    }

    private fun getTaskIdRegex(): Regex {
        return project.storage().state.taskIdRegex?.toNotBlankRegex() ?: DEFAULT_TASK_ID_REGEX
    }

    companion object {
        const val ANCHOR = "\$TASK_ID"

        @JvmStatic
        fun getInstance(project: Project): Replacer = project.service<BranchTaskIdReplacer>()
    }
}
