package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Locale;

import algorithms.FCFS;
import algorithms.SJF;
import algorithms.SRTF;
import algorithms.Priority;
import algorithms.RoundRobin;

import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class SchedulingEngine {

    private List<SchedulingResult> schedulingResults;
    private List<ExecutionSegment> executionSegments;

    public SchedulingEngine() {
        schedulingResults = new java.util.ArrayList<>();
        executionSegments = new java.util.ArrayList<>();
    }

    private void validateProcesses(List<Process> processes) {

        Set<String> processIds = new HashSet<>();

        for (Process process : processes) {

            if (process == null) {
                throw new IllegalArgumentException(
                        "Process list cannot contain null entries."
                );
            }

            String processId = process.getProcessId().trim();

            if (processId.equalsIgnoreCase("IDLE")) {
                throw new IllegalArgumentException(
                        "Process ID 'IDLE' is reserved for CPU idle time."
                );
            }

            if (!processIds.add(processId.toLowerCase(Locale.ROOT))) {
                throw new IllegalArgumentException(
                        "Duplicate process ID found: " + processId
                );
            }
        }
    }

    public List<SchedulingResult> schedule(
            String algorithmName,
            List<Process> processes,
            int timeQuantum) {

        // Validate process input
        if (processes == null || processes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Process list cannot be null or empty."
            );
        }

        validateProcesses(processes);

        // Clear previous scheduling data
        schedulingResults.clear();
        executionSegments.clear();

        if (algorithmName == null || algorithmName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Algorithm name cannot be empty."
            );
        }

        // Select the scheduling algorithm
        switch (algorithmName.toUpperCase()) {

            case "FCFS":

                FCFS fcfs = new FCFS();

                schedulingResults = fcfs.schedule(processes);

                executionSegments = fcfs.getExecutionSegments();

                break;

            case "SJF":

                SJF sjf = new SJF();

                schedulingResults = sjf.schedule(processes);

                executionSegments = sjf.getExecutionSegments();

                break;

            case "SRTF":

                SRTF srtf = new SRTF();

                schedulingResults = srtf.schedule(processes);

                executionSegments = srtf.getExecutionSegments();

                break;

            case "PRIORITY":

                Priority priority = new Priority();

                schedulingResults = priority.schedule(processes);

                executionSegments = priority.getExecutionSegments();

                break;

            case "ROUND ROBIN":

                RoundRobin roundRobin = new RoundRobin();

                schedulingResults = roundRobin.schedule(
                        processes,
                        timeQuantum
                );

                executionSegments = roundRobin.getExecutionSegments();

                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid scheduling algorithm: " + algorithmName
                );
        }

        return schedulingResults;
    }

    public List<SchedulingResult> getSchedulingResults() {
        return new ArrayList<>(schedulingResults);
    }

    public List<ExecutionSegment> getExecutionSegments() {
        return new ArrayList<>(executionSegments);
    }
}
