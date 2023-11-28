package com.kozhun.commitmessagetemplate.settings.util

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.project.Project

object PatternEditorUtil {
    private const val DEFAULT_DOCUMENT_TEXT = ""

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

        editor.settings.apply {
            additionalLinesCount = 0
            isLineNumbersShown = false
            isVirtualSpace = false
            isLineMarkerAreaShown = false
            isIndentGuidesShown = false
            isFoldingOutlineShown = false
            isCaretRowShown = false
        }

        return editor
    }

    /**
     * Releases the given pattern editor.
     *
     * @param patternEditor the pattern editor to be released
     */
    fun dispose(patternEditor: Editor) {
        EditorFactory.getInstance().releaseEditor(patternEditor);
    }

    private fun createDocument(): Document {
        return EditorFactory.getInstance().createDocument(DEFAULT_DOCUMENT_TEXT)
    }
}
