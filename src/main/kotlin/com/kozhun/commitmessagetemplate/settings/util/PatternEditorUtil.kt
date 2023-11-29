package com.kozhun.commitmessagetemplate.settings.util

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.highlighter.EditorHighlighterFactory
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightVirtualFile
import com.kozhun.commitmessagetemplate.language.CMTLanguage


object PatternEditorUtil {
    private const val DEFAULT_DOCUMENT_TEXT = ""
    private const val VIRTUAL_FILE_NAME = "settings-pattern.cmt"

    /**
     * Creates an instance of Editor for a pattern.
     *
     * @param project the project in which the editor will be created
     * @return the created editor
     */
    fun createEditor(project: Project): Editor {
        val document = createDocument()
        val editorFactory = EditorFactory.getInstance()
        val editor = editorFactory.createEditor(document, project)
        val file = LightVirtualFile(VIRTUAL_FILE_NAME, CMTLanguage.INSTANCE, DEFAULT_DOCUMENT_TEXT)

        editor.settings.apply {
            additionalLinesCount = 0
            isLineNumbersShown = false
            isVirtualSpace = false
            isLineMarkerAreaShown = false
            isIndentGuidesShown = false
            isFoldingOutlineShown = false
            isCaretRowShown = false
        }
        editor.setupHighlighter(file)

        return editor
    }

    /**
     * Releases the given pattern editor.
     *
     * @param patternEditor the pattern editor to be released
     */
    fun dispose(patternEditor: Editor) {
        EditorFactory.getInstance().releaseEditor(patternEditor)
    }

    private fun createDocument(): Document {
        return EditorFactory.getInstance().createDocument(DEFAULT_DOCUMENT_TEXT)
    }
}

private fun Editor.setupHighlighter(file: VirtualFile) {
    val syntaxHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(file.fileType, project, file)
    val editorHighlighter = EditorHighlighterFactory.getInstance().createEditorHighlighter(syntaxHighlighter, this.colorsScheme)
    (this as EditorEx).highlighter = editorHighlighter
}
