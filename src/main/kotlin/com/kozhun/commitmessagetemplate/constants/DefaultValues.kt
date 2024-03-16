package com.kozhun.commitmessagetemplate.constants

object DefaultValues {
    const val DEFAULT_PROJECT_NAME_SEPARATOR = "|"
    val DEFAULT_TASK_ID_REGEX = "[a-zA-Z0-9]+-\\d+".toRegex()
    val DEFAULT_TYPE_REGEX = "fix|bugfix|hotfix|feat|feature".toRegex(RegexOption.IGNORE_CASE)
}
