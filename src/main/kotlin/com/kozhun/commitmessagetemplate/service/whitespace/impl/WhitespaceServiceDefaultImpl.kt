package com.kozhun.commitmessagetemplate.service.whitespace.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.whitespace.WhitespaceService
import com.kozhun.commitmessagetemplate.util.storage

@Service(Service.Level.PROJECT)
class WhitespaceServiceDefaultImpl(
    private val project: Project
) : WhitespaceService {
    override fun format(string: String): String {
        var formattedString = string
        val storageState = project.storage().state

        if (storageState.unnecessaryWhitespaces) {
            formattedString = string.replace("\\s+".toRegex(), " ")
        }

        if (storageState.trimWhitespacesStart) {
            formattedString = string.trimStart()
        }

        if (storageState.trimWhitespacesEnd) {
            formattedString = string.trimEnd()
        }

        return formattedString
    }

    companion object {
        @JvmStatic
        fun getInstance(project: Project): WhitespaceService = project.service<WhitespaceServiceDefaultImpl>()
    }
}
