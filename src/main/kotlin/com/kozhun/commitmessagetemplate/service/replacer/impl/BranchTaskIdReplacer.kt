package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

/**
 * Replaces a predefined anchor in a given message with the task ID of the current Git branch.
 *
 * @param project The IntelliJ IDEA project.
 */
@Service(Service.Level.PROJECT)
class BranchTaskIdReplacer(
    private val project: Project
) : Replacer {
    override fun replace(message: String): String {
        return message.replace(TASK_ID_ANCHOR, getTaskIdFromCurrentBranch())
    }

    private fun getTaskIdFromCurrentBranch(): String {
        val gitRepositoryManager = GitRepositoryManager.getInstance(project)
        return getCurrentRepository(gitRepositoryManager)?.currentBranch?.name
            ?.let { getTaskIdRegex().find(it)?.value }
            ?: ""
    }

    private fun getCurrentRepository(manager: GitRepositoryManager): GitRepository? {
        return manager.repositories.firstOrNull()
    }

    private fun getTaskIdRegex(): Regex {
        val settingsStorage = SettingsStorage.getInstance(project)
        return settingsStorage.state.taskIdRegex?.toRegex() ?: DEFAULT_TASK_ID_REGEX
    }

    companion object {
        val DEFAULT_TASK_ID_REGEX = "[a-zA-Z0-9]+-\\d+".toRegex()

        private const val TASK_ID_ANCHOR = "\$TASK-ID"

        @JvmStatic
        fun getInstance(project: Project): BranchTaskIdReplacer = project.service()
    }
}
