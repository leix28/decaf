#实验报告

##`++/--`运算符

在词法分析中将`++`和`--`分别识别为`Parser.INC`和`Parser.DEC`，在语法分析中定义相应的token。

在语法分析中加入

```
Expr : LValue INC {
         $$.expr = new Tree.Unary(Tree.POSTINC, $1.lvalue, $2.loc);
       }
	 | INC IDENTIFIER {
	   	 $$.expr = new Tree.Unary(Tree.PREINC, 
	   	                          new Tree.Ident(null, $2.ident, $2.loc), $1.loc);
	   }
	 | LValue DEC
	   ...
	 | DEC IDENTIFIER
	   ...
```
这样做的原因是避免冲突，可能会支持一些本不应该支持的语法，例如 a.b++，但这些问题可以在语义分析中予以消除。

相应的需要添加`INC`和`DEC`的优先级，结合性为`%nonassoc`。

在`Tree.Unary`中加入对`POSTINC, PREINC, POSTDEC, PREDEC`的支持

##三元运算符

在词法分析中加入`?`和`:`的识别。

在语法分析中加入`Expr : Expr '?' Expr ':' Expr;`，这是一个右结合的运算符。

需要在Tree中定义三元运算符的类`Tree.Ternary`

##反射运算

在词法分析中加入对于`numinstances`的识别。

在语法分析中加入`Expr : NUMINSTANCES '(' IDENTIFIER ')'`。

在Tree中定义Tree.TypeCount。和Tree.TypeCast非常相似。

##串行条件卫士语句

在词法分析中加入对于`|||`的识别，识别为`GUARDED_OR`，加入对`fi`的识别。

在语法分析中，定义形如`E:S`的语句为`GuardedStmt: Expr ':' Stmt`。

定义形如`E1:S1 ||| En:Sn`的语句为`GuardedStmts: GuardedStmts GUARDED_OR GuardedStmt | GuardedStmt`

再加上两端的`if/fi`，写作`GuardedIfStmt : IF GuardedStmts FI`，并将其加入`Stmts`。

需要实现`Tree.GuardedIf`，来支持对于子树GuardedStmts的遍历。还要实现`Tree.GuardedStmt`来支持`GuardedStmt`的结构。

##串行循环卫士语句

在条件卫士语句的基础上，将`if/fi`换作`do/od`即可。