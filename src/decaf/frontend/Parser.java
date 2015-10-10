//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short LESS_EQUAL=281;
public final static short GREATER_EQUAL=282;
public final static short EQUAL=283;
public final static short NOT_EQUAL=284;
public final static short INC=285;
public final static short DEC=286;
public final static short UMINUS=287;
public final static short EMPTY=288;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   14,   14,   14,   24,   24,
   21,   21,   23,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   26,   26,   25,   25,   27,   27,   16,
   17,   20,   15,   28,   28,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    3,    1,    0,    2,    0,
    2,    4,    5,    1,    1,    1,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    2,    2,    2,    2,    2,    3,    3,    1,    4,
    5,    6,    5,    1,    1,    1,    0,    3,    1,    5,
    9,    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   75,   69,    0,    0,    0,
    0,   82,    0,    0,    0,    0,   74,    0,    0,    0,
    0,    0,    0,   24,   27,   35,   25,    0,   29,   30,
   31,    0,    0,    0,    0,    0,    0,    0,   46,    0,
    0,    0,   44,    0,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   28,   32,   33,
   34,    0,    0,    0,    0,    0,    0,    0,   64,   66,
    0,    0,    0,    0,    0,    0,    0,   39,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   67,   68,    0,
    0,   60,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,    0,    0,   88,    0,    0,   42,    0,    0,
   80,    0,    0,   71,    0,    0,   73,   43,    0,    0,
   83,   72,    0,   84,    0,   81,
};
final static short yydgoto[] = {                          2,
    3,    4,   65,   20,   33,    8,   11,   22,   34,   35,
   66,   45,   67,   68,   69,   70,   71,   72,   73,   74,
   83,   76,   85,   78,  159,   79,  127,  171,
};
final static short yysindex[] = {                      -226,
 -238,    0, -226,    0, -223,    0, -228,  -67,    0,    0,
  482,    0,    0,    0,    0, -219, -112,    0,    0,    3,
  -87,    0,    0,  -86,    0,   19,  -30,   24, -112,    0,
 -112,    0,  -76,   28,   21,   29,    0,  -52, -112,  -52,
    0,    0,    0,    0,   -3,    0,    0,   33,   34,   35,
   69,    0, -208,   36,   38,   40,    0,   41,   69,   69,
   69,   69,   44,    0,    0,    0,    0,   23,    0,    0,
    0,   26,   32,   39,   22,  498,    0, -190,    0,   69,
   69,   69,    0,  498,    0,   47,    6,   69,   52,   58,
   69,  -37,  -37,  -12,  -12, -188,  347,    0,    0,    0,
    0,   69,   69,   69,   69,   69,   69,   69,    0,    0,
   69,   69,   69,   69,   69,   69,   69,    0,   69,   60,
  379,   45,  403,   62,   61,  498,  -17,    0,    0,  414,
   64,    0,  498,  531,  520,  599,  599,  559,  559,  -36,
  -36,  -12,  -12,  -12,  599,  599,  435,   69,   27,   69,
   27,    0,  446,   69,    0, -169,   69,    0,   67,   66,
    0,  470, -157,    0,  498,   71,    0,    0,   69,   27,
    0,    0,   72,    0,   27,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  116,    0,   -6,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   73,    0,    0,   80,    0,
   80,    0,    0,    0,   90,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -57,    0,    0,    0,    0,    0,
  -56,    0,    0,    0,    0,    0,    0,    0, -139, -139,
 -139, -139, -139,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  509,    0,   93,    0,    0, -139,
  -57, -139,    0,   92,    0,    0,    0, -139,    0,    0,
 -139,  119,  151,  787,  811,    0,    0,    0,    0,    0,
    0, -139, -139, -139, -139, -139, -139, -139,    0,    0,
 -139, -139, -139, -139, -139, -139, -139,    0, -139,   82,
    0,    0,    0,    0, -139,  -13,    0,    0,    0,    0,
    0,    0,  -18,    2,  -27,   74,  371,  460,  490,  819,
  890,  846,  855,  881,  425,  427,    0,  -28,  -57, -139,
  -57,    0,    0, -139,    0,    0, -139,    0,    0,  117,
    0,    0,  -33,    0,   -8,    0,    0,    0,  -16,  -57,
    0,    0,    0,    0,  -57,    0,
};
final static short yygindex[] = {                         0,
    0,  154,  148,  -10,    5,    0,    0,    0,  134,    0,
    7,    0, -131,  -73,    0,    0,    0,    0,    0,    0,
  595,  896,  793,    0,    0,    0,   20,    0,
};
final static int YYTABLESIZE=1174;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
  115,   38,   87,   27,   27,  113,   85,  122,  118,  118,
  114,   85,   77,   59,   27,   21,   59,  161,   32,  163,
   32,   24,   36,  155,   38,   85,  154,   79,   43,   62,
   79,   59,   78,  118,    1,   78,   63,    5,  174,    7,
   36,   61,   58,  176,   42,   58,   44,    9,   12,   13,
   14,   15,   16,  119,  119,   10,   23,   87,   29,   62,
   58,   25,   30,   31,   39,   59,   63,   86,   38,   40,
   41,   61,   80,   81,   82,   88,   62,   89,  119,   90,
   91,   98,  102,   63,   99,  120,  124,  131,   61,   85,
  100,   85,  128,   62,   58,  173,  125,  101,  129,  148,
   63,   62,  152,  150,  157,   61,  166,  168,   63,  154,
  170,  172,  175,   61,   56,    1,   14,   56,   41,   41,
   19,   64,   41,   41,   41,   41,   41,   41,   41,   45,
   18,    5,   56,   37,   45,   45,   40,   45,   45,   45,
   41,   41,   41,   41,   12,   13,   14,   15,   16,   41,
   86,   37,   45,   30,   45,   63,    6,   76,   19,   63,
   63,   63,   63,   63,   36,   63,   56,  160,    0,    0,
    0,    0,   41,    0,   41,    0,    0,   63,   63,    0,
   63,    0,    0,   45,    0,    0,    0,   65,   26,   28,
    0,   65,   65,   65,   65,   65,    0,   65,    0,   37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
   65,   63,   65,    0,    0,    0,    0,    0,   40,   40,
    0,    0,    0,   85,   85,   85,   85,   85,   85,    0,
   85,   85,   85,   85,    0,   85,   85,   85,   85,   85,
   85,   85,   85,   65,    0,    0,   85,   40,  109,  110,
   59,   85,   85,   12,   13,   14,   15,   16,   46,   40,
   47,   48,   49,   50,    0,   51,   52,   53,   54,   55,
   56,   57,  109,  110,    0,    0,   58,    0,   58,   58,
    0,   59,   60,   12,   13,   14,   15,   16,   46,    0,
   47,   48,   49,   50,    0,   51,   52,   53,   54,   55,
   56,   57,    0,    0,   96,   46,   58,   47,    0,    0,
    0,   59,   60,    0,   53,    0,   55,   56,   57,    0,
    0,    0,   46,   58,   47,    0,    0,    0,   59,   60,
   46,   53,   47,   55,   56,   57,    0,    0,    0,   53,
   58,   55,   56,   57,    0,   59,   60,    0,   58,    0,
   56,   56,    0,   59,   60,    0,   56,   56,   41,   41,
    0,    0,   41,   41,   41,   41,   41,   41,    0,   45,
   45,    0,    0,   45,   45,   45,   45,   45,   45,    0,
    0,    0,    0,  115,    0,    0,    0,  132,  113,  111,
    0,  112,  118,  114,    0,   63,   63,    0,    0,   63,
   63,   63,   63,   63,   63,    0,  117,    0,  116,    0,
    0,   57,    0,    0,   57,  115,    0,    0,    0,  149,
  113,  111,    0,  112,  118,  114,    0,   65,   65,   57,
    0,   65,   65,   65,   65,   65,   65,  119,  117,  115,
  116,    0,    0,  151,  113,  111,    0,  112,  118,  114,
  115,    0,    0,    0,    0,  113,  111,  156,  112,  118,
  114,    0,  117,   57,  116,   55,    0,   54,   55,  119,
   54,  115,    0,  117,    0,  116,  113,  111,    0,  112,
  118,  114,  115,   55,    0,   54,    0,  113,  111,    0,
  112,  118,  114,  119,  117,    0,  116,    0,    0,    0,
   52,    0,    0,   52,  119,  117,  115,  116,    0,    0,
    0,  113,  111,    0,  112,  118,  114,   55,   52,   54,
    0,    0,    0,    0,    0,  119,    0,  158,  169,  117,
   53,  116,    0,   53,  115,    0,  119,    0,  164,  113,
  111,    0,  112,  118,  114,   44,    0,    0,   53,    0,
   44,   44,   52,   44,   44,   44,  115,  117,    0,  116,
  119,  113,  111,    0,  112,  118,  114,  115,   44,    0,
   44,    0,  113,  111,    0,  112,  118,  114,    0,  117,
    0,  116,   53,    0,    0,    0,    0,    0,  119,    0,
  117,    0,  116,    0,    0,  115,    0,    0,    0,   44,
  113,  111,    0,  112,  118,  114,   18,    0,    0,    0,
  119,    0,    0,    0,    0,    0,    0,    0,  117,    0,
  116,  119,    0,  103,  104,    0,    0,  105,  106,  107,
  108,  109,  110,    0,    0,  115,    0,    0,    0,   75,
  113,  111,    0,  112,  118,  114,    0,   57,   57,  119,
    0,    0,    0,   57,   57,  103,  104,    0,    0,  105,
  106,  107,  108,  109,  110,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   75,    0,    0,    0,  103,
  104,    0,    0,  105,  106,  107,  108,  109,  110,  119,
  103,  104,    0,    0,  105,  106,  107,  108,  109,  110,
    0,   55,   55,   54,   54,    0,    0,   55,   55,   54,
   54,  103,  104,    0,    0,  105,  106,  107,  108,  109,
  110,    0,  103,  104,    0,    0,  105,  106,  107,  108,
  109,  110,    0,    0,    0,    0,   52,   52,   12,   13,
   14,   15,   16,   75,    0,   75,  103,  104,    0,    0,
  105,  106,  107,  108,  109,  110,    0,    0,    0,    0,
   17,    0,    0,   75,   75,    0,   53,   53,    0,   75,
    0,    0,    0,    0,  103,  104,    0,    0,  105,  106,
  107,  108,  109,  110,    0,   44,   44,    0,    0,   44,
   44,   44,   44,   44,   44,    0,  103,    0,    0,    0,
  105,  106,  107,  108,  109,  110,    0,    0,    0,    0,
    0,  105,  106,  107,  108,  109,  110,    0,    0,    0,
    0,    0,    0,   61,    0,    0,    0,   61,   61,   61,
   61,   61,    0,   61,    0,    0,    0,   77,    0,  105,
  106,    0,    0,  109,  110,   61,   61,   62,   61,    0,
    0,   62,   62,   62,   62,   62,    0,   62,    0,   47,
    0,   47,   47,   47,    0,    0,    0,    0,    0,   62,
   62,    0,   62,   77,    0,    0,    0,   47,   47,   61,
   47,    0,   49,  109,  110,    0,   49,   49,   49,   49,
   49,   50,   49,    0,    0,   50,   50,   50,   50,   50,
    0,   50,    0,   62,   49,   49,    0,   49,    0,    0,
    0,   47,    0,   50,   50,    0,   50,   51,    0,    0,
    0,   51,   51,   51,   51,   51,    0,   51,    0,    0,
   48,    0,   48,   48,   48,    0,    0,    0,   49,   51,
   51,   77,   51,   77,    0,    0,   84,   50,   48,   48,
    0,   48,    0,    0,   92,   93,   94,   95,   97,    0,
    0,   77,   77,    0,    0,    0,    0,   77,    0,    0,
    0,    0,    0,   51,    0,  121,    0,  123,    0,    0,
    0,    0,   48,  126,    0,    0,  130,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  133,  134,  135,
  136,  137,  138,  139,    0,    0,  140,  141,  142,  143,
  144,  145,  146,    0,  147,    0,    0,    0,    0,    0,
  153,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  126,    0,  162,    0,    0,    0,  165,
    0,    0,  167,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   61,   61,    0,    0,   61,   61,   61,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   62,   62,    0,
    0,   62,   62,   62,   62,   47,   47,    0,    0,   47,
   47,   47,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   49,   49,    0,    0,   49,   49,   49,   49,
    0,   50,   50,    0,    0,   50,   50,   50,   50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   51,   51,    0,
    0,   51,   51,   51,   51,    0,   48,   48,    0,    0,
   48,   48,   48,   48,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   37,   59,   59,   91,   91,   42,   40,   81,   46,   46,
   47,   45,   41,   41,   91,   11,   44,  149,   29,  151,
   31,   17,   41,   41,   41,   59,   44,   41,   39,   33,
   44,   59,   41,   46,  261,   44,   40,  276,  170,  263,
   59,   45,   41,  175,   38,   44,   40,  276,  257,  258,
  259,  260,  261,   91,   91,  123,  276,   53,   40,   33,
   59,   59,   93,   40,   44,   93,   40,  276,   41,   41,
  123,   45,   40,   40,   40,   40,   33,   40,   91,   40,
   40,   59,   61,   40,   59,  276,   40,  276,   45,  123,
   59,  125,   41,   33,   93,  169,   91,   59,   41,   40,
   40,   33,   41,   59,   41,   45,  276,   41,   40,   44,
  268,   41,   41,   45,   41,    0,  123,   44,   37,  123,
   41,  125,   41,   42,   43,   44,   45,   46,   47,   37,
   41,   59,   59,   41,   42,   43,  276,   45,   46,   47,
   59,   60,   61,   62,  257,  258,  259,  260,  261,  123,
   59,   59,   60,   93,   62,   37,    3,   41,   11,   41,
   42,   43,   44,   45,   31,   47,   93,  148,   -1,   -1,
   -1,   -1,   91,   -1,   93,   -1,   -1,   59,   60,   -1,
   62,   -1,   -1,   91,   -1,   -1,   -1,   37,  276,  276,
   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,  276,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   60,   93,   62,   -1,   -1,   -1,   -1,   -1,  276,  276,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,   93,   -1,   -1,  280,  276,  285,  286,
  278,  285,  286,  257,  258,  259,  260,  261,  262,  276,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  285,  286,   -1,   -1,  280,   -1,  277,  278,
   -1,  285,  286,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,   -1,   -1,  261,  262,  280,  264,   -1,   -1,
   -1,  285,  286,   -1,  271,   -1,  273,  274,  275,   -1,
   -1,   -1,  262,  280,  264,   -1,   -1,   -1,  285,  286,
  262,  271,  264,  273,  274,  275,   -1,   -1,   -1,  271,
  280,  273,  274,  275,   -1,  285,  286,   -1,  280,   -1,
  277,  278,   -1,  285,  286,   -1,  283,  284,  277,  278,
   -1,   -1,  281,  282,  283,  284,  285,  286,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,  285,  286,   -1,
   -1,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,  285,  286,   -1,   60,   -1,   62,   -1,
   -1,   41,   -1,   -1,   44,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,  277,  278,   59,
   -1,  281,  282,  283,  284,  285,  286,   91,   60,   37,
   62,   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,
   37,   -1,   -1,   -1,   -1,   42,   43,   44,   45,   46,
   47,   -1,   60,   93,   62,   41,   -1,   41,   44,   91,
   44,   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,
   46,   47,   37,   59,   -1,   59,   -1,   42,   43,   -1,
   45,   46,   47,   91,   60,   -1,   62,   -1,   -1,   -1,
   41,   -1,   -1,   44,   91,   60,   37,   62,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   93,   59,   93,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,   59,   60,
   41,   62,   -1,   44,   37,   -1,   91,   -1,   93,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   59,   -1,
   42,   43,   93,   45,   46,   47,   37,   60,   -1,   62,
   91,   42,   43,   -1,   45,   46,   47,   37,   60,   -1,
   62,   -1,   42,   43,   -1,   45,   46,   47,   -1,   60,
   -1,   62,   93,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   60,   -1,   62,   -1,   -1,   37,   -1,   -1,   -1,   91,
   42,   43,   -1,   45,   46,   47,  125,   -1,   -1,   -1,
   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,   -1,
   62,   91,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,  285,  286,   -1,   -1,   37,   -1,   -1,   -1,   45,
   42,   43,   -1,   45,   46,   47,   -1,  277,  278,   91,
   -1,   -1,   -1,  283,  284,  277,  278,   -1,   -1,  281,
  282,  283,  284,  285,  286,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   81,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,  285,  286,   91,
  277,  278,   -1,   -1,  281,  282,  283,  284,  285,  286,
   -1,  277,  278,  277,  278,   -1,   -1,  283,  284,  283,
  284,  277,  278,   -1,   -1,  281,  282,  283,  284,  285,
  286,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
  285,  286,   -1,   -1,   -1,   -1,  277,  278,  257,  258,
  259,  260,  261,  149,   -1,  151,  277,  278,   -1,   -1,
  281,  282,  283,  284,  285,  286,   -1,   -1,   -1,   -1,
  279,   -1,   -1,  169,  170,   -1,  277,  278,   -1,  175,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,  285,  286,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,  285,  286,   -1,  277,   -1,   -1,   -1,
  281,  282,  283,  284,  285,  286,   -1,   -1,   -1,   -1,
   -1,  281,  282,  283,  284,  285,  286,   -1,   -1,   -1,
   -1,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   -1,   -1,   45,   -1,  281,
  282,   -1,   -1,  285,  286,   59,   60,   37,   62,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,   41,
   -1,   43,   44,   45,   -1,   -1,   -1,   -1,   -1,   59,
   60,   -1,   62,   81,   -1,   -1,   -1,   59,   60,   93,
   62,   -1,   37,  285,  286,   -1,   41,   42,   43,   44,
   45,   37,   47,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,   -1,   93,   59,   60,   -1,   62,   -1,   -1,
   -1,   93,   -1,   59,   60,   -1,   62,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   41,   -1,   43,   44,   45,   -1,   -1,   -1,   93,   59,
   60,  149,   62,  151,   -1,   -1,   51,   93,   59,   60,
   -1,   62,   -1,   -1,   59,   60,   61,   62,   63,   -1,
   -1,  169,  170,   -1,   -1,   -1,   -1,  175,   -1,   -1,
   -1,   -1,   -1,   93,   -1,   80,   -1,   82,   -1,   -1,
   -1,   -1,   93,   88,   -1,   -1,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  102,  103,  104,
  105,  106,  107,  108,   -1,   -1,  111,  112,  113,  114,
  115,  116,  117,   -1,  119,   -1,   -1,   -1,   -1,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  148,   -1,  150,   -1,   -1,   -1,  154,
   -1,   -1,  157,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=288;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","INC",
"DEC","UMINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : INC Expr",
"Expr : Expr INC",
"Expr : DEC Expr",
"Expr : Expr DEC",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 441 "Parser.y"

	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }

    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 618 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 54 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 60 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 64 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 74 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 80 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 84 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 88 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 92 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 96 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 100 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 106 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 112 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 116 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 122 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 126 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 130 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 138 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>();
                	}
break;
case 20:
//#line 145 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 149 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 156 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 160 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 166 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 172 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 176 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 183 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 188 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 36:
//#line 203 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 37:
//#line 207 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 38:
//#line 211 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 40:
//#line 218 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 224 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 42:
//#line 231 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 43:
//#line 237 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 44:
//#line 246 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 47:
//#line 252 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 48:
//#line 256 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 49:
//#line 260 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 264 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 268 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 272 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 276 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 280 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 284 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 288 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 292 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 296 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 300 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 304 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 61:
//#line 308 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 316 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(1).loc);
                  }
break;
case 64:
//#line 320 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(0).loc);
                  }
break;
case 65:
//#line 324 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(1).loc);
                  }
break;
case 66:
//#line 328 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(0).loc);
                  }
break;
case 67:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 68:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 69:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 70:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 71:
//#line 348 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 72:
//#line 352 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 73:
//#line 356 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 74:
//#line 362 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 75:
//#line 366 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 77:
//#line 373 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 78:
//#line 380 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 79:
//#line 384 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 80:
//#line 391 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 81:
//#line 397 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 82:
//#line 403 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 83:
//#line 409 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 84:
//#line 415 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 85:
//#line 419 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 86:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 87:
//#line 429 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1229 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
