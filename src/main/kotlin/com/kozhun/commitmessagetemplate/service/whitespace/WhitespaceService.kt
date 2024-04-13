package com.kozhun.commitmessagetemplate.service.whitespace

fun interface WhitespaceService {
    fun format(string: String): String
}
