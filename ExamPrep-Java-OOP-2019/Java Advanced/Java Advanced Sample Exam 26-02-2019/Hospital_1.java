package JavaAdvanced.ExamPrep_24;

import java.sql.SQLOutput;
import java.util.*;

public class Hospital_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        Map<String, String[][]> departments = new HashMap<>();
        Map<String, TreeSet<String>> doctorsAndPatients = new HashMap<>();

        while (!input.equals("Output")) {
            String[] tokens = input.split("\\s+");
            String department = tokens[0];
            String doctor = tokens[1] + " " + tokens[2];
            String patient = tokens[3];

            if (!departments.containsKey(department)) {
                departments.put(department, new String[20][3]);
            }

            boolean patientAdded = false;
            for (int row = 0; row < departments.get(department).length; row++) {
                for (int col = 0; col < departments.get(department)[row].length; col++) {
                    if (departments.get(department)[row][col] == null) {
                        departments.get(department)[row][col] = patient;
                        patientAdded = true;
                        break;
                    }
                }
                if (patientAdded) {
                    break;
                }
            }

            if (!doctorsAndPatients.containsKey(doctor)) {
                doctorsAndPatients.put(doctor, new TreeSet<>());
            }
            if (patientAdded) {
                doctorsAndPatients.get(doctor).add(patient);
            }


            input = sc.nextLine();
        }
        
        input = sc.nextLine();

        while (!input.equals("End")) {
            String[] tokens = input.split("\\s+");

            if (tokens.length == 1) {
                String department = tokens[0];
                Arrays.stream(departments.get(department)).forEach(e -> {
                    Arrays.stream(e).filter(Objects::nonNull).forEach(System.out::println);
                });
            } else if (tokens.length == 2) {
                if (Character.isDigit(tokens[1].charAt(0))) {
                    String department = tokens[0];
                    int room = Integer.parseInt(tokens[1]) - 1;
                    String[] patients = departments.get(department)[room];
                    Arrays.stream(patients).filter(Objects::nonNull).sorted(String::compareTo)
                            .forEach(System.out::println);
                } else {
                    String doctor = tokens[0] + " " + tokens[1];

                    doctorsAndPatients.get(doctor).forEach(System.out::println);
                }
            }

            input = sc.nextLine();

        }
    }
}

