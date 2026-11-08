
import java.util.ArrayList;
import java.util.List;

import engine.SchedulingEngine;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class SchedulingEngineTest {

    private static void displayAverageMetrics(List<SchedulingResult> results) {

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;

        for (SchedulingResult result : results) {
            totalWaitingTime += result.getWaitingTime();
            totalTurnaroundTime += result.getTurnaroundTime();
            totalResponseTime += result.getResponseTime();
        }

        int processCount = results.size();

        System.out.println("\nAverage Waiting Time: "
                + totalWaitingTime / processCount);

        System.out.println("Average Turnaround Time: "
                + totalTurnaroundTime / processCount);

        System.out.println("Average Response Time: "
                + totalResponseTime / processCount);
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 7, 3));
        processes.add(new Process("P2", 1, 4, 1));
        processes.add(new Process("P3", 2, 2, 4));
        processes.add(new Process("P4", 3, 1, 2));

        SchedulingEngine engine = new SchedulingEngine();

        List<SchedulingResult> results = engine.schedule(
                "FCFS",
                processes,
                2
        );
        
        System.out.println("FCFS Scheduling Test");
        System.out.println("--------------------");

        for (SchedulingResult result : results) {

            System.out.println(
                    result.getProcessId()
                    + " | CT: " + result.getCompletionTime()
                    + " | WT: " + result.getWaitingTime()
                    + " | TAT: " + result.getTurnaroundTime()
            );
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment : engine.getExecutionSegments()) {
            System.out.println(segment);
        }
        displayAverageMetrics(results);
        System.out.println("\n\nSJF Scheduling Test");
        System.out.println("-------------------");

        results = engine.schedule("SJF", processes, 2);

        for (SchedulingResult result : results) {
            System.out.println(
                    result.getProcessId()
                    + " | CT: " + result.getCompletionTime()
                    + " | WT: " + result.getWaitingTime()
                    + " | TAT: " + result.getTurnaroundTime()
            );
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment : engine.getExecutionSegments()) {
            System.out.println(segment);
        }
        displayAverageMetrics(results);
        System.out.println("\n\nSRTF Scheduling Test");
        System.out.println("--------------------");

        results = engine.schedule("SRTF", processes, 2);

        for (SchedulingResult result : results) {
            System.out.println(
                    result.getProcessId()
                    + " | CT: " + result.getCompletionTime()
                    + " | WT: " + result.getWaitingTime()
                    + " | TAT: " + result.getTurnaroundTime()
            );
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment : engine.getExecutionSegments()) {
            System.out.println(segment);
        }
        displayAverageMetrics(results);
        System.out.println("\n\nPriority Scheduling Test");
        System.out.println("------------------------");

        results = engine.schedule("PRIORITY", processes, 2);

        for (SchedulingResult result : results) {
            System.out.println(
                    result.getProcessId()
                    + " | CT: " + result.getCompletionTime()
                    + " | WT: " + result.getWaitingTime()
                    + " | TAT: " + result.getTurnaroundTime()
            );
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment : engine.getExecutionSegments()) {
            System.out.println(segment);
        }
        displayAverageMetrics(results);

        System.out.println("\n\nRound Robin Scheduling Test");
        System.out.println("---------------------------");

        results = engine.schedule("ROUND ROBIN", processes, 2);

        for (SchedulingResult result : results) {
            System.out.println(
                    result.getProcessId()
                    + " | CT: " + result.getCompletionTime()
                    + " | WT: " + result.getWaitingTime()
                    + " | TAT: " + result.getTurnaroundTime()
            );
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment : engine.getExecutionSegments()) {
            System.out.println(segment);
        }
        displayAverageMetrics(results);

    }
}
