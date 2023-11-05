package com.kozhun.commitmessagetemplate.service

interface MessageTemplateFormatter {
    fun getCommitMessageTemplate(): String
}
