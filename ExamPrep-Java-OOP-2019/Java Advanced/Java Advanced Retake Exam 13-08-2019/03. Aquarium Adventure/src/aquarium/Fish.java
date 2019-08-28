package aquarium;

public class Fish {
    private String name;
    private String color;
    private int fins;

    public Fish(String name, String color, int fins) {
        this.name = name;
        this.color = color;
        this.fins = fins;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getFins() {
        return fins;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Fish: %s", this.name)).append(System.lineSeparator())
            .append(String.format("Color: %s", this.color)).append(System.lineSeparator())
            .append(String.format("Number of fins: %d", this.fins));

        return sb.toString();
    }
}
