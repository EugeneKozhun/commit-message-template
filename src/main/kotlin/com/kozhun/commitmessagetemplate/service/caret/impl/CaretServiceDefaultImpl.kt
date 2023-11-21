package com.kozhun.commitmessagetemplate.service.caret.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.caret.CaretService

@Service(Service.Level.PROJECT)
class CaretServiceDefaultImpl : CaretService {

    override fun getCaretOffsetByAnchor(message: String): Pair<String, Int> {
        return when (val caretOffset = message.indexOf(CARET_ANCHOR)) {
            -1 -> message to message.length
            else -> message.replace(CARET_ANCHOR, "") to caretOffset
        }
    }

    companion object {
        private const val CARET_ANCHOR = "\$CARET"

        @JvmStatic
        fun getInstance(project: Project): CaretServiceDefaultImpl = project.service()
    }
}
