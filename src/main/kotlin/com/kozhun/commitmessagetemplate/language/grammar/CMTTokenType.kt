package com.kozhun.commitmessagetemplate.language.grammar

import com.intellij.psi.tree.IElementType
import com.kozhun.commitmessagetemplate.language.CMTLanguage

class CMTTokenType(debugName: String) : IElementType(debugName, CMTLanguage.INSTANCE) {
    override fun toString(): String {
        return "CMTTokenType." + super.toString()
    }
}
