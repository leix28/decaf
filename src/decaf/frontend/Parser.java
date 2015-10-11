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
public final static short NUMINSTANCES=287;
public final static short FI=288;
public final static short GUARDED_OR=289;
public final static short UMINUS=290;
public final static short EMPTY=291;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   16,   22,   22,   23,
   14,   14,   14,   27,   27,   25,   25,   26,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   29,   29,   28,   28,   30,   30,   17,   18,   21,   15,
   31,   31,   19,   19,   20,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    1,    2,    2,    2,    1,    3,    3,    1,    3,
    3,    1,    0,    2,    0,    2,    4,    5,    1,    1,
    1,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    2,    2,    2,    2,    2,
    2,    5,    3,    3,    1,    4,    5,    6,    5,    4,
    1,    1,    1,    0,    3,    1,    5,    9,    1,    6,
    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   82,   75,    0,    0,    0,
    0,   89,    0,    0,    0,    0,   81,    0,    0,    0,
    0,    0,    0,    0,   24,   27,   36,   25,    0,   29,
   30,   31,   32,    0,    0,    0,    0,    0,    0,    0,
   51,    0,    0,    0,    0,   39,    0,   49,   50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   28,   33,   34,   35,    0,    0,    0,
    0,    0,    0,   69,   71,    0,    0,    0,    0,    0,
    0,    0,   44,    0,    0,    0,    0,    0,    0,    0,
   37,    0,    0,    0,    0,    0,    0,   73,   74,    0,
    0,    0,   65,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   38,   40,   76,    0,    0,   95,    0,
   80,    0,   47,    0,    0,    0,   87,    0,    0,   77,
    0,    0,   79,    0,   48,    0,    0,   90,   78,    0,
   91,    0,   88,
};
final static short yydgoto[] = {                          2,
    3,    4,   66,   20,   33,    8,   11,   22,   34,   35,
   67,   45,   68,   69,   70,   71,   72,   73,   74,   75,
   76,   85,   86,   77,   88,   89,   80,  175,   81,  137,
  188,
};
final static short yysindex[] = {                      -253,
 -249,    0, -253,    0, -226,    0, -224,  -67,    0,    0,
 -111,    0,    0,    0,    0, -217,   87,    0,    0,    3,
  -87,    0,    0,  -74,    0,   20,  -29,   26,   87,    0,
   87,    0,  -73,   28,   27,   29,    0,  -49,   87,  -49,
    0,    0,    0,    0,    1,    0,    0,   39,   41,  112,
  129,    0, -145,   42,   43,   45,    0,   46,  129,  129,
   47,  129,  129,   78,    0,    0,    0,    0,   30,    0,
    0,    0,    0,   34,   35,   36,  704,   37,    0, -185,
    0,  129,  129,   78, -244,    0,  417,    0,    0,  704,
   57,    8,  129,   59,   64,  129,  -37,  -37, -172,   -3,
   -3, -170,  441,    0,    0,    0,    0,  129,  129,  129,
  129,  129,  129,    0,    0,  129,  129,  129,  129,  129,
  129,  129,    0,  129,  129,  129,   67,  453,   50,  477,
    0,  129,   32,   76,   95,  704,  -13,    0,    0,  504,
   79,   80,    0,  840,  790,   -7,   -7,  847,  847,  -36,
  -36,   -3,   -3,   -3,   -7,   -7,  515,  539,  704,  129,
   32,  129,   63,    0,    0,    0,  578,  129,    0, -157,
    0,  129,    0,  129,   81,   83,    0,  589, -143,    0,
  704,   88,    0,  704,    0,  129,   32,    0,    0,   89,
    0,   32,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  132,    0,   10,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   75,    0,    0,   96,    0,
   96,    0,    0,    0,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -57,    0,    0,    0,    0, -140,
  -56,    0,    0,    0,    0,    0,    0,    0, -140, -140,
    0, -140, -140, -140,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  766,  406,    0,
    0, -140,  -57, -140,    0,    0,    0,    0,    0,   84,
    0,    0, -140,    0,    0, -140,  874,  883,    0,  910,
  963,    0,    0,    0,    0,    0,    0, -140, -140, -140,
 -140, -140, -140,    0,    0, -140, -140, -140, -140, -140,
 -140, -140,    0, -140, -140, -140,  153,    0,    0,    0,
    0, -140,  -57,    0, -140,  -12,    0,    0,    0,    0,
    0,    0,    0,  100,   17,  996, 1086,  529, 1166,  934,
 1137,  972, 1041, 1050, 1127, 1164,    0,    0,  -17,  -28,
  -57, -140,  382,    0,    0,    0,    0, -140,    0,    0,
    0, -140,    0, -140,    0,   98,    0,    0,  -33,    0,
    7,    0,    0,    9,    0,  -25,  -57,    0,    0,    0,
    0,  -57,    0,
};
final static short yygindex[] = {                         0,
    0,  139,  142,   -6,    4,    0,    0,    0,  123,    0,
  -18,    0, -114,  -78,    0,    0,    0,    0,    0,    0,
    0,    0,   24, 1372,   18, 1101,    0,    0,    0,    5,
    0,
};
final static int YYTABLESIZE=1546;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         92,
  120,   43,   94,   27,  129,  118,   92,    1,  123,  123,
  119,   92,   84,   18,   21,   43,   27,   27,  165,   42,
   24,   44,   32,   41,   32,   92,    5,  169,   86,  120,
  168,   86,   43,   63,  118,  116,    7,  117,  123,  119,
   64,   41,  123,  131,  132,   62,  177,   85,  179,   72,
   85,    9,   72,  124,  124,   10,   92,   64,   23,   29,
   64,   25,   78,   30,   63,   31,   72,   72,   38,   40,
   39,   64,  191,   41,   64,   64,   62,  193,   82,   64,
   83,   93,   94,  124,   95,   96,   99,  124,  104,   92,
  127,   92,  105,  106,  107,   63,  134,  126,  135,  138,
   78,   72,   64,  141,  139,  142,  160,  190,  162,   64,
   63,   12,   13,   14,   15,   16,  166,   64,  182,  171,
  172,  185,   62,   41,  187,   65,  168,   63,  189,  192,
   91,    1,   14,    5,   64,   45,   19,   18,   83,   62,
   63,    6,   93,   63,   63,   12,   13,   14,   15,   16,
   78,   84,   19,   36,   41,  164,   62,   63,   63,    0,
    0,   63,   63,    0,  176,    0,    0,   17,   64,    0,
    0,    0,    0,   62,    0,    0,    0,    0,   78,    0,
   78,    0,    0,    0,    0,   41,    0,   30,   26,   46,
    0,    0,   63,   46,   46,   46,   46,   46,   46,   46,
    0,   28,   37,   78,   78,    0,    0,    0,    0,   78,
   46,   46,   46,   46,   46,   46,    0,    0,   45,   45,
    0,    0,    0,   92,   92,   92,   92,   92,   92,    0,
   92,   92,   92,   92,    0,   92,   92,   92,   92,   92,
   92,   92,   92,   46,    0,   46,   92,   45,  114,  115,
   45,   92,   92,   92,   92,   92,    0,   12,   13,   14,
   15,   16,   46,    0,   47,   48,   49,   50,    0,   51,
   52,   53,   54,   55,   56,   57,    0,  114,  115,    0,
   58,  114,  115,    0,    0,   59,   60,   61,   12,   13,
   14,   15,   16,   46,   64,   47,   48,   49,   50,    0,
   51,   52,   53,   54,   55,   56,   57,    0,    0,    0,
    0,   58,    0,    0,    0,    0,   59,   60,   61,   12,
   13,   14,   15,   16,   46,    0,   47,   48,   49,   50,
    0,   51,   52,   53,   54,   55,   56,   57,  102,   46,
    0,   47,   58,   12,   13,   14,   15,   16,   53,   61,
   55,   56,   57,    0,    0,    0,   46,   58,   47,    0,
    0,    0,   59,   60,   61,   53,    0,   55,   56,   57,
    0,    0,    0,   46,   58,   47,   63,   63,    0,   59,
   60,   61,   53,    0,   55,   56,   57,    0,    0,    0,
   46,   58,   47,    0,    0,    0,   59,   60,   61,   53,
    0,   55,   56,   57,    0,    0,    0,    0,   58,    0,
    0,    0,    0,   59,   60,   61,    0,    0,   65,    0,
    0,    0,    0,   65,   65,    0,   65,   65,   65,   46,
   46,    0,    0,   46,   46,   46,   46,   46,   46,   65,
   43,   65,   50,   65,   65,    0,   42,   50,   50,    0,
   50,   50,   50,  120,    0,    0,    0,    0,  118,  116,
    0,  117,  123,  119,   42,   50,    0,   50,   50,    0,
    0,    0,   65,    0,  133,    0,  122,  120,  121,  125,
    0,  143,  118,  116,    0,  117,  123,  119,    0,  120,
    0,    0,    0,  161,  118,  116,   50,  117,  123,  119,
  122,    0,  121,  125,    0,    0,    0,  124,    0,    0,
    0,    0,  122,  120,  121,  125,    0,  163,  118,  116,
    0,  117,  123,  119,    0,    0,    0,    0,    0,    0,
    0,  124,    0,    0,    0,    0,  122,    0,  121,  125,
  120,    0,    0,  124,    0,  118,  116,  170,  117,  123,
  119,  120,    0,    0,    0,    0,  118,  116,    0,  117,
  123,  119,    0,  122,    0,  121,  125,  124,    0,   57,
    0,    0,   57,    0,  122,  120,  121,  125,    0,    0,
  118,  116,    0,  117,  123,  119,   57,   57,    0,    0,
    0,   57,    0,    0,  124,    0,  174,    0,  122,    0,
  121,  125,    0,    0,    0,  124,    0,  173,    0,    0,
    0,    0,    0,    0,  120,    0,    0,    0,    0,  118,
  116,   57,  117,  123,  119,  120,    0,    0,    0,  124,
  118,  116,    0,  117,  123,  119,    0,  122,    0,  121,
  125,    0,    0,    0,    0,    0,    0,  186,  122,    0,
  121,  125,    0,    0,    0,    0,    0,   45,   65,   65,
    0,    0,   65,   65,   65,   65,   65,   65,  124,    0,
  180,    0,    0,    0,    0,    0,    0,    0,    0,  124,
    0,    0,   50,   50,    0,    0,   50,   50,   50,   50,
   50,   50,    0,  108,  109,    0,    0,  110,  111,  112,
  113,  114,  115,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  108,  109,    0,
    0,  110,  111,  112,  113,  114,  115,    0,    0,  108,
  109,    0,    0,  110,  111,  112,  113,  114,  115,    0,
  120,    0,    0,    0,    0,  118,  116,    0,  117,  123,
  119,    0,    0,  108,  109,    0,    0,  110,  111,  112,
  113,  114,  115,  122,    0,  121,  125,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  108,  109,    0,    0,  110,  111,  112,  113,  114,  115,
    0,  108,  109,    0,  124,  110,  111,  112,  113,  114,
  115,    0,   49,    0,    0,   57,   57,   49,   49,    0,
   49,   49,   49,    0,    0,  108,  109,    0,    0,  110,
  111,  112,  113,  114,  115,   49,  120,   49,   49,    0,
    0,  118,  116,    0,  117,  123,  119,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  122,
    0,  121,    0,    0,  108,  109,   49,    0,  110,  111,
  112,  113,  114,  115,    0,  108,  109,    0,    0,  110,
  111,  112,  113,  114,  115,    0,  120,    0,    0,    0,
  124,  118,  116,  120,  117,  123,  119,    0,  118,  116,
    0,  117,  123,  119,    0,    0,    0,    0,    0,  122,
    0,  121,    0,    0,    0,    0,  122,    0,  121,    0,
   68,    0,    0,    0,   68,   68,   68,   68,   68,   70,
   68,    0,    0,   70,   70,   70,   70,   70,    0,   70,
  124,   68,   68,   68,    0,   68,   68,  124,    0,    0,
   70,   70,   70,    0,   70,   70,   66,    0,    0,    0,
   66,   66,   66,   66,   66,    0,   66,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   68,   66,   66,   66,
    0,   66,   66,    0,   52,   70,   52,   52,   52,    0,
  108,  109,    0,    0,  110,  111,  112,  113,  114,  115,
    0,   52,   52,   52,    0,   52,   52,    0,    0,   67,
    0,    0,   66,   67,   67,   67,   67,   67,   54,   67,
    0,    0,   54,   54,   54,   54,   54,    0,   54,    0,
   67,   67,   67,    0,   67,   67,   52,    0,    0,   54,
   54,   54,    0,   54,   54,    0,   61,    0,    0,   61,
    0,    0,   49,   49,    0,    0,   49,   49,   49,   49,
   49,   49,    0,   61,   61,   67,    0,    0,   61,    0,
    0,    0,    0,    0,   54,    0,  108,    0,    0,    0,
  110,  111,  112,  113,  114,  115,    0,   55,    0,    0,
    0,   55,   55,   55,   55,   55,   56,   55,   61,    0,
   56,   56,   56,   56,   56,    0,   56,    0,   55,   55,
   55,    0,   55,   55,    0,    0,    0,   56,   56,   56,
    0,   56,   56,    0,    0,    0,    0,    0,    0,    0,
  110,  111,  112,  113,  114,  115,   62,  110,  111,   62,
    0,  114,  115,   55,    0,    0,    0,    0,    0,    0,
    0,    0,   56,   62,   62,   79,    0,    0,   62,    0,
   68,   68,    0,    0,   68,   68,   68,   68,    0,   70,
   70,    0,    0,   70,   70,   70,   70,   60,    0,    0,
   60,    0,    0,    0,    0,    0,    0,   53,   62,   53,
   53,   53,    0,   79,   60,   60,   66,   66,    0,   60,
   66,   66,   66,   66,   53,   53,   53,    0,   53,   53,
    0,    0,    0,    0,   59,    0,   58,   59,    0,   58,
   52,   52,    0,    0,   52,   52,   52,   52,    0,   60,
    0,   59,   59,   58,   58,    0,   59,    0,   58,   53,
    0,    0,    0,   79,    0,    0,    0,    0,    0,   67,
   67,    0,    0,   67,   67,   67,   67,    0,   54,   54,
    0,    0,   54,   54,   54,   54,   59,    0,   58,    0,
    0,   79,    0,   79,    0,    0,    0,    0,    0,    0,
    0,    0,   61,   61,    0,    0,    0,    0,   61,   61,
    0,    0,    0,    0,    0,    0,   79,   79,    0,    0,
    0,    0,   79,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,   55,    0,
    0,   55,   55,   55,   55,    0,   56,   56,    0,    0,
   56,   56,   56,   56,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   62,   62,    0,    0,    0,    0,   62,   62,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   60,   60,    0,    0,    0,    0,   60,
   60,    0,    0,   53,   53,    0,    0,   53,   53,   53,
   53,   87,   90,    0,    0,    0,    0,    0,    0,    0,
   97,   98,    0,  100,  101,  103,    0,    0,    0,    0,
   59,   59,   58,   58,    0,    0,   59,   59,    0,    0,
    0,    0,    0,  128,    0,  130,    0,    0,    0,    0,
    0,    0,    0,    0,  136,    0,    0,  140,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  144,
  145,  146,  147,  148,  149,    0,    0,  150,  151,  152,
  153,  154,  155,  156,    0,  157,  158,  159,    0,    0,
    0,    0,    0,   87,    0,    0,  167,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  136,    0,  178,    0,    0,    0,    0,    0,  181,
    0,    0,    0,  183,    0,  184,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   37,   59,   59,   91,   83,   42,   40,  261,   46,   46,
   47,   45,   41,  125,   11,   41,   91,   91,  133,   38,
   17,   40,   29,   41,   31,   59,  276,   41,   41,   37,
   44,   44,   39,   33,   42,   43,  263,   45,   46,   47,
   40,   59,   46,  288,  289,   45,  161,   41,  163,   41,
   44,  276,   44,   91,   91,  123,   53,   41,  276,   40,
   44,   59,   45,   93,   33,   40,   58,   59,   41,   41,
   44,   40,  187,  123,   58,   59,   45,  192,   40,   63,
   40,   40,   40,   91,   40,   40,   40,   91,   59,  123,
  276,  125,   59,   59,   59,   33,   40,   61,   91,   41,
   83,   93,   40,  276,   41,  276,   40,  186,   59,   93,
   33,  257,  258,  259,  260,  261,   41,   40,  276,   41,
   41,   41,   45,  123,  268,  125,   44,   33,   41,   41,
  276,    0,  123,   59,   40,  276,   41,   41,   41,   45,
   41,    3,   59,   44,   33,  257,  258,  259,  260,  261,
  133,   40,   11,   31,  123,  132,   45,   58,   59,   -1,
   -1,   33,   63,   -1,  160,   -1,   -1,  279,   40,   -1,
   -1,   -1,   -1,   45,   -1,   -1,   -1,   -1,  161,   -1,
  163,   -1,   -1,   -1,   -1,  123,   -1,   93,  276,   37,
   -1,   -1,   93,   41,   42,   43,   44,   45,   46,   47,
   -1,  276,  276,  186,  187,   -1,   -1,   -1,   -1,  192,
   58,   59,   60,   61,   62,   63,   -1,   -1,  276,  276,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,   91,   -1,   93,  280,  276,  285,  286,
  276,  285,  286,  287,  288,  289,   -1,  257,  258,  259,
  260,  261,  262,   -1,  264,  265,  266,  267,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,  285,  286,   -1,
  280,  285,  286,   -1,   -1,  285,  286,  287,  257,  258,
  259,  260,  261,  262,  278,  264,  265,  266,  267,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,
   -1,  280,   -1,   -1,   -1,   -1,  285,  286,  287,  257,
  258,  259,  260,  261,  262,   -1,  264,  265,  266,  267,
   -1,  269,  270,  271,  272,  273,  274,  275,  261,  262,
   -1,  264,  280,  257,  258,  259,  260,  261,  271,  287,
  273,  274,  275,   -1,   -1,   -1,  262,  280,  264,   -1,
   -1,   -1,  285,  286,  287,  271,   -1,  273,  274,  275,
   -1,   -1,   -1,  262,  280,  264,  277,  278,   -1,  285,
  286,  287,  271,   -1,  273,  274,  275,   -1,   -1,   -1,
  262,  280,  264,   -1,   -1,   -1,  285,  286,  287,  271,
   -1,  273,  274,  275,   -1,   -1,   -1,   -1,  280,   -1,
   -1,   -1,   -1,  285,  286,  287,   -1,   -1,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,  277,
  278,   -1,   -1,  281,  282,  283,  284,  285,  286,   58,
   59,   60,   37,   62,   63,   -1,   41,   42,   43,   -1,
   45,   46,   47,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   59,   60,   -1,   62,   63,   -1,
   -1,   -1,   91,   -1,   58,   -1,   60,   37,   62,   63,
   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,   37,
   -1,   -1,   -1,   41,   42,   43,   91,   45,   46,   47,
   60,   -1,   62,   63,   -1,   -1,   -1,   91,   -1,   -1,
   -1,   -1,   60,   37,   62,   63,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   -1,   -1,   -1,   60,   -1,   62,   63,
   37,   -1,   -1,   91,   -1,   42,   43,   44,   45,   46,
   47,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   60,   -1,   62,   63,   91,   -1,   41,
   -1,   -1,   44,   -1,   60,   37,   62,   63,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   58,   59,   -1,   -1,
   -1,   63,   -1,   -1,   91,   -1,   58,   -1,   60,   -1,
   62,   63,   -1,   -1,   -1,   91,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,   42,
   43,   93,   45,   46,   47,   37,   -1,   -1,   -1,   91,
   42,   43,   -1,   45,   46,   47,   -1,   60,   -1,   62,
   63,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   -1,
   62,   63,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
   -1,   -1,  281,  282,  283,  284,  285,  286,   91,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
  285,  286,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,  285,  286,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,  285,  286,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,  285,  286,   -1,
   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,  285,  286,   60,   -1,   62,   63,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,  285,  286,
   -1,  277,  278,   -1,   91,  281,  282,  283,  284,  285,
  286,   -1,   37,   -1,   -1,  277,  278,   42,   43,   -1,
   45,   46,   47,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,  285,  286,   60,   37,   62,   63,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,
   -1,   62,   -1,   -1,  277,  278,   91,   -1,  281,  282,
  283,  284,  285,  286,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,  285,  286,   -1,   37,   -1,   -1,   -1,
   91,   42,   43,   37,   45,   46,   47,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   60,
   -1,   62,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,
   47,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
   91,   58,   59,   60,   -1,   62,   63,   91,   -1,   -1,
   58,   59,   60,   -1,   62,   63,   37,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   58,   59,   60,
   -1,   62,   63,   -1,   41,   93,   43,   44,   45,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,  285,  286,
   -1,   58,   59,   60,   -1,   62,   63,   -1,   -1,   37,
   -1,   -1,   93,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   58,   59,   60,   -1,   62,   63,   93,   -1,   -1,   58,
   59,   60,   -1,   62,   63,   -1,   41,   -1,   -1,   44,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
  285,  286,   -1,   58,   59,   93,   -1,   -1,   63,   -1,
   -1,   -1,   -1,   -1,   93,   -1,  277,   -1,   -1,   -1,
  281,  282,  283,  284,  285,  286,   -1,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   37,   47,   93,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   58,   59,
   60,   -1,   62,   63,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   63,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  281,  282,  283,  284,  285,  286,   41,  281,  282,   44,
   -1,  285,  286,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   93,   58,   59,   45,   -1,   -1,   63,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   41,   93,   43,
   44,   45,   -1,   83,   58,   59,  277,  278,   -1,   63,
  281,  282,  283,  284,   58,   59,   60,   -1,   62,   63,
   -1,   -1,   -1,   -1,   41,   -1,   41,   44,   -1,   44,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   93,
   -1,   58,   59,   58,   59,   -1,   63,   -1,   63,   93,
   -1,   -1,   -1,  133,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   93,   -1,   93,   -1,
   -1,  161,   -1,  163,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,  283,  284,
   -1,   -1,   -1,   -1,   -1,   -1,  186,  187,   -1,   -1,
   -1,   -1,  192,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,  283,  284,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,  283,
  284,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   50,   51,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,   63,   64,   -1,   -1,   -1,   -1,
  277,  278,  277,  278,   -1,   -1,  283,  284,   -1,   -1,
   -1,   -1,   -1,   82,   -1,   84,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   93,   -1,   -1,   96,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  108,
  109,  110,  111,  112,  113,   -1,   -1,  116,  117,  118,
  119,  120,  121,  122,   -1,  124,  125,  126,   -1,   -1,
   -1,   -1,   -1,  132,   -1,   -1,  135,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  160,   -1,  162,   -1,   -1,   -1,   -1,   -1,  168,
   -1,   -1,   -1,  172,   -1,  174,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=291;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
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
"DEC","NUMINSTANCES","FI","GUARDED_OR","UMINUS","EMPTY",
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
"Stmt : GuardedIfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"GuardedIfStmt : IF GuardedStmts FI",
"GuardedStmts : GuardedStmts GUARDED_OR GuardedStmt",
"GuardedStmts : GuardedStmt",
"GuardedStmt : Expr ':' Stmt",
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
"Expr : Expr '?' Expr ':' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : NUMINSTANCES '(' IDENTIFIER ')'",
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

//#line 475 "Parser.y"

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
//#line 712 "Parser.java"
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
//#line 58 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 64 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 68 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 78 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 84 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 88 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 92 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 96 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 100 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 104 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 110 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 116 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 120 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 126 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 130 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 134 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 142 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>();
                	}
break;
case 20:
//#line 149 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 153 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 160 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 164 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 170 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 176 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 180 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 187 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 192 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 37:
//#line 208 "Parser.y"
{
                    yyval.stmt = new Tree.GuardedIf(val_peek(1).slist, val_peek(2).loc);
                  }
break;
case 38:
//#line 213 "Parser.y"
{
                    val_peek(2).slist.add(val_peek(0).stmt);
                  }
break;
case 39:
//#line 217 "Parser.y"
{
                    yyval = new SemValue();
                    yyval.slist = new ArrayList<Tree>();
                    yyval.slist.add(val_peek(0).stmt);
                  }
break;
case 40:
//#line 224 "Parser.y"
{
                    yyval.stmt = new Tree.GuardedStmt(val_peek(2).expr, val_peek(0).stmt, val_peek(1).loc);
                  }
break;
case 41:
//#line 229 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 42:
//#line 233 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 43:
//#line 237 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 45:
//#line 244 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 46:
//#line 250 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 47:
//#line 257 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 48:
//#line 263 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 49:
//#line 272 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 52:
//#line 278 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 282 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 286 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 290 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 294 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 298 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 302 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 306 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 310 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 314 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 318 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 322 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 326 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 330 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 66:
//#line 334 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 338 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 342 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(1).loc);
                  }
break;
case 69:
//#line 346 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(0).loc);
                  }
break;
case 70:
//#line 350 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(1).loc);
                  }
break;
case 71:
//#line 354 "Parser.y"
{
                    yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(0).loc);
                  }
break;
case 72:
//#line 358 "Parser.y"
{
                    yyval.expr = new Tree.Ternary(Tree.COND, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(3).loc);
                  }
break;
case 73:
//#line 362 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 366 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 370 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 76:
//#line 374 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 77:
//#line 378 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 78:
//#line 382 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 79:
//#line 386 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 80:
//#line 390 "Parser.y"
{
                    yyval.expr = new Tree.TypeCount(val_peek(1).ident, val_peek(3).loc);
                  }
break;
case 81:
//#line 396 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 82:
//#line 400 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 84:
//#line 407 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 85:
//#line 414 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 86:
//#line 418 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 87:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 88:
//#line 431 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 89:
//#line 437 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 90:
//#line 443 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 91:
//#line 449 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 92:
//#line 453 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 93:
//#line 459 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 94:
//#line 463 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 95:
//#line 469 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1361 "Parser.java"
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
