package com.kozhun.commitmessagetemplate.language.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.kozhun.commitmessagetemplate.language.CMTLanguage
import com.kozhun.commitmessagetemplate.language.lexer.CMTLexerAdapter
import com.kozhun.commitmessagetemplate.language.psi.CMTFile
import com.kozhun.commitmessagetemplate.language.psi.CMTTypes

class CMTParserDefinition : ParserDefinition {

    override fun createLexer(project: Project): Lexer {
        return CMTLexerAdapter()
    }

    override fun createParser(project: Project): PsiParser {
        return CMTParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createElement(node: ASTNode): PsiElement {
        return CMTTypes.Factory.createElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return CMTFile(viewProvider)
    }

    companion object {
        val FILE = IFileElementType(CMTLanguage.INSTANCE)
    }
}
