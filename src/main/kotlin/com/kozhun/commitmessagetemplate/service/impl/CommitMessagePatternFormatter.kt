package com.kozhun.commitmessagetemplate.service.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.CommitMessageFormatter
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import git4idea.repo.GitRepositoryManager

@Service(Service.Level.PROJECT)
class CommitMessagePatternFormatter(
    private val project: Project
) : CommitMessageFormatter {

    // TODO: refactor it. Open-close!!!
    override fun getCommitMessageTemplate(): String {
        val pattern = settings().pattern
        return pattern
            ?.replace("\$TASK", getTaskValue())
            ?: ""
    }

    private fun settings() = project.service<SettingsStorage>().state

    private fun getTaskValue(): String {
        val manager = GitRepositoryManager.getInstance(project)
        // TODO: optimize get current branch.
        return manager.repositories.first()?.currentBranch?.name ?: ""
    }
}
