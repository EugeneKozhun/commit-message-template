package com.kozhun.commitmessagetemplate.ui.components.type

import com.intellij.util.ui.JBUI
import com.kozhun.commitmessagetemplate.ui.components.common.SynonymLabel
import net.miginfocom.swing.MigLayout
import java.awt.Insets
import javax.swing.JPanel

class TypeSynonymsPanel(
    private var typeSynonyms: Map<String, String>
) : JPanel() {

    init {
        layout = MigLayout("insets 0, gap 5 5, flowx, align left")

        border = JBUI.Borders.empty()
        reset(typeSynonyms)
    }

    override fun getInsets(): Insets {
        return JBUI.emptyInsets()
    }

    fun reset(newTypeSynonyms: Map<String, String>) {
        removeAll()

        typeSynonyms = newTypeSynonyms
        typeSynonyms.forEach { (key, value) ->
            add(SynonymLabel("$key -> $value"))
        }

        revalidate()
        repaint()
    }
}
