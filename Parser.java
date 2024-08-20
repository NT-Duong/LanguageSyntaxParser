/* Complete all the methods.
EBNF of Mini Language
Program" --> "("Sequence State")".
Sequence --> "("Statements")".
Statements --> Stmt*
Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
State -->  "("Pairs")".
Pairs -->  Pair*.
Pair --> "("Identifier Literal")".
NullStatement --> "skip".
Assignment --> "assign" Identifier Expression.
Conditional --> "conditional" Expression Stmt Stmt.
Loop --> "loop" Expression Stmt.
Block --> "block" Statements.
Expression --> Identifier | Literal | "("Operation Expression Expression")".
Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".

Note: Treat Identifier and Literal as terminal symbols. Every symbol inside " and " is a terminal symbol. The rest are non terminals.

P>
  (Sequence)>
    (St)>
      Stmt*>
        N|A||C||L|B
  State>
    (Pairs)>
      Pairs*>
        (E)>
          E>
            Id|L|(O E E)>
              O
*/

import java.util.ArrayList;

public class Parser{
  private Token currentToken;
  Scanner scanner;
  ArrayList<Token> rawProgram;
  int nullcount;
  boolean nullStatement;

  private void accept(byte expectedKind) {
    if (currentToken.kind == expectedKind){
      //add currentToken to rawProgram array
      rawProgram.add(currentToken);
      //print out currentToken spelling and kind
      System.out.println("Line: "+1+", spelling = ["+ currentToken.spelling+"], kind = "+ currentToken.kind);
      currentToken = scanner.scan();
  }
    else{
      new Error("Syntax error: " + currentToken.spelling + " is not expected.",
                currentToken.line);
    }
  }

  private void acceptIt() {
    currentToken = scanner.scan();
  }

  public void parse() {
    SourceFile sourceFile = new SourceFile();
    scanner = new Scanner(sourceFile.openFile());
    // get first token
    currentToken = scanner.scan();
    rawProgram = new ArrayList<>();
    // check if file is empty
      if (currentToken.kind != 13){
        // parse the file
        parseProgram();
        System.out.println("The completed program:");
        //print out the completed rawProgram arraylist
        for(int i = 0; i < rawProgram.size(); i++){
          Token a = rawProgram.get(i);
          System.out.print(a.spelling+" ");
        }
        System.out.println("");
      }
      // end of file OR error
        else{
          new Error("Syntax error: Redundant characters at the end of program.", currentToken.line);
        }
  }

  //Program" --> "("Sequence State")".
  private void parseProgram() {
    //parse P into (SS)
    System.out.println("Enter Program");
    accept((byte) 9);
    parseSequence();
    parseState();
    accept((byte) 10);
    System.out.println("Exit Program");
  }

  //Sequence --> "("Statements")".
  private void parseSequence(){
    //parse (SS) into (St)
    System.out.println("Enter Sequence");
    accept((byte) 9);
    parseStatements();
    accept((byte) 10);
    System.out.println("Exit Sequence");
  }

  //Statements --> Stmt*
  private void parseStatements(){
    System.out.println("Enter Statements");
    parseStmt();
    System.out.println("Exit Statements");
  }

  //Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
  private void parseStmt(){
    System.out.println("Enter Stmt");
    //create as many stmt as needed
      //check for non-NullStatement
      while (currentToken.kind != 10){
        //Skip
        if(currentToken.kind ==6){
          parseNullStatement();
        }
        else{
          accept((byte) 9);
          //Assignment
          if (currentToken.kind == 2){
            parseAssignment();
            accept((byte) 10);
          }
          //Conditional
          else if(currentToken.kind == 3){
            parseConditional();
            accept((byte) 10);
          }
          //Loop
          else if(currentToken.kind == 4){
            parseLoop();
            accept((byte) 10);
          }
          //Block
          else if(currentToken.kind == 5){
            parseBlock();
            accept((byte) 10);
          }
          else{
            break;
          }
        }
      }
    System.out.println("Exit Stmt");
  }

  //State -->  "("Pairs")".
  private void parseState(){
    System.out.println("Enter State");
    accept((byte) 9);
    parsePairs();
    accept((byte) 10);
    System.out.println("Exit State");
  }

  //Pairs --> Pair*.
  private void parsePairs(){
    System.out.println("Enter Pairs");
      parsePair();
    System.out.println("Exit Pairs");
  }

  //Pair --> "("Identifier Literal")".
  private void parsePair(){
    System.out.println("Enter Pair");
    //add 0 or more pairs.
    while(currentToken.kind == 9){
      accept((byte) 9);
      accept((byte) 0);
      accept((byte) 1);
      accept((byte) 10);
    }
    System.out.println("Exit Pair");
  }

  //NullStatement --> skip.
  private void parseNullStatement(){
    System.out.println("NullStatement");
      accept((byte) 6);
  }

  //Assignment --> "assign" Identifier Expression.
  private void parseAssignment(){
    System.out.println("Enter Assignment");
    accept((byte) 2);
    accept((byte) 0);
    parseExpression();
    System.out.println("Exit Assignment");
  }

  //Conditional --> "conditional" Expression Stmt Stmt.
  private void parseConditional(){
    System.out.println("Enter Conditional");
    accept((byte) 3);
    parseExpression();;
    parseStmt();
    parseStmt();
    System.out.println("Exit Conditional");
  }

  //Loop --> "loop" Expression Stmt.
  private void parseLoop(){
    System.out.println("Enter Loop");
    accept((byte) 4);
    parseExpression();
    parseStmt();
    System.out.println("Exit Loop");
  }

  //Block --> "block" Statements.
  private void parseBlock(){
    System.out.println("Enter Block");
    accept((byte) 5);
    parseStatements();
    System.out.println("Exit Block");
  }

  //Expression --> Identifier | Literal | "("Operation Expression Expression")".
  private void parseExpression(){
    System.out.println("Enter Expression");
    if(currentToken.kind != 0 && currentToken.kind !=1){
      accept((byte) 9);
      parseOperation();
      parseExpression();
      parseExpression();
      accept((byte) 10);
    }
    //identifier
    else if(currentToken.kind == 0){
      accept((byte) 0);
    }
    //litteral
    else{
      accept((byte) 1);
    }
    System.out.println("Exit Expression");
  }

  //Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".
  private void parseOperation(){
    System.out.println("Enter Operation");
    if(currentToken.kind == 7){
      accept((byte) 7);
    }
    else if (currentToken.kind == 8){
      accept((byte) 8);
    }
    else{
      accept((byte) 11);
    }
    System.out.println("Exit Operation");
  }
}
