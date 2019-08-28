import core.ManagerControllerImpl;
import core.interfaces.ManagerController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ManagerController managerController = new ManagerControllerImpl();

        String input = sc.nextLine();
        while (!input.equals("Exit")) {
            String[] tokens = input.split("\\s+");

            switch (tokens[0]) {
                case "AddPlayer":
                    try {
                        System.out.println(managerController.addPlayer(tokens[1], tokens[2]));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "AddCard":
                    try {
                        System.out.println(managerController.addCard(tokens[1], tokens[2]));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "AddPlayerCard":
                    try {
                        System.out.println(managerController.addPlayerCard(tokens[1], tokens[2]));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "Fight":
                    try {
                        System.out.println(managerController.fight(tokens[1], tokens[2]));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "Report":
                    System.out.print(managerController.report());

                    break;
                default:
            }

            input = sc.nextLine();
        }

    }
}
