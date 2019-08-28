package entities;

import entities.interfaces.Machine;
import entities.interfaces.Pilot;

import java.util.ArrayList;
import java.util.List;

public class PilotImpl implements Pilot{
    private String name;
    private List<Machine> machines;

    public PilotImpl(String name) {
        this.setName(name);
        this.machines = new ArrayList<>();
    }
    
    private void setName(String name) throws IllegalArgumentException{
        if (name == null || name.equals("null") || name.equals("") || name.equals(" ")) {
            throw new IllegalArgumentException("Pilot name cannot be null or empty string.");
        }
    
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void addMachine(Machine machine) throws NullPointerException{
        if (machine == null) {
            throw new NullPointerException("Null machine cannot be added to the pilot.");
        }
        
        this.machines.add(machine);
    }
    
    @Override
    public List<Machine> getMachines() {
        return this.machines;
    }
    
    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - %d machines", this.getName(), this.getMachines().size()))
            .append(System.lineSeparator());
        this.machines.forEach(m -> sb.append(m.toString()));

        if (this.machines.size() == 0) {
            return sb.toString().substring(0, sb.length() - 2);
        }

        return sb.toString();
    }
}
