package com.kozhun.commitmessagetemplate.ui.components.common

import com.intellij.ui.JBColor
import com.intellij.ui.RoundedLineBorder
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.JBUI

class SynonymLabel(text: String) : JBLabel(text) {
    init {
        border = JBUI.Borders.compound(
            RoundedLineBorder(JBColor.border(), BORDER_RADIUS),
            JBUI.Borders.empty(TOP_BOTTOM_GAP, LEFT_RIGHT_GAP)
        )
    }

    companion object {
        private const val BORDER_RADIUS = 8
        private const val TOP_BOTTOM_GAP = 4
        private const val LEFT_RIGHT_GAP = 8
    }
}
