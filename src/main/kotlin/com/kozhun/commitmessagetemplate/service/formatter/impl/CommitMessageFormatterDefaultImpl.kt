package com.kozhun.commitmessagetemplate.service.formatter.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.formatter.CommitMessageFormatter
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTypeReplacer
import com.kozhun.commitmessagetemplate.service.replacer.impl.ScopeReplacer
import com.kozhun.commitmessagetemplate.service.whitespace.impl.WhitespaceServiceDefaultImpl
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
        ScopeReplacer.getInstance(project)
    )

    private val whitespaceService = WhitespaceServiceDefaultImpl.getInstance(project)

    override fun getFormattedCommitMessage(): String {
        val pattern = project.storage().state.pattern.orEmpty()
        return replacers.fold(pattern) { result, replacer -> replacer.replace(result) }
            .let { whitespaceService.format(it) }
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): CommitMessageFormatter = project.service<CommitMessageFormatterDefaultImpl>()
    }
}
