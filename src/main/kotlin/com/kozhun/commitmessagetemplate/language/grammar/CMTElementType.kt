package com.kozhun.commitmessagetemplate.language.grammar

import com.intellij.psi.tree.IElementType
import com.kozhun.commitmessagetemplate.language.CMTLanguage

class CMTElementType(debugName: String) : IElementType(debugName, CMTLanguage.INSTANCE)
