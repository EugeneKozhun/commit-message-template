package com.kozhun.commitmessagetemplate.util

/**
 * Returns a regular expression based on the current string if it is not blank.
 *
 * @return the regular expression if the string is not blank, otherwise null.
 */
fun String.toNotBlankRegex() = this.takeIf { it.isNotBlank() }?.toRegex()

/**
 * Returns a regular expression if the input string is not blank, based on the provided regex option.
 *
 * @param option The desired regex option to be used when constructing the regular expression.
 * @return The regular expression if the input string is not blank, otherwise null.
 */
fun String.toNotBlankRegex(option: RegexOption) = this.takeIf { it.isNotBlank() }?.toRegex(option)
