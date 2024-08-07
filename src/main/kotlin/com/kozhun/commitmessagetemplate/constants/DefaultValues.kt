package com.kozhun.commitmessagetemplate.constants

object DefaultValues {
    const val DEFAULT_SCOPE_SEPARATOR = "|"
    val DEFAULT_TASK_ID_REGEX = "[a-zA-Z0-9]+-\\d+".toRegex()
    val DEFAULT_TYPE_REGEX = "bugfix|hotfix|fix|feature|feat".toRegex(RegexOption.IGNORE_CASE)
}
