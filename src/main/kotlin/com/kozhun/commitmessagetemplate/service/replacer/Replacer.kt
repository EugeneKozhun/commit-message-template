package com.kozhun.commitmessagetemplate.service.replacer

fun interface Replacer {

    fun replace(message: String): String
}
