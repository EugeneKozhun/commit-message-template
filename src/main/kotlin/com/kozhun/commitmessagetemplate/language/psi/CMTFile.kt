package com.kozhun.commitmessagetemplate.language.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.kozhun.commitmessagetemplate.language.CMTFileType
import com.kozhun.commitmessagetemplate.language.CMTLanguage

/**
 * Class representing a CMT file.
 *
 * @param viewProvider The file view provider.
 */
class CMTFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, CMTLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return CMTFileType
    }

    override fun toString(): String {
        return "CMT File"
    }
}
