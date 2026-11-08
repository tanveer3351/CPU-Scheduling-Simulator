package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.ExecutionSegment;
import model.Process;
import model.SchedulingResult;

public class FCFS {

    private final List<ExecutionSegment> executionSegments = new ArrayList<>();

    public List<SchedulingResult> schedule(List<Process> processes) {

        // Clear previous execution data
        executionSegments.clear();

        // Create a copy so the original list is not modified
        List<Process> sortedProcesses = new ArrayList<>(processes);

        // Sort processes according to arrival time
        sortedProcesses.sort(
            Comparator.comparingInt(Process::getArrivalTime)
        );

        List<SchedulingResult> results = new ArrayList<>();

        // Initially, CPU time is zero
        int currentTime = 0;

        // Execute processes one by one
        for (Process process : sortedProcesses) {

            // Calculate process start time
            int startTime = Math.max(
                currentTime,
                process.getArrivalTime()
            );

            // Record CPU idle time if necessary
            if (startTime > currentTime) {
                executionSegments.add(
                    new ExecutionSegment(
                        "IDLE",
                        currentTime,
                        startTime
                    )
                );
            }

            // Calculate completion time
            int completionTime = startTime + process.getBurstTime();

            // Store scheduling results
            SchedulingResult result = new SchedulingResult(
                process.getProcessId(),
                process.getArrivalTime(),
                process.getBurstTime(),
                startTime,
                completionTime
            );

            results.add(result);

            // Record process execution
            executionSegments.add(
                new ExecutionSegment(
                    process.getProcessId(),
                    startTime,
                    completionTime
                )
            );

            // Update CPU time
            currentTime = completionTime;
        }

        return results;
    }

    // Return a copy of execution segments
    public List<ExecutionSegment> getExecutionSegments() {
        return new ArrayList<>(executionSegments);
    }
}
