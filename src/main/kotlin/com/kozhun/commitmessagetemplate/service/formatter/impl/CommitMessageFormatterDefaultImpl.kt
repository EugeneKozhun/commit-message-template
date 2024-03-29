package com.kozhun.commitmessagetemplate.service.formatter.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.formatter.CommitMessageFormatter
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTypeReplacer
import com.kozhun.commitmessagetemplate.service.replacer.impl.ProjectNameReplacer
import com.kozhun.commitmessagetemplate.util.storage

/**
 * This class is responsible for formatting commit messages based on a pattern, using a list of Replacers.
 *
 * @param project The project associated with the formatter.
 */
@Service(Service.Level.PROJECT)
class CommitMessageFormatterDefaultImpl(
    private val project: Project
) : CommitMessageFormatter {

    private val replacers = listOf(
        BranchTypeReplacer.getInstance(project),
        BranchTaskIdReplacer.getInstance(project),
        ProjectNameReplacer.getInstance(project)
    )

    override fun getFormattedCommitMessage(): String {
        val pattern = project.storage().state.pattern ?: return ""
        return replacers.fold(pattern) { result, replacer -> replacer.replace(result) }
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): CommitMessageFormatter = project.service<CommitMessageFormatterDefaultImpl>()
    }
}
