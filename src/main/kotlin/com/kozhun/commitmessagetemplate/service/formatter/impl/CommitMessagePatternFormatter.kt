package com.kozhun.commitmessagetemplate.service.formatter.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.formatter.CommitMessageFormatter
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import com.kozhun.commitmessagetemplate.service.replacer.impl.BranchTaskIdReplacer
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage

@Service(Service.Level.PROJECT)
class CommitMessagePatternFormatter(
    private val project: Project
) : CommitMessageFormatter {
    private val replacers: List<Replacer>

    init {
        replacers = listOf(
            BranchTaskIdReplacer.getInstance(project)
        )
    }

    override fun getFormattedCommitMessage(): String {
        val pattern = SettingsStorage.getInstance(project).state.pattern ?: return ""
        return replacers.fold(pattern) { result, replacer -> replacer.replace(result) }
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): CommitMessagePatternFormatter = project.service()
    }
}
