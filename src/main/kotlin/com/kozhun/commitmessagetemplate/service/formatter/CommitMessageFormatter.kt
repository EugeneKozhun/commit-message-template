package com.kozhun.commitmessagetemplate.service.formatter

/**
 * A functional interface representing a commit message formatter.
 */
fun interface CommitMessageFormatter {

    /**
     * Retrieves the formatted commit message.
     *
     * @return The formatted commit message as a String.
     */
    fun getFormattedCommitMessage(): String
}
