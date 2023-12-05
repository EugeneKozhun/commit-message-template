package com.kozhun.commitmessagetemplate.util

fun String.toNotBlankRegex() = this.takeIf { it.isNotBlank() }?.toRegex()

fun String.toNotBlankRegex(option: RegexOption) = this.takeIf { it.isNotBlank() }?.toRegex(option)
