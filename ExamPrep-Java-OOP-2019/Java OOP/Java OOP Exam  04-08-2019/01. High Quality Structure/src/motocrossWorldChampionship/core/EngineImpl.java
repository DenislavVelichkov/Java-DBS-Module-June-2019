package motocrossWorldChampionship.core;

import motocrossWorldChampionship.common.Command;
import motocrossWorldChampionship.core.interfaces.ChampionshipController;
import motocrossWorldChampionship.core.interfaces.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EngineImpl implements Engine {
    private ChampionshipController controller;
    private BufferedReader reader;

    public EngineImpl(ChampionshipController controller) {
        this.controller = controller;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String result = null;
            try {
                result = processInput();

                if (result.equals(Command.End.name())) {
                    break;
                }
            } catch (NullPointerException | IllegalArgumentException | IOException e) {
                result = e.getMessage();
            }

            System.out.println(result);
        }
    }

    private String processInput() throws IOException {
        String input = this.reader.readLine();
        String[] tokens = input.split("\\s+");

        Command command = Command.valueOf(tokens[0]);
        String result = null;
        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        switch (command) {
            case CreateRider:
                result = this.controller.createRider(data[0]);

                break;
            case CreateMotorcycle:
                result = this.controller.createMotorcycle(data[0], data[1], Integer.parseInt(data[2]));

                break;
            case AddMotorcycleToRider:
                result = this.controller.addMotorcycleToRider(data[0], data[1]);

                break;
            case AddRiderToRace:
                result = this.controller.addRiderToRace(data[0], data[1]);

                break;
            case CreateRace:
                result = this.controller.createRace(data[0], Integer.parseInt(data[1]));

                break;
            case StartRace:
                result = this.controller.startRace(data[0]);

                break;
            case End:
                result = Command.End.name();

                break;
        }

        return result;
    }
}
