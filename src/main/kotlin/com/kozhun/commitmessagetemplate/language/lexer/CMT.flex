package com.kozhun.commitmessagetemplate.language.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.kozhun.commitmessagetemplate.language.psi.CMTTypes.*;

%%

%{
  public _CMTLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _CMTLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+


%%
<YYINITIAL> {
  {WHITE_SPACE}       { return WHITE_SPACE; }
  "\$TASK_ID"         { return TASK_ID; }
  "\$CARET_POSITION"  { return CARET_POSITION; }
  .                   { return TOKEN; }
}

[^] { return BAD_CHARACTER; }
