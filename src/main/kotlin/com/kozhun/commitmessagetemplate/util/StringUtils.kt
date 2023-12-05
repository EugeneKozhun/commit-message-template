package com.kozhun.commitmessagetemplate.util

fun String.toNotBlankRegex() = this.takeIf { it.isNotBlank() }?.toRegex()
