package com.kozhun.commitmessagetemplate.service.replacer.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import com.kozhun.commitmessagetemplate.settings.enums.StringCase
import com.kozhun.commitmessagetemplate.util.branches
import com.kozhun.commitmessagetemplate.util.storage
import com.kozhun.commitmessagetemplate.util.toCase
import com.kozhun.commitmessagetemplate.util.toNotBlankRegex

/**
 * A class that replaces a specific substring in a given message based on the current branch type and settings.
 *
 * @property project The IntelliJ IDEA project.
 */
@Service(Service.Level.PROJECT)
class BranchTypeReplacer(
    private val project: Project
) : Replacer {
    override fun replace(message: String): String {
        val foundType = getTypeFromCurrentBranch()
        return message.replace(ANCHOR, changeCase(foundType))
    }

    private fun getTypeFromCurrentBranch(): String {
        return project.branches().getCurrentBranch().name
            .let { getTypeRegex().find(it)?.value }
            .orEmpty()
    }

    private fun getTypeRegex(): Regex {
        return project.storage().state.typeRegex?.toNotBlankRegex() ?: DEFAULT_REGEX
    }

    private fun changeCase(value: String): String {
        return project.storage().state.typePostprocessor
            ?.let { StringCase.labelValueOf(it) }
            ?.let { value.toCase(it) }
            ?: value
    }

    companion object {
        const val ANCHOR = "\$TYPE"
        val DEFAULT_REGEX = "bugfix|feature|hotfix|enhancement|refactoring".toRegex(RegexOption.IGNORE_CASE)

        @JvmStatic
        fun getInstance(project: Project): Replacer = project.service<BranchTypeReplacer>()
    }
}
