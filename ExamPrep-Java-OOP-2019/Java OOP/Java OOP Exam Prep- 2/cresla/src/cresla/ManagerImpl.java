package cresla;

import cresla.entities.*;
import cresla.interfaces.*;
import cresla.interfaces.Module;

import java.util.ArrayList;
import java.util.List;

public class ManagerImpl implements Manager {
    private static int ID;
    private List<Reactor> reactors;
    private List<Module> modules;

    public ManagerImpl() {
        ManagerImpl.ID = 1;
        this.reactors = new ArrayList<>();
        this.modules = new ArrayList<>();
    }

    @Override
    public String reactorCommand(List<String> arguments) {
        String reactorName = arguments.get(0);
        int additionalParameter = Integer.parseInt(arguments.get(1));
        int maxCapacity = Integer.parseInt(arguments.get(2));
        Reactor reactor = null;

        switch (reactorName) {
            case "Cryo":
                reactor = new CryoReactor(ID, additionalParameter, maxCapacity);
                break;
            case "Heat":
                reactor = new HeatReactor(ID, additionalParameter, maxCapacity);
                break;
        }

        this.reactors.add(reactor);

        return String.format("Created %s Reactor - %d", reactorName, ID++);
    }

    @Override
    public String moduleCommand(List<String> arguments) {
        int idOfTheReactorToBeAdded = Integer.parseInt(arguments.get(0));
        String moduleType = arguments.get(1);
        int additionalParameter = Integer.parseInt(arguments.get(2));
        Module module = null;

        switch (moduleType) {
            case "CryogenRod":
                module = new CryogenRod(ID, additionalParameter);
                break;
            case "HeatProcessor":
                module = new HeatProcessor(ID, additionalParameter);
                break;
            case "CooldownSystem":
                module = new CooldownSystem(ID, additionalParameter);
                break;
        }

        for (Reactor reactor : this.reactors) {
            if (reactor.getId() == idOfTheReactorToBeAdded) {
                String moduleClass = module.getClass().getSuperclass().getSimpleName();

                if (moduleClass.equals("BaseEnergyModule")) {
                    reactor.addEnergyModule((EnergyModule) module);
                } else {
                    reactor.addAbsorbingModule((AbsorbingModule) module);
                }

                break;
            }

        }

        this.modules.add(module);
        return String.format("Added %s - %d to Reactor - %d", moduleType, ID++, idOfTheReactorToBeAdded);
    }

    @Override
    public String reportCommand(List<String> arguments) {
        int idToFind = Integer.parseInt(arguments.get(0));

        for (Reactor reactor : this.reactors) {
            if (reactor.getId() == idToFind) {
                return reactor.toString();
            }
        }

        for (Module module : this.modules) {
            if (module.getId() == idToFind) {
                return module.toString();
            }
        }

        return "";
    }

    @Override
    public String exitCommand(List<String> arguments) throws NoSuchFieldException, IllegalAccessException {

        long cryoReactorsCount = this.reactors
                                     .stream()
                                     .filter(rtr -> rtr.getClass().getSimpleName().equals("CryoReactor"))
                                     .count();

        long heatReactorsCount = this.reactors
                                     .stream()
                                     .filter(rtr -> rtr.getClass().getSimpleName().equals("HeatReactor"))
                                     .count();

        long energyModulesCount = this.modules
                                      .stream()
                                      .filter(rtr -> rtr.getClass().getSuperclass().getSimpleName().equals("BaseEnergyModule"))
                                      .count();

        long absorbingModulesCount = this.modules
                                         .stream()
                                         .filter(rtr -> rtr.getClass().getSuperclass().getSimpleName().equals("BaseHeatAbsorbingModule"))
                                         .count();

        long totalEnergyOutput = 0L;
        for (Reactor reactor : this.reactors) {
            long energyOutput = reactor.getTotalEnergyOutput();
            totalEnergyOutput += energyOutput;
        }

        long totalHeatAbsorbing = this.reactors
                                      .stream()
                                      .mapToLong(Reactor::getTotalHeatAbsorbing).sum();

        StringBuilder sb = new StringBuilder();
        sb.append("Cryo Reactors: ").append(cryoReactorsCount).append(System.lineSeparator());
        sb.append("Heat Reactors: ").append(heatReactorsCount).append(System.lineSeparator());
        sb.append("Energy Modules: ").append(energyModulesCount).append(System.lineSeparator());
        sb.append("Absorbing Modules: ").append(absorbingModulesCount).append(System.lineSeparator());
        sb.append("Total Energy Output: ").append(totalEnergyOutput).append(System.lineSeparator());
        sb.append("Total Heat Absorbing: ").append(totalHeatAbsorbing);

        return sb.toString();
    }
}
