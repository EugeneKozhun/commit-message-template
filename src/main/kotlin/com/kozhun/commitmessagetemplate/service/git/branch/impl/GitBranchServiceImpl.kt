package com.kozhun.commitmessagetemplate.service.git.branch.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.git.branch.GitBranchService
import git4idea.GitBranch
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

@Service(Service.Level.PROJECT)
class GitBranchServiceImpl(
    private val project: Project
) : GitBranchService {

    override fun getCurrentBranch(): GitBranch {
        val gitRepositoryManager = GitRepositoryManager.getInstance(project)
        return gitRepositoryManager.repositories.firstOrNull()?.currentBranch
            ?: error("Current git branch not found.")
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): GitBranchService = project.service<GitBranchServiceImpl>()
    }
}
