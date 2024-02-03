package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import com.kozhun.commitmessagetemplate.settings.enums.StringCase
import com.kozhun.commitmessagetemplate.util.storage
import com.kozhun.commitmessagetemplate.util.toCase

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
            .flatMap { it.split("/") }
            .mapNotNull { DEFAULT_REGEX.find(it)?.value }
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
            ?: DEFAULT_SEPARATOR
    }

    companion object {
        const val ANCHOR = "\$PROJECT_NAME"
        const val DEFAULT_SEPARATOR = "|"

        // TODO: change it to something relevant
        val DEFAULT_REGEX = "party|aml|soft".toRegex(RegexOption.IGNORE_CASE)

        @JvmStatic
        fun getInstance(project: Project): Replacer = project.service<ProjectNameReplacer>()
    }
}
