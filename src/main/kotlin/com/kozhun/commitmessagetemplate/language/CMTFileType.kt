package com.kozhun.commitmessagetemplate.language

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader.getIcon
import javax.swing.Icon

/**
 * An object representing a custom icon for CMTIcon.
 * The ICON property statically holds the reference to the icon.
 */
object CMTIcon {
    val ICON = getIcon("icons/format-button.svg", CMTIcon::class.java)
}

/**
 * Represents the file type for Commit Message Template files.
 */
object CMTFileType : LanguageFileType(CMTLanguage.INSTANCE) {

    override fun getName(): String {
        return "Commit Message Template"
    }

    override fun getDescription(): String {
        return "Commit message template description"
    }

    override fun getDefaultExtension(): String {
        return "cmt"
    }

    override fun getIcon(): Icon {
        return CMTIcon.ICON
    }
}
