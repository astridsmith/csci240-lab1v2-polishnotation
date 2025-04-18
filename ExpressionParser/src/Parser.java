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

    // Parse expressions with addition and subtraction (lowest precedence)
    int parseExpr() {
        int result = parseTerm();

        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
            TokenType op = currentToken.type;
            eat(op);
            int rhs = parseTerm(); // Evaluate the right-hand side
            if (op == TokenType.PLUS) {
                result += rhs;
            } else {
                result -= rhs;
            }
        }

        return result;
    }

    // Parse terms with multiplication and division (medium precedence)
    int parseTerm() {
        int result = parseFactor();

        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            TokenType op = currentToken.type;
            eat(op);
            int rhs = parseFactor(); // Evaluate the right-hand side
            if (op == TokenType.MUL) {
                result *= rhs;
            } else {
                result /= rhs;
            }
        }

        return result;
    }

    // Parse numbers or parenthesized expressions (highest precedence)
    int parseFactor() {
        if (currentToken.type == TokenType.NUMBER) {
            int value = Integer.parseInt(currentToken.text);
            eat(TokenType.NUMBER);
            return value;
        } else if (currentToken.type == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            int value = parseExpr(); // Recursively evaluate the expression inside the parentheses
            eat(TokenType.RPAREN);
            return value;
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken.type); // Error handling
        }
    }
}