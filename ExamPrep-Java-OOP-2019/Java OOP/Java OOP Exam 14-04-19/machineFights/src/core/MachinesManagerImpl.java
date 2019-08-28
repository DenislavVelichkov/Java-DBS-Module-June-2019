package core;

import common.OutputMessages;
import core.interfaces.MachineFactory;
import core.interfaces.MachinesManager;
import core.interfaces.PilotFactory;
import entities.interfaces.Fighter;
import entities.interfaces.Machine;
import entities.interfaces.Pilot;
import entities.interfaces.Tank;

import java.util.Map;

public class MachinesManagerImpl implements MachinesManager {
    private PilotFactory pilotFactory;
    private MachineFactory machineFactory;
    private Map<String, Pilot> pilots;
    private Map<String, Machine> machines;
    
    public MachinesManagerImpl(
        PilotFactory pilotFactory,
        MachineFactory machineFactory,
        Map<String, Pilot> pilots,
        Map<String, Machine> machines) {

        this.pilotFactory = pilotFactory;
        this.machineFactory = machineFactory;
        this.pilots = pilots;
        this.machines = machines;
    }


    @Override
    public String hirePilot(String name) throws IllegalArgumentException {
        String outputMsg = "";

        if (!this.pilots.containsKey(name)) {
            this.pilots.put(name, this.pilotFactory.createPilot(name));
            outputMsg = String.format(OutputMessages.pilotHired, name);
        } else {
            throw new IllegalArgumentException(String.format(OutputMessages.pilotExists, name));
        }

        return outputMsg;
    }

    @Override
    public String manufactureTank(String name, double attackPoints, double defensePoints) throws IllegalArgumentException {
        String outputMsg = "";

        if (!this.machines.containsKey(name)) {
            this.machines.put(name, this.machineFactory.createTank(name, attackPoints, defensePoints));
            outputMsg = String.format(OutputMessages.tankManufactured, name, attackPoints, defensePoints);
        } else {
            throw new IllegalArgumentException(String.format(OutputMessages.machineExists, name));
        }
       
        return outputMsg;
    }

    @Override
    public String manufactureFighter(String name, double attackPoints, double defensePoints) throws IllegalArgumentException {
        String outputMassage = "";

        if (!this.machines.containsKey(name)) {
            this.machines.put(
                    name, this.machineFactory.createFighter(name, attackPoints, defensePoints));
            outputMassage = String.format(OutputMessages.fighterManufactured, name, attackPoints, defensePoints);
        } else {
            throw new IllegalArgumentException(String.format(OutputMessages.machineExists, name));
        }

        return outputMassage;
    }

    @Override
    public String engageMachine(String selectedPilotName, String selectedMachineName) throws IllegalArgumentException {
        if (this.machines.get(selectedMachineName).getPilot() != null) {
            throw new IllegalArgumentException(String.format(OutputMessages.machineHasPilotAlready, selectedMachineName));
        }


        this.machines.get(selectedMachineName).setPilot(this.pilots.get(selectedPilotName));
        this.pilots.get(selectedPilotName)
            .addMachine(this.machines.get(selectedMachineName));


        return String.format(OutputMessages.machineEngaged, selectedPilotName, selectedMachineName);
    }

    @Override
    public String attackMachines(String attackingMachineName, String defendingMachineName) throws IllegalArgumentException{
        String outputMassage = "";

        if (!this.machines.containsKey(attackingMachineName)) {
            outputMassage = String.format(OutputMessages.machineNotFound, attackingMachineName);
            throw new IllegalArgumentException(outputMassage);
        }
        if (!this.machines.containsKey(defendingMachineName)) {
            outputMassage = String.format(OutputMessages.machineNotFound, defendingMachineName);
            throw new IllegalArgumentException(outputMassage);
        }
        
        boolean areTheyManufactured =
            this.machines.containsKey(attackingMachineName)
                && this.machines.containsKey(defendingMachineName);
    
        if (areTheyManufactured) {
            this.machines.get(attackingMachineName).attack(defendingMachineName);
    
            if (this.machines.get(attackingMachineName).getAttackPoints() >
                    this.machines.get(defendingMachineName).getDefensePoints()) {
                this.machines.get(defendingMachineName)
                    .setHealthPoints(this.machines.get(defendingMachineName).getHealthPoints()
                                         - (this.machines.get(attackingMachineName).getAttackPoints()
                                                - this.machines.get(defendingMachineName).getDefensePoints()));
                if (this.machines.get(defendingMachineName).getHealthPoints() < 0) {
                    this.machines.get(defendingMachineName).setHealthPoints(0);
                }

            }
            
            outputMassage =
                String.format(
                    OutputMessages.attackSuccessful,
                    defendingMachineName,
                    attackingMachineName,
                    this.machines.get(defendingMachineName).getHealthPoints()
                );
        }
        
        return outputMassage;
    }

    @Override
    public String pilotReport(String pilotName) throws NullPointerException{
        if (!this.pilots.containsKey(pilotName)) {
            throw new NullPointerException(String.format(OutputMessages.pilotNotFound, pilotName));
        }

        return this.pilots.get(pilotName).report();
    }

    @Override
    public String toggleFighterAggressiveMode(String fighterName) {
        String outputMsg = String.format(OutputMessages.notSupportedOperation, fighterName);

        if (this.machines.get(fighterName) instanceof Fighter) {
            ((Fighter) this.machines.get(fighterName)).toggleAggressiveMode();
            outputMsg = String.format(OutputMessages.fighterOperationSuccessful, fighterName);
        }

        return outputMsg;
    }

    @Override
    public String toggleTankDefenseMode(String tankName) {
        String outputMsg = String.format(OutputMessages.notSupportedOperation, tankName);

        if (this.machines.get(tankName) instanceof Tank) {
            ((Tank) this.machines.get(tankName)).toggleDefenseMode();
            outputMsg = String.format(OutputMessages.tankOperationSuccessful, tankName);
        }
        
        return outputMsg;
    }
}
