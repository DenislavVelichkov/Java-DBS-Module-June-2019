package JavaAdvanced.DemoExam_17_02_19;

import java.util.*;
import java.util.stream.Collectors;

public class Socks_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Deque<Integer> leftSock = Arrays.stream(sc.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        Deque<Integer> rightSock = Arrays.stream(sc.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(ArrayDeque::new));

        ArrayList<Integer> result = new ArrayList<>();

        while (leftSock.size() != 0 && rightSock.size() != 0) {
            int firstSock = leftSock.peekLast();
            int secondSock = rightSock.peekFirst();

            if (firstSock > secondSock) {
                result.add(firstSock + secondSock);
                leftSock.pollLast();
                rightSock.pollFirst();
            } else if (firstSock == secondSock) {
                rightSock.pollFirst();
                int numToAdd = leftSock.pollLast() + 1;
                leftSock.addLast(numToAdd);
            } else {
                leftSock.pollLast();
            }

        }

        int maxElement = result.stream().max(Integer::compareTo).get();
        System.out.println(maxElement);
        for (Integer integer : result) {
            System.out.print(integer + " ");
        }

    }
}