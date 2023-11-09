package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

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
            ?.let { DEFAULT_TASK_ID_REGEXP.find(it)?.value }
            ?: ""
    }

    private fun getCurrentRepository(manager: GitRepositoryManager): GitRepository? {
        return manager.repositories.firstOrNull()
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): BranchTaskIdReplacer = project.service()

        private const val TASK_ID_ANCHOR = "\$TASK"
        private val DEFAULT_TASK_ID_REGEXP = "[a-zA-Z0-9]+-\\d+".toRegex()
    }
}
