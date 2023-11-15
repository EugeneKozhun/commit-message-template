package com.kozhun.commitmessagetemplate.action

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.kozhun.commitmessagetemplate.service.formatter.impl.CommitMessagePatternFormatter

class GenerateCommitMessageAction : DumbAwareAction() {
    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val formatter = CommitMessagePatternFormatter.getInstance(anActionEvent.project!!)
        getCommitMessageInput(anActionEvent)
            ?.setCommitMessage(formatter.getCommitMessageTemplate())
    }

    private fun getCommitMessageInput(anActionEvent: AnActionEvent): CommitMessageI? {
        return anActionEvent.getData(VcsDataKeys.COMMIT_MESSAGE_CONTROL)
    }
}
