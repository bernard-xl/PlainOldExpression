grammar Expression;

expression              : '(' expression ')'                                             #parenExpression
                        | primary                                                        #value
                        | left=expression op='.' Identifier                              #fieldAccess
                        | left=expression '[' index=expression ']'                       #indexAccess
                        | left=expression op=('*' | '/' | '%') right=expression          #arithmetic1
                        | left=expression op=('+' | '-') right=expression                #arithmetic2
                        | left=expression op=('<=' | '>=' | '>' | '<') right=expression  #comparison
                        | left=expression op=('==' | '!=') right=expression              #equality
                        | left=expression op=('&&' | '||') right=expression              #logical
                        ;

primary                 : literal                        #literalValue
                        | reference                      #referenceValue
                        ;

literal                 : '-'? IntegerLiteral            #intLiteral
                        | '-'? FloatingPointLiteral      #floatLiteral
                        | StringLiteral                  #stringLiteral
                        | BooleanLiteral                 #booleanLiteral
                        | NullLiteral                    #nullLiteral
                        ;

reference               : Identifier;


// Tokens

NullLiteral             : 'null' ;

BooleanLiteral          : 'true' | 'false' ;

StringLiteral           : '\'' StringElement* '\'' ;

IntegerLiteral          : (DecimalNumeral | HexNumeral) ;

FloatingPointLiteral    : Digit+ '.' Digit+ ExponentPart?
                        | '.' Digit+ ExponentPart?
                        | Digit ExponentPart
                        | Digit+ ExponentPart? ;


Identifier              : JavaLetter JavaLetterOrDigit* ;

WS                      : [ \t\r\n\u000C]+ -> skip;


// Fragments

fragment StringElement  : ~['\\] ;
fragment HexDigit       : '0'..'9'  |  'A'..'Z'  |  'a'..'z' ;
fragment ExponentPart   : ('E' | 'e') ('+' | '-')? Digit+;
fragment DecimalNumeral :  '0' | NonZeroDigit Digit*;
fragment HexNumeral     :  '0' 'x' HexDigit HexDigit+;
fragment Digit          :  '0' | NonZeroDigit;
fragment NonZeroDigit   :  '1'..'9';

fragment JavaLetter
    :   [a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
    |   // covers all characters above 0x7F which are not a surrogate
        ~[\u0000-\u007F\uD800-\uDBFF]
        {Character.isJavaIdentifierStart(_input.LA(-1))}?
    |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
        [\uD800-\uDBFF] [\uDC00-\uDFFF]
        {Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;

fragment JavaLetterOrDigit
    :   [a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
    |   // covers all characters above 0x7F which are not a surrogate
        ~[\u0000-\u007F\uD800-\uDBFF]
        {Character.isJavaIdentifierPart(_input.LA(-1))}?
    |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
        [\uD800-\uDBFF] [\uDC00-\uDFFF]
        {Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;





