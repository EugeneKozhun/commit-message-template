package com.kozhun.commitmessagetemplate.language

import com.intellij.lang.Language

/**
 * The `CMTLanguage` class represents the CMT language.
 */
class CMTLanguage : Language("CMT") {
    companion object {
        val INSTANCE = CMTLanguage()
    }
}
