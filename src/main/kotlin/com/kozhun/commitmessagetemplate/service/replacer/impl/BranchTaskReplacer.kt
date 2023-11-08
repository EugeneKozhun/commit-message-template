package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

@Service(Service.Level.PROJECT)
class BranchTaskReplacer(
    private val project: Project
) : Replacer {
    override fun replace(message: String): String {
        return message.replace(TASK_ANCHOR, getTaskId())
    }

    private fun getTaskId(): String {
        val gitRepositoryManager = GitRepositoryManager.getInstance(project)
        return getCurrentRepository(gitRepositoryManager)?.currentBranch?.name ?: ""
    }

    private fun getCurrentRepository(manager: GitRepositoryManager): GitRepository? {
        return manager.repositories.firstOrNull()
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): BranchTaskReplacer = project.service()

        private const val TASK_ANCHOR = "\$TASK"
    }
}
