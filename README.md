# LanguageSyntaxParser
Parse an input text file to determine if the code's language syntax structure is in line with expect expected language syntax using Java.

The Parser class looks at the inputed text file and goes through each character to determine if the syntax matches the expected syntax for our custom language.
The Scanner class looks at the character ingested to return a id that matches the ingested character. The id can represent a letter, digit, expression, assignment or comparison. The scanner also takes into account any spaces and skips them for the purpose of evaluation.
The Token class contains all the possible id that is used in our Scanner class.

test.txt contain a list of all the expressions used in this language.
test2.txt contains our test file written in the custom language for testing the project. It contains 2 parts, the function which has expression in paranthesis and the variable assignments such as (a 0) which is a = 0 at the end.
