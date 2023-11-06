package com.kozhun.commitmessagetemplate.service.replacer

interface Replacer {

    fun replace(value: String): String
}
