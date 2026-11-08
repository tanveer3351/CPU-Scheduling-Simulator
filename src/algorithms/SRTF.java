package algorithms;

import java.util.ArrayList;
import java.util.List;

import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class SRTF {

    private List<ExecutionSegment> executionSegments =
            new ArrayList<>();

    public List<SchedulingResult> schedule(List<Process> processes) {

        executionSegments.clear();

        List<Process> processList = new ArrayList<>(processes);

        int processCount = processList.size();

        int[] remainingTime = new int[processCount];
        int[] startTime = new int[processCount];
        int[] completionTime = new int[processCount];

        boolean[] hasStarted = new boolean[processCount];

        for (int i = 0; i < processCount; i++) {
            remainingTime[i] = processList.get(i).getBurstTime();
        }

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < processCount) {

            int selectedProcess = -1;

            // Find the arrived process with the shortest remaining time
            for (int i = 0; i < processCount; i++) {

                Process process = processList.get(i);

                if (process.getArrivalTime() <= currentTime
                        && remainingTime[i] > 0) {

                    if (selectedProcess == -1
                            || remainingTime[i] <
                            remainingTime[selectedProcess]) {

                        selectedProcess = i;
                    }
                }
            }

            // Handle CPU idle time
            if (selectedProcess == -1) {

                if (!executionSegments.isEmpty()
                        && executionSegments.get(
                                executionSegments.size() - 1)
                                .getProcessId().equals("IDLE")) {

                    ExecutionSegment lastSegment =
                            executionSegments.remove(
                                    executionSegments.size() - 1);

                    executionSegments.add(
                            new ExecutionSegment(
                                    "IDLE",
                                    lastSegment.getStartTime(),
                                    currentTime + 1
                            )
                    );

                } else {

                    executionSegments.add(
                            new ExecutionSegment(
                                    "IDLE",
                                    currentTime,
                                    currentTime + 1
                            )
                    );
                }

                currentTime++;
                continue;
            }

            Process process = processList.get(selectedProcess);

            // Record the first time the process gets CPU
            if (!hasStarted[selectedProcess]) {

                startTime[selectedProcess] = currentTime;
                hasStarted[selectedProcess] = true;
            }

            // Record CPU execution
            if (!executionSegments.isEmpty()) {

                ExecutionSegment lastSegment =
                        executionSegments.get(
                                executionSegments.size() - 1);

                if (lastSegment.getProcessId().equals(
                        process.getProcessId())
                        && lastSegment.getEndTime() == currentTime) {

                    executionSegments.remove(
                            executionSegments.size() - 1);

                    executionSegments.add(
                            new ExecutionSegment(
                                    process.getProcessId(),
                                    lastSegment.getStartTime(),
                                    currentTime + 1
                            )
                    );

                } else {

                    executionSegments.add(
                            new ExecutionSegment(
                                    process.getProcessId(),
                                    currentTime,
                                    currentTime + 1
                            )
                    );
                }

            } else {

                executionSegments.add(
                        new ExecutionSegment(
                                process.getProcessId(),
                                currentTime,
                                currentTime + 1
                        )
                );
            }

            // Execute for one time unit
            remainingTime[selectedProcess]--;
            currentTime++;

            // Check whether the process has completed
            if (remainingTime[selectedProcess] == 0) {

                completionTime[selectedProcess] = currentTime;
                completedProcesses++;
            }
        }

        // Generate final scheduling results
        List<SchedulingResult> results = new ArrayList<>();

        for (int i = 0; i < processCount; i++) {

            Process process = processList.get(i);

            results.add(
                    new SchedulingResult(
                            process.getProcessId(),
                            process.getArrivalTime(),
                            process.getBurstTime(),
                            startTime[i],
                            completionTime[i]
                    )
            );
        }

        return results;
    }

    // Return CPU execution segments
    public List<ExecutionSegment> getExecutionSegments() {
        return new ArrayList<>(executionSegments);
    }
}
