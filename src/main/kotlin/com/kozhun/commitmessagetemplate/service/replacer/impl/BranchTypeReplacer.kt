package com.kozhun.commitmessagetemplate.service.replacer.impl

import ai.grazie.utils.capitalize
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.settings.enums.BranchTypePostprocessor
import com.kozhun.commitmessagetemplate.settings.storage.SettingsStorage
import com.kozhun.commitmessagetemplate.util.toNotBlankRegex

@Service(Service.Level.PROJECT)
class BranchTypeReplacer(
    project: Project
) : BranchReplacer(project) {
    override fun replace(message: String): String {
        val foundType = getTypeFromCurrentBranch()
        return message.replace(TYPE_ANCHOR, withSelectedCase(foundType))
    }

    private fun getTypeFromCurrentBranch(): String {
        return getCurrentBranchName()
            ?.let { getTypeRegex().find(it)?.value }
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
        fun getInstance(project: Project): BranchTypeReplacer = project.service()
    }
}
