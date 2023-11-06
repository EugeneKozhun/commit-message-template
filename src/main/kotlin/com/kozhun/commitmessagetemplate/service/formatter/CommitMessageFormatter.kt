package com.kozhun.commitmessagetemplate.service.formatter

fun interface CommitMessageFormatter {
    fun getCommitMessageTemplate(): String
}
