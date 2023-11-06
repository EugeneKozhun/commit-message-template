package com.kozhun.commitmessagetemplate.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.ui.Refreshable
import com.kozhun.commitmessagetemplate.service.formatter.impl.CommitMessagePatternFormatter

class GenerateCommitMessageAction : AnAction() {
    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val formatter = anActionEvent.project
            ?.let { CommitMessagePatternFormatter.getInstance(it) }
            ?: return
        getCommitMessageInput(anActionEvent)
            ?.setCommitMessage(formatter.getCommitMessageTemplate())
    }

    private fun getCommitMessageInput(anActionEvent: AnActionEvent): CommitMessageI? {
        return Refreshable.PANEL_KEY.getData(anActionEvent.dataContext)
            ?.takeIf { it is CommitMessageI }
            ?.let { it as CommitMessageI }
            ?: VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(anActionEvent.dataContext)
    }
}
