package xl.playground.expression;

import java.util.Scanner;

/**
 * Created by xl on 3/8/16.
 */
public class ReadEvalPrintLoop {

    private static final String PROMPT_SYMBOL = ">>>";

    public static void main(String...args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                Thread.currentThread().interrupt();
                System.out.print(PROMPT_SYMBOL);
                String input = scanner.nextLine();
                System.out.println(new Expression(input).evaluate(Object.class));
            } catch (ClassCastException | IllegalStateException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
