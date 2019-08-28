import core.MachineFactoryImpl;
import core.MachinesManagerImpl;
import core.PilotFactoryImpl;
import core.interfaces.MachineFactory;
import core.interfaces.MachinesManager;
import core.interfaces.PilotFactory;
import entities.interfaces.Machine;
import entities.interfaces.Pilot;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        PilotFactory pilotFactory = new PilotFactoryImpl();
        MachineFactory machineFactory = new MachineFactoryImpl();

        Map<String, Pilot> pilots = new LinkedHashMap<>();
        Map<String, Machine> machines = new LinkedHashMap<>();

        MachinesManager machinesManager = new MachinesManagerImpl(
            pilotFactory, machineFactory, pilots, machines);

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equalsIgnoreCase("Over")) {
            String[] tokens = input.split(" ");

            switch (tokens[0]) {
                case "Hire":
                    try {
                        System.out.println(machinesManager.hirePilot(tokens[1]));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "Report":
                    try {
                        System.out.println(machinesManager.pilotReport(tokens[1]));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "ManufactureTank":
                    try {
                        System.out.println(
                            machinesManager.manufactureTank(tokens[1],
                                Double.parseDouble(tokens[2]),
                                Double.parseDouble(tokens[3])));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "ManufactureFighter":
                    try {
                        System.out.println(
                            machinesManager.manufactureFighter(tokens[1],
                                Double.parseDouble(tokens[2]),
                                Double.parseDouble(tokens[3])));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "Engage":
                    try {
                        System.out.println(machinesManager.engageMachine(tokens[1], tokens[2]));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "Attack":
                    try {
                        System.out.println(machinesManager.attackMachines(tokens[1], tokens[2]));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "DefenseMode":
                    try {
                        System.out.println(machinesManager.toggleTankDefenseMode(tokens[1]));
                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "AggressiveMode":
                    try {
                        System.out.println(machinesManager.toggleFighterAggressiveMode(tokens[1]));

                    } catch (IllegalArgumentException | NullPointerException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
            }

            input = sc.nextLine();
        }

    }
}
