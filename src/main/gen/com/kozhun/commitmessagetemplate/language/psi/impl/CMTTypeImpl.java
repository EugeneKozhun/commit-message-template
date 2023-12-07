// This is a generated file. Not intended for manual editing.
package com.kozhun.commitmessagetemplate.language.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.kozhun.commitmessagetemplate.language.psi.CMTType;
import com.kozhun.commitmessagetemplate.language.psi.CMTVisitor;
import org.jetbrains.annotations.NotNull;

public class CMTTypeImpl extends ASTWrapperPsiElement implements CMTType {

    public CMTTypeImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull CMTVisitor visitor) {
        visitor.visitType(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof CMTVisitor) accept((CMTVisitor) visitor);
        else super.accept(visitor);
    }
}
