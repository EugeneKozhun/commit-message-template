package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import git4idea.repo.GitRepository
import git4idea.repo.GitRepositoryManager

/**
 * A base class for replacing substrings in a given message based on the current Git branch.
 * @property project The IntelliJ IDEA project.
 */
abstract class BranchReplacer(
    protected val project: Project
) : Replacer {

    protected fun getCurrentBranchName(): String? {
        val gitRepositoryManager = GitRepositoryManager.getInstance(project)
        return getCurrentRepository(gitRepositoryManager)?.currentBranch?.name
    }

    private fun getCurrentRepository(manager: GitRepositoryManager): GitRepository? {
        return manager.repositories.firstOrNull()
    }
}
