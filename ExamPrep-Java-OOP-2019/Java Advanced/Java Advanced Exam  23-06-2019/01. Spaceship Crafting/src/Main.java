import java.util.*;
import java.util.stream.Collectors;

public class Main {
    enum AdvancedMaterial {
        Glass(25),
        Aluminium(50),
        Lithium(75),
        CarbonFiber(100);

        int value;

        AdvancedMaterial(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayDeque<Integer> chemicalLiquids = Arrays.stream(
                sc.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        ArrayDeque<Integer> physicalItems = Arrays.stream(
                sc.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        Map<String, Integer> listOfAdvancedMaterials = new HashMap<>();
        listOfAdvancedMaterials.putIfAbsent("aluminium", 0);
        listOfAdvancedMaterials.putIfAbsent("carbonfiber", 0);
        listOfAdvancedMaterials.putIfAbsent("glass", 0);
        listOfAdvancedMaterials.putIfAbsent("lithium", 0);

        while (!chemicalLiquids.isEmpty() && !physicalItems.isEmpty()) {

            int sumOfLiquidAndItem = chemicalLiquids.removeFirst() + physicalItems.peekLast();

            String advancedMaterialToAdd = checkIfAdvValueIsPresent(sumOfLiquidAndItem);

            if (advancedMaterialToAdd != null) {
                physicalItems.removeLast();
                listOfAdvancedMaterials.put(advancedMaterialToAdd.toLowerCase(),
                        listOfAdvancedMaterials.get(advancedMaterialToAdd.toLowerCase()) + 1);
            } else {
                int addedValue = physicalItems.removeLast() + 3;
                physicalItems.addLast(addedValue);
            }
        }

        if (checkRequiredMaterials(listOfAdvancedMaterials)) {
            System.out.println("Wohoo! You succeeded in building the spaceship!");
        } else {
            System.out.println("Ugh, what a pity! You didn't have enough materials to build the spaceship.");
        }

        StringBuilder sb = new StringBuilder();
        if (chemicalLiquids.size() == 0) {
            System.out.println("Liquids left: none");
        } else {
            for (Integer chemicalLiquid : chemicalLiquids) {
                sb.append(chemicalLiquid).append(", ");
            }

            String liquidsLeft = sb.toString().trim().substring(0, sb.length() - 2);
            System.out.println(String.format("Liquids left: %s", liquidsLeft));
        }
        if (physicalItems.size() == 0) {
            System.out.println("Physical items left: none");
        } else {
            while (!physicalItems.isEmpty()) {
                sb.append(physicalItems.removeLast()).append(", ");
            }

            String itemsLeft = sb.toString().trim().substring(0, sb.length() - 2);
            System.out.println(String.format("Physical items left: %s", itemsLeft));
        }

        StringBuilder finalSb = new StringBuilder();
        listOfAdvancedMaterials
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> {
                    if (entry.getKey().equalsIgnoreCase(AdvancedMaterial.Aluminium.toString())) {
                        finalSb.append(String.format("Aluminium: %d", entry.getValue()))
                        .append(System.lineSeparator());
                    }
                    if (entry.getKey().equalsIgnoreCase(AdvancedMaterial.Glass.toString())) {
                        finalSb.append(String.format("Glass: %d", entry.getValue()))
                                .append(System.lineSeparator());
                    }
                    if (entry.getKey().equalsIgnoreCase(AdvancedMaterial.CarbonFiber.toString())) {
                        finalSb.append(String.format("Carbon fiber: %d", entry.getValue()))
                                .append(System.lineSeparator());
                    }
                    if (entry.getKey().equalsIgnoreCase(AdvancedMaterial.Lithium.toString())) {
                        finalSb.append(String.format("Lithium: %d", entry.getValue()))
                                .append(System.lineSeparator());
                    }
                });

        System.out.println(sb.toString());
    }

    private static boolean checkRequiredMaterials(Map<String, Integer> listOfAdvancedMaterials) {
        boolean aluminiumPresent =
                listOfAdvancedMaterials.entrySet().stream()
                        .anyMatch(entry ->
                                entry.getKey().equalsIgnoreCase(AdvancedMaterial.Aluminium.toString().toLowerCase()) &&
                                        entry.getValue() != 0);

        boolean lithiumPresent =
                listOfAdvancedMaterials.entrySet().stream()
                        .anyMatch(entry ->
                                entry.getKey().equalsIgnoreCase(AdvancedMaterial.Lithium.toString().toLowerCase()) &&
                                        entry.getValue() != 0);
        boolean glassPresent =
                listOfAdvancedMaterials.entrySet().stream()
                        .anyMatch(entry ->
                                entry.getKey().equalsIgnoreCase(AdvancedMaterial.Glass.toString().toLowerCase()) &&
                                        entry.getValue() != 0);
        boolean carbonFiberPresent =
                listOfAdvancedMaterials.entrySet().stream()
                        .anyMatch(entry ->
                                entry.getKey().equalsIgnoreCase(AdvancedMaterial.CarbonFiber.toString().toLowerCase()) &&
                                        entry.getValue() != 0);

        return aluminiumPresent &&
                lithiumPresent &&
                glassPresent &&
                carbonFiberPresent;

    }

    private static String checkIfAdvValueIsPresent(int sumOfLiquidAndItem) {
        String result = null;
        if (AdvancedMaterial.Aluminium.getValue() == sumOfLiquidAndItem) {
            result = "aluminium";
        } else if (AdvancedMaterial.Glass.getValue() == sumOfLiquidAndItem) {
            result = "glass";
        } else if (AdvancedMaterial.CarbonFiber.getValue() == sumOfLiquidAndItem) {
            result = "carbonFiber";
        } else if (AdvancedMaterial.Lithium.getValue() == sumOfLiquidAndItem) {
            result = "lithium";
        }

        return result;
    }
}
