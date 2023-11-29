// This is a generated file. Not intended for manual editing.
package com.kozhun.commitmessagetemplate.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.kozhun.commitmessagetemplate.language.psi.CMTTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class CMTParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return cmtFile(b, l + 1);
  }

  /* ********************************************************** */
  // '$CARET_POSITION'
  public static boolean caret_position(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "caret_position")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CARET_POSITION, "<caret position>");
    r = consumeToken(b, "$CARET_POSITION");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean cmtFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmtFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cmtFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // task_id|caret_position|other
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = task_id(b, l + 1);
    if (!r) r = caret_position(b, l + 1);
    if (!r) r = other(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // TOKEN
  public static boolean other(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "other")) return false;
    if (!nextTokenIs(b, TOKEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TOKEN);
    exit_section_(b, m, OTHER, r);
    return r;
  }

  /* ********************************************************** */
  // '$TASK_ID'
  public static boolean task_id(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "task_id")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TASK_ID, "<task id>");
    r = consumeToken(b, "$TASK_ID");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
