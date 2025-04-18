// Tokenizer reads a raw string and produces a sequence of tokens
public class Tokenizer {
    private String input;
    private int pos = 0;

    Tokenizer(String input) {
        this.input = input.replaceAll("\\s+", ""); // Remove all whitespace for simplicity
    }

    // Produce the next token from the input string
    Token nextToken() {
        if (pos >= input.length()) return new Token(TokenType.EOF, ""); // End of input

        char current = input.charAt(pos);

        // Read a multi-digit number
        if (Character.isDigit(current)) {
            int start = pos;
            while (pos < input.length() && Character.isDigit(input.charAt(pos))) pos++;
            return new Token(TokenType.NUMBER, input.substring(start, pos));
        }

        // Recognize operators and parentheses
        switch (current) {
            case '+': pos++; return new Token(TokenType.PLUS, "+");
            case '-': pos++; return new Token(TokenType.MINUS, "-");
            case '*': pos++; return new Token(TokenType.MUL, "*");
            case '/': pos++; return new Token(TokenType.DIV, "/");
            case '(': pos++; return new Token(TokenType.LPAREN, "(");
            case ')': pos++; return new Token(TokenType.RPAREN, ")");
            default:
                throw new RuntimeException("Unknown character: " + current); // Catch any unexpected character
        }
    }
}