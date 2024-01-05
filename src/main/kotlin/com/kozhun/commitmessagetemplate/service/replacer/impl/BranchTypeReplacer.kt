package com.kozhun.commitmessagetemplate.service.replacer.impl

import ai.grazie.utils.capitalize
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.git.branch.impl.GitBranchServiceImpl
import com.kozhun.commitmessagetemplate.service.replacer.Replacer
import com.kozhun.commitmessagetemplate.settings.enums.BranchTypePostprocessor
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
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
        return message.replace(TYPE_ANCHOR, withSelectedCase(foundType))
    }

    private fun getTypeFromCurrentBranch(): String {
        return GitBranchServiceImpl.getInstance(project).getCurrentBranch().name
            .let { getTypeRegex().find(it)?.value }
            .orEmpty()
    }

    private fun getTypeRegex(): Regex {
        val settingsStorage = SettingsStorage.getInstance(project)
        return settingsStorage.state.typeRegex?.toNotBlankRegex() ?: DEFAULT_TYPE_REGEX
    }

    private fun withSelectedCase(value: String): String {
        val settingsStorage = SettingsStorage.getInstance(project)
        val branchTypePostprocessor = settingsStorage.state.typePostprocessor
            ?.let { BranchTypePostprocessor.labelValueOf(it) }
            ?: BranchTypePostprocessor.NONE

        return when (branchTypePostprocessor) {
            BranchTypePostprocessor.CAPITALIZE -> value.capitalize()
            BranchTypePostprocessor.UPPERCASE -> value.uppercase()
            BranchTypePostprocessor.LOWERCASE -> value.lowercase()
            else -> value
        }
    }

    companion object {
        const val TYPE_ANCHOR = "\$TYPE"
        val DEFAULT_TYPE_REGEX = "bugfix|feature|hotfix|enhancement|refactoring".toRegex(RegexOption.IGNORE_CASE)

        @JvmStatic
        fun getInstance(project: Project): Replacer = project.service<BranchTypeReplacer>()
    }
}
