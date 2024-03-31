package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.kozhun.commitmessagetemplate.constants.DefaultValues.DEFAULT_SCOPE_SEPARATOR
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import com.kozhun.commitmessagetemplate.settings.enums.StringCase
import com.kozhun.commitmessagetemplate.util.storage
import com.kozhun.commitmessagetemplate.util.toCase
import com.kozhun.commitmessagetemplate.util.toNotBlankRegex

@Service(Service.Level.PROJECT)
class ProjectNameReplacer(
    private val project: Project
) : Replacer {

    override fun replace(message: String): String {
        val projectName = extractProjectName()
        return message.replace(ANCHOR, projectName)
    }

    private fun extractProjectName(): String {
        return ChangeListManager.getInstance(project)
            .affectedPaths
            .asSequence()
            .mapNotNull { it.path }
            .mapNotNull { getRegex().find(it) }
            .map { it.value }
            .filter { it.isNotEmpty() }
            .distinct()
            .joinToString(getSeparator())
            .let { changeCase(it) }
    }

    private fun changeCase(value: String): String {
        return project.storage().state.projectNamePostprocessor
            ?.let { StringCase.labelValueOf(it) }
            ?.let { value.toCase(it) }
            ?: value
    }

    private fun getSeparator(): String {
        return project.storage().state.projectNameSeparator
            ?.takeIf { it.isNotBlank() }
            ?: DEFAULT_SCOPE_SEPARATOR
    }

    private fun getRegex(): Regex {
        return project.storage().state.projectNameRegex?.toNotBlankRegex() ?: project.name.toRegex(RegexOption.IGNORE_CASE)
    }

    companion object {
        private const val ANCHOR = "\$SCOPE"

        @JvmStatic
        fun getInstance(project: Project): Replacer = project.service<ProjectNameReplacer>()
    }
}
