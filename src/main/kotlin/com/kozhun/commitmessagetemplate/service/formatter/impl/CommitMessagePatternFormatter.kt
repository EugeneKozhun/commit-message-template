package com.kozhun.commitmessagetemplate.service.formatter.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.formatter.CommitMessageFormatter
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskReplacer
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage

@Service(Service.Level.PROJECT)
class CommitMessagePatternFormatter(
    private val project: Project
) : CommitMessageFormatter {

    override fun getCommitMessageTemplate(): String {
        val pattern = SettingsStorage.getInstance(project).state.pattern ?: return ""
        val branchTaskReplacer = BranchTaskReplacer.getInstance(project)
        return branchTaskReplacer.replace(pattern)
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): CommitMessagePatternFormatter = project.service()
    }
}
