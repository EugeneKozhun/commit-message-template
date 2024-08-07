package com.kozhun.commitmessagetemplate.language.highlighter

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.kozhun.commitmessagetemplate.language.lexer.CMTLexerAdapter
import com.kozhun.commitmessagetemplate.language.psi.CMTTypes

/**
 * CMT Syntax Highlighter for highlighting CMT code.
 */
class CMTSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return CMTLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            CMTTypes.TASK_ID, CMTTypes.TYPE, CMTTypes.SCOPE -> keywordKeys
            CMTTypes.CARET_POSITION -> caretPositionKeys
            else -> arrayOf()
        }
    }

    companion object {
        private val keywordKeys = arrayOf(
            createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.CONSTANT),
        )
        private val caretPositionKeys = arrayOf(
            createTextAttributesKey("CARET_POSITION", DefaultLanguageHighlighterColors.KEYWORD)
        )
    }
}
