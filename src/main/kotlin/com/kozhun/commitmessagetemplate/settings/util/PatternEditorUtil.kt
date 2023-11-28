package com.kozhun.commitmessagetemplate.settings.util

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.project.Project

object PatternEditorUtil {
    private const val DEFAULT_DOCUMENT_TEXT = ""

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

    private fun createDocument(): Document {
        return EditorFactory.getInstance().createDocument(DEFAULT_DOCUMENT_TEXT)
    }
}
