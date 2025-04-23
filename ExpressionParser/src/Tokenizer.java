// Tokenizer reads a raw string and produces a sequence of tokens
public class Tokenizer {
    private String input;
    private int pos = 0;

    Tokenizer(String input) {
        formChecker(input);

        this.input = input; // compat: don't remove the whitespace, needed to deliminate tokens
    }

    private void formChecker(String input) {
        // I know this is out of character for the program, but it's also hella easy and comfy for me...
        String[] inputToCheck = input.split("");
        char[] test = {'a', 'b'};
        // check every other character to make sure it's a space

        boolean switcher = false; // should never start with a space
        for (int i = 0; i < inputToCheck.length; i++){
            if(!switcher){
                if (inputToCheck[i].equals(" ")){
                    throw new RuntimeException("Formatting Error: Separate operands and operators with a single space.\n Don't add additional spaces!");
                } else {
                    switcher = true;
                }
            } else {
                if (!(inputToCheck[i].equals(" "))){
                    try {
                        int convTest = Integer.parseInt(String.valueOf(inputToCheck[i])); // if this succeeds, the character can be converted into an int, which means it's a multi-character number and we shouldn't toggle the switcher or throw an error
                    } catch (NumberFormatException exception){
                        throw new RuntimeException("Formatting Error: Separate operands and operators with a single space.\n Don't add additional spaces!");
                    }
                } else {
                    switcher = false;
                }
            }
        }
    }

    // Produce the next token from the input string
    Token nextToken() {
        // update: handle whitespace "tokens" by skipping them altogether, sorry not sorry
        while (true){

            if (pos >= input.length()) return new Token(TokenType.EOF, ""); // End of input

            char current = input.charAt(pos);
            // if whitespace, skip and advance
            if(!(current == ' ')) {
                // Read a multi-digit number
                if (Character.isDigit(current)) {
                    int start = pos;
                    while (pos < input.length() && Character.isDigit(input.charAt(pos))) pos++;
                    return new Token(TokenType.NUMBER, input.substring(start, pos));
                }

                // Recognize operators and parentheses
                switch (current) {
                    case '+':
                        pos++;
                        return new Token(TokenType.PLUS, "+");
                    case '-':
                        pos++;
                        return new Token(TokenType.MINUS, "-");
                    case '*':
                        pos++;
                        return new Token(TokenType.MUL, "*");
                    case '/':
                        pos++;
                        return new Token(TokenType.DIV, "/");
                    default:
                        throw new RuntimeException("Unknown character: " + current); // Catch any unexpected character
                }
            } else {
                pos++; // advance to next character by while-looping
            }

        }

    }
}