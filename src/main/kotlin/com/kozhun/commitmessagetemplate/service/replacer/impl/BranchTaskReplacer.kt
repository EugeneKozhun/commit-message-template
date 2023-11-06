package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import git4idea.repo.GitRepositoryManager

@Service(Service.Level.PROJECT)
class BranchTaskReplacer(
    private val project: Project
) : Replacer {
    override fun replace(value: String): String {
        return value.replace(TASK_ANCHOR, getTaskValue())
    }

    private fun getTaskValue(): String {
        val manager = GitRepositoryManager.getInstance(project)
        // TODO: optimize get current branch.
        return manager.repositories.first()?.currentBranch?.name ?: ""
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): BranchTaskReplacer = project.service()

        private const val TASK_ANCHOR = "\$TASK"
    }
}
