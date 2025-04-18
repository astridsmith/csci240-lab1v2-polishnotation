// A Token represents a single unit of the expression: a number, operator, or parenthesis
class Token {
    TokenType type; // The category of the token
    String text;    // The literal text of the token

    Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }
}
