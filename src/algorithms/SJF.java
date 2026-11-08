package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class SJF {

    private List<ExecutionSegment> executionSegments
            = new ArrayList<>();

    public List<SchedulingResult> schedule(List<Process> processes) {

        executionSegments.clear();

        // Copy the original process list
        List<Process> pendingProcesses = new ArrayList<>(processes);

        // Sort processes by arrival time
        pendingProcesses.sort(
                Comparator.comparingInt(Process::getArrivalTime)
        );

        List<SchedulingResult> results = new ArrayList<>();


        int currentTime = 0;

        // Continue until all processes are completed
        while (!pendingProcesses.isEmpty()) {

            Process selectedProcess = null;

            // Find the shortest available process
            for (Process process : pendingProcesses) {

                if (process.getArrivalTime() <= currentTime) {

                    if (selectedProcess == null
                            || process.getBurstTime() < selectedProcess.getBurstTime()) {

                        selectedProcess = process;
                    }
                }
            }

            // If no process has arrived, CPU remains idle
            if (selectedProcess == null) {

                int nextArrivalTime
                        = pendingProcesses.get(0).getArrivalTime();

                executionSegments.add(
                        new ExecutionSegment(
                                "IDLE",
                                currentTime,
                                nextArrivalTime
                        )
                );

                currentTime = nextArrivalTime;
                continue;
            }

            // Calculate execution times
            int startTime = currentTime;

            int completionTime
                    = startTime + selectedProcess.getBurstTime();

            // Store scheduling result
            SchedulingResult result = new SchedulingResult(
                    selectedProcess.getProcessId(),
                    selectedProcess.getArrivalTime(),
                    selectedProcess.getBurstTime(),
                    startTime,
                    completionTime
            );

            results.add(result);

            // Store execution segment
            executionSegments.add(
                    new ExecutionSegment(
                            selectedProcess.getProcessId(),
                            startTime,
                            completionTime
                    )
            );

            // Update current CPU time
            currentTime = completionTime;

            // Remove completed process
            pendingProcesses.remove(selectedProcess);
        }

        return results;
    }

    // Return CPU execution segments
    public List<ExecutionSegment> getExecutionSegments() {
    return new ArrayList<>(executionSegments);
    }
}
