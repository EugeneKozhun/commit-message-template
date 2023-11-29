// This is a generated file. Not intended for manual editing.
package com.kozhun.commitmessagetemplate.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.kozhun.commitmessagetemplate.language.grammar.CMTElementType;
import com.kozhun.commitmessagetemplate.language.grammar.CMTTokenType;
import com.kozhun.commitmessagetemplate.language.psi.impl.*;

public interface CMTTypes {

  IElementType CARET_POSITION = new CMTElementType("CARET_POSITION");
  IElementType OTHER = new CMTElementType("OTHER");
  IElementType TASK_ID = new CMTElementType("TASK_ID");

  IElementType TOKEN = new CMTTokenType("TOKEN");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == CARET_POSITION) {
        return new CMTCaretPositionImpl(node);
      }
      else if (type == OTHER) {
        return new CMTOtherImpl(node);
      }
      else if (type == TASK_ID) {
        return new CMTTaskIdImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
