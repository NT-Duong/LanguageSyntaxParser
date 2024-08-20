/*
1. You do not need a separate token (number) for each operator. All of them should be translated to one token (number) which is OPERATOR. See below
2. In the switch the code for identifier and literal is complete. Do not change it

3. Symbol e means epsilon.

 BNF grammar of Mini Language

 Program" --> "("Sequence State")".
 Sequence --> "("Statements")".
 Statements --> Statements  Stmt | e
 Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
 State -->  "("Pairs")".
 Pairs -->  Pairs Pair | e.
 Pair --> "("Identifier Literal")".
 NullStatement --> "skip".
 Assignment --> "assign" Identifier Expression.
 Conditional --> "conditional" Expression Stmt Stmt.
 Loop --> "loop" Expression Stmt.
 Block --> "block" Statements.
 Expression --> Identifier | Literal | "("Operation Expression Expression")".
 Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".

 Note: Treat Identifier and Literal as terminal symbols. Every symbol inside " and " is a terminal symbol. The rest are non terminals.

Input file: test.txt
Output:
 Line: 1, spelling = [(], kind = 9
 Line: 1, spelling = [)], kind = 10
 Line: 1, spelling = [sum], kind = 0
 Line: 1, spelling = [a], kind = 0
 Line: 1, spelling = [2], kind = 1
 Line: 1, spelling = [xyz], kind = 0
 Line: 2, spelling = [skip], kind = 6
 Line: 2, spelling = [assign], kind = 2
 Line: 2, spelling = [conditional], kind = 3
 Line: 2, spelling = [loop], kind = 4
 Line: 2, spelling = [block], kind = 5
 Line: 3, spelling = [1234], kind = 1
 Line: 4, spelling = [+], kind = 11
 Line: 4, spelling = [-], kind = 11
 Line: 4, spelling = [*], kind = 11
 Line: 4, spelling = [/], kind = 11
 Line: 4, spelling = [<], kind = 11
 Line: 4, spelling = [<=], kind = 11
 Line: 4, spelling = [>], kind = 11
 Line: 4, spelling = [>=], kind = 11
 Line: 4, spelling = [=], kind = 11
 Line: 4, spelling = [!=], kind = 11
 Line: 4, spelling = [or], kind = 8
 Line: 4, spelling = [and], kind = 7
 Line: 5, spelling = [-], kind = 11
 Line: 5, spelling = [1234], kind = 1
 Line 6: wrong token !

Note: After you get an error message for the symbol = remove this symbol and
run the program. Repeat this until the last wrong token which is: ?

You should get the following error messges:
 Line 6: wrong token !
 Line 6: wrong token ?
*/

import java.io.*;

public class Scanner{
  private char currentChar;
  private byte currentKind;
  private StringBuffer currentSpelling;
  BufferedReader inFile;
  private static int line = 1;
  private int i = 0;
  private String word = "";
  private String outputString = "";

  public Scanner(BufferedReader inFile){
    this.inFile = inFile;
    try{
      i = this.inFile.read();
      if(i == -1) //end of file
        currentChar = '\u0000';
      else
        currentChar = (char)i;
        i++;
    }
    catch(IOException e){
        System.out.println(e);
    }
  }

  private void takeIt(){
    currentSpelling.append(currentChar);
    try{
      i = inFile.read();
      if(i == -1) //end of file
        currentChar = '\u0000';
      else
        currentChar = (char)i;
        i++;
    }
    catch(IOException e){
        System.out.println(e);
    }
  }

  private void discard(){
    try{
      i = inFile.read();
      if(i == -1) //end of file
        currentChar = '\u0000';
      else
        currentChar = (char)i;
        i++;
    }
    catch(IOException e){
        System.out.println(e);
    }
  }

  // Get String and output the correct byte type
  private byte scanToken(String wordToken){
    String id = wordToken;
    try {
      int a = Integer.parseInt(outputString);
      id = "DIGIT";
    }
    catch(NumberFormatException nfe){
        id = String.valueOf(outputString);
    }
    switch(id){
      case "(":
        return 9;
      case ")":
        return 10;
      case "+":
        return 11;
      case "-":
        return 11;
      case "*":
        return 11;
      case "/":
        return 11;
      case "<":
        return 11;
      case "<=":
        return 11;
      case ">":
        return 11;
      case ">=":
        return 11;
      case "=":
        return 11;
      case "!=":
        return 11;
      case "or":
        return 8;
      case "and":
        return 7;
      case "skip":
        return 6;
      case "assign":
        return 2;
      case "conditional":
        return 3;
      case "loop":
        return 4;
      case "block":
        return 5;
      case "LETTER":
        return 0;
      case "DIGIT":
        return 1;
      case "\u0000":
        return 13;
      // default returns error
      default:
        return 12;
    }
  }

  private void scanSeparator(){
    switch(currentChar){
      case ' ': case '\n': case '\r': case '\t':
        if(currentChar == '\n')
          line++;
        discard();
    }
  }

  //scan the current string buffer
  public Token scan(){
    currentSpelling = new StringBuffer("");
    while(currentChar == ' ' ||currentChar == '\n' || currentChar == '\r'){
      scanSeparator();
    }
    // End of File
    if(currentChar == '\u0000'){
      outputString = "\u0000";
      currentKind = scanToken(outputString);
      takeIt();
      // return new Token(currentKind, outputString, line);
    }
    // String Variable
    else if(isLetter(currentChar) == true){
      // create a word from letter or number characters
      while(isLetter(currentChar) == true || isDigit(currentChar) == true){
        word += currentChar;
        takeIt();
      }
      outputString = word;
      word = "";
      currentKind = 0;
      // return new Token(currentKind, outputString, line);
    }
    // Integer
    else if(isDigit(currentChar) == true){
      //create a larger number from number characters
      while(isDigit(currentChar) == true){
        word += currentChar;
        takeIt();
      }
      outputString = word;
      word = "";
      currentKind = scanToken(outputString);
      // return new Token(currentKind, outputString, line);
    }
     // Graphic
    else if (isGraphic(currentChar) == true ){
      //check for less
      if (currentChar == '<'){
        word += currentChar;
        takeIt();
        for(i=0; i<1;i++){
          // Check once for equal
          if (isGraphic(currentChar) == true && currentChar == '='){
            word += currentChar;
            takeIt();
          }
          else{
            outputString = word;
            word = "";
            currentKind = scanToken(outputString);
            return new Token(currentKind, outputString, line);
          }
        }
        outputString = word;
        word = "";
        currentKind = scanToken(outputString);
        // return new Token(currentKind, outputString, line);
      }
      //check for More
      else if (currentChar == '>'){
        word += currentChar;
        takeIt();
        for(i=0; i<1;i++){
          // Check once for equal
          if (isGraphic(currentChar) == true && currentChar == '='){
            word += currentChar;
            takeIt();
          }
          else{
            outputString = word;
            word = "";
            currentKind = scanToken(outputString);
            return new Token(currentKind, outputString, line);
          }
        }
        outputString = word;
        word = "";
        currentKind = scanToken(outputString);
        // return new Token(currentKind, outputString, line);
      }
      //check for Not
      else if (currentChar == '!'){
        word += currentChar;
        takeIt();
        for(i=0; i<1;i++){
          // Check once for equal
          if (isGraphic(currentChar) == true && currentChar == '='){
            word += currentChar;
            takeIt();
          }
          else{
            outputString = word;
            word = "";
            currentKind = scanToken(outputString);
            return new Token(currentKind, outputString, line);
          }
        }
        outputString = word;
        word = "";
        currentKind = scanToken(outputString);
        // return new Token(currentKind, outputString, line);
      }
      // All other Graphics
      else{
        word += currentChar;
        outputString = word;
        word = "";
        currentKind = scanToken(outputString);
        takeIt();
        // return new Token(currentKind, outputString, line);
      }
    }
    return new Token(currentKind, outputString, line);
  }

  private boolean isGraphic(char c){
    return c == '\t' ||(' ' <= c && c <= '~');
  }

  private boolean isDigit(char c){
    return '0' <= c && c <= '9';
  }

  private boolean isLetter(char c){
    return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
  }
}
