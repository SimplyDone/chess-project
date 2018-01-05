package chess.project;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Alex Zurad
 */
public class TextInput {

    public static String getStringChoice(String msg, String regex) {
        String selection = "";
        while (selection.equals("")) {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            try {
                selection = sc.next();

                if (!selection.matches(regex)) {
                    System.out.println("[Invalid choice]");
                    selection = "";
                }

            } catch (InputMismatchException e) {
                System.out.println("[Invalid choice]");
                selection = "";
            }
        }
        return selection;
    }

    public static int getIntChoice(String msg, String regex) {
        int selection = -1;

        while (selection == -1) {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);
            try {
                selection = Integer.parseInt(sc.next(regex));

            } catch (InputMismatchException e) {
                System.out.println("[Invalid choice]");
                selection = -1;
            }
        }

        return selection;
    }

    public static boolean getBoolChoice(String msg) {
        return getIntChoice(msg, "[1-2]") == 1;
    }

}
