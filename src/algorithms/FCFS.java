package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Process;
import model.SchedulingResult;

public class FCFS {

    public List<SchedulingResult> schedule(List<Process> processes) {

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

            // Wait if the next process has not arrived
            int startTime = Math.max(
                currentTime,
                process.getArrivalTime()
            );

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

            // Update CPU time
            currentTime = completionTime;
        }

        return results;
    }
}