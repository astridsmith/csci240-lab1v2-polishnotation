// The parser evaluates an arithmetic expression using recursive descent
public class Parser {
    private Tokenizer tokenizer;
    private Token currentToken; // The current token we're looking at

    Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.currentToken = tokenizer.nextToken(); // Initialize by reading the first token
    }

    // Consume the current token if it matches the expected type
    private void eat(TokenType expected) {
        if (currentToken.type == expected) {
            currentToken = tokenizer.nextToken();
        } else {
            throw new RuntimeException("Expected " + expected + " but got " + currentToken.type);
        }
    }

    int parseExpr() {
        Token token = currentToken;
        eat(token.type);
        if (token.type == TokenType.NUMBER){
            return Integer.parseInt(token.text); // base case
        }else {
            // left and right hand sides, after left is parsed we move to the right one position-wise through the eating process above
            int lhs = parseExpr();
            int rhs = parseExpr();

            switch (token.type){
                case PLUS:
                    return lhs + rhs;
                case MINUS:
                    return lhs - rhs;
                case MUL:
                    return lhs * rhs;
                case DIV:
                    return  lhs / rhs;
                default:
                    throw new RuntimeException("Unexpected token: " + token.type);
            }
        }

    }

    // Parse numbers or parenthesized expressions (highest precedence)
    int parseFactor() {
        if (currentToken.type == TokenType.NUMBER) {
            int value = Integer.parseInt(currentToken.text); // Integer.parseInt() is a Java method that converts a string representation of a number into an integer.
            eat(TokenType.NUMBER);
            return value;
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken.type); // Error handling
        }
    }
}