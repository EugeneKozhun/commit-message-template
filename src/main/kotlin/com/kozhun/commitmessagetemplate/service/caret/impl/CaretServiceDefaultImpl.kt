package com.kozhun.commitmessagetemplate.service.caret.impl

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.kozhun.commitmessagetemplate.service.caret.CaretService

/**
 * Default implementation of the CaretService interface.
 *
 * This class provides functionality to obtain the caret offset by anchor in a given message.
 */
@Service(Service.Level.PROJECT)
class CaretServiceDefaultImpl : CaretService {

    override fun getCaretOffsetByAnchor(message: String): Pair<String, Int> {
        return when (val caretOffset = message.indexOf(CARET_POSITION_ANCHOR)) {
            NOT_FOUND_INDEX -> message to message.length
            else -> message.replace(CARET_POSITION_ANCHOR, "") to caretOffset
        }
    }

    companion object {
        private const val NOT_FOUND_INDEX = -1
        const val CARET_POSITION_ANCHOR = "\$CARET_POSITION"

        @JvmStatic
        fun getInstance(project: Project): CaretServiceDefaultImpl = project.service()
    }
}
