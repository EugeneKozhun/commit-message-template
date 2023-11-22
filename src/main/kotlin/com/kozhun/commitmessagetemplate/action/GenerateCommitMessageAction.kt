package com.kozhun.commitmessagetemplate.action

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.ui.CommitMessage
import com.kozhun.commitmessagetemplate.service.caret.impl.CaretServiceDefaultImpl
import com.kozhun.commitmessagetemplate.service.formatter.impl.CommitMessagePatternFormatter

/**
 * A class representing an action to generate a commit message.
 */
class GenerateCommitMessageAction : DumbAwareAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        getCommitMessageInput(anActionEvent)
            ?.apply {
                val formatter = CommitMessagePatternFormatter.getInstance(anActionEvent.project!!)
                val caretService = CaretServiceDefaultImpl.getInstance(anActionEvent.project!!)
                val (message, caretOffset) = formatter.getFormattedCommitMessage()
                    .let(caretService::getCaretOffsetByAnchor)
                setCommitMessageWithCaretOffset(message, caretOffset)
            }
    }

    private fun getCommitMessageInput(anActionEvent: AnActionEvent): CommitMessage? {
        return anActionEvent.getData(VcsDataKeys.COMMIT_MESSAGE_CONTROL) as CommitMessage?
    }

    private fun CommitMessage.setCommitMessageWithCaretOffset(message: String, offset: Int) {
        setCommitMessage(message)
        editorField.removeSelection()
        editorField.requestFocus()
        editorField.caretModel.moveToOffset(offset)
    }
}
