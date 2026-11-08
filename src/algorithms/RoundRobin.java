
package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class RoundRobin {

    private List<ExecutionSegment> executionSegments =
            new ArrayList<>();

    public List<SchedulingResult> schedule(
            List<Process> processes, int timeQuantum) {

        executionSegments.clear();

        if (timeQuantum <= 0) {
            throw new IllegalArgumentException(
                    "Time quantum must be greater than zero."
            );
        }

        List<Process> sortedProcesses =
                new ArrayList<>(processes);

        sortedProcesses.sort(
                Comparator.comparingInt(Process::getArrivalTime)
        );

        List<SchedulingResult> results = new ArrayList<>();

        int processCount = sortedProcesses.size();

        int[] remainingTime = new int[processCount];
        int[] firstStartTime = new int[processCount];
        int[] completionTime = new int[processCount];

        boolean[] hasStarted = new boolean[processCount];

        for (int i = 0; i < processCount; i++) {
            remainingTime[i] = sortedProcesses.get(i).getBurstTime();
        }

        Queue<Integer> readyQueue = new LinkedList<>();

        int currentTime = 0;
        int nextProcessIndex = 0;

        while (nextProcessIndex < processCount
                || !readyQueue.isEmpty()) {

            if (readyQueue.isEmpty()) {

                int nextArrivalTime =
                        sortedProcesses.get(nextProcessIndex)
                                .getArrivalTime();

                if (currentTime < nextArrivalTime) {

                    executionSegments.add(
                            new ExecutionSegment(
                                    "IDLE",
                                    currentTime,
                                    nextArrivalTime
                            )
                    );

                    currentTime = nextArrivalTime;
                }

                readyQueue.add(nextProcessIndex);
                nextProcessIndex++;
            }

            int selectedIndex = readyQueue.poll();

            Process selectedProcess =
                    sortedProcesses.get(selectedIndex);

            if (!hasStarted[selectedIndex]) {
                firstStartTime[selectedIndex] = currentTime;
                hasStarted[selectedIndex] = true;
            }

            int executionTime = Math.min(
                    timeQuantum,
                    remainingTime[selectedIndex]
            );

            int startTime = currentTime;

            currentTime += executionTime;

            remainingTime[selectedIndex] -= executionTime;

            executionSegments.add(
                    new ExecutionSegment(
                            selectedProcess.getProcessId(),
                            startTime,
                            currentTime
                    )
            );

            while (nextProcessIndex < processCount
                    && sortedProcesses.get(nextProcessIndex)
                            .getArrivalTime() <= currentTime) {

                readyQueue.add(nextProcessIndex);
                nextProcessIndex++;
            }

            if (remainingTime[selectedIndex] > 0) {
                readyQueue.add(selectedIndex);
            } else {
                completionTime[selectedIndex] = currentTime;
            }
        }

        for (int i = 0; i < processCount; i++) {

            Process process = sortedProcesses.get(i);

            results.add(
                    new SchedulingResult(
                            process.getProcessId(),
                            process.getArrivalTime(),
                            process.getBurstTime(),
                            firstStartTime[i],
                            completionTime[i]
                    )
            );
        }

        return results;
    }

    public List<ExecutionSegment> getExecutionSegments() {
        return new ArrayList<>(executionSegments);
    }
}
