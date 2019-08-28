package cresla;

import cresla.interfaces.Manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String input = sc.readLine();
        Manager manager = new ManagerImpl();

        while (!input.equalsIgnoreCase("Exit")) {
            String[] tokens = input.split("\\s+");

            if (tokens[0].equalsIgnoreCase("Reactor")) {
                System.out.println(manager.reactorCommand(Arrays.asList(tokens).subList(1, tokens.length)));
            } else if (tokens[0].equalsIgnoreCase("Module")) {
                System.out.println(manager.moduleCommand(Arrays.asList(tokens).subList(1, tokens.length)));
            } else if (tokens[0].equalsIgnoreCase("Report")) {
                System.out.println(manager.reportCommand(Arrays.asList(tokens).subList(1, tokens.length)));
            }

            input = sc.readLine();
        }

        try {
            System.out.println(manager.exitCommand(new ArrayList<>()));
        } catch (Exception e) {
        }

    }
}
