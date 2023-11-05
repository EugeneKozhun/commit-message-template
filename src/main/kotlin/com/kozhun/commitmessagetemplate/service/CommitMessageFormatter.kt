package com.kozhun.commitmessagetemplate.service

interface CommitMessageFormatter {
    fun getCommitMessageTemplate(): String
}
