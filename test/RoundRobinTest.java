
import java.util.ArrayList;
import java.util.List;

import algorithms.RoundRobin;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class RoundRobinTest {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        // processes.add(new Process("P1", 0, 5, 3));
        // processes.add(new Process("P2", 1, 4, 1));
        // processes.add(new Process("P3", 2, 2, 2));
        // int timeQuantum = 2;
        processes.add(new Process("P1", 2, 3, 2));
        processes.add(new Process("P2", 8, 2, 1));

        int timeQuantum = 2;

        RoundRobin roundRobin = new RoundRobin();

        List<SchedulingResult> results
                = roundRobin.schedule(processes, timeQuantum);

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        double totalResponseTime = 0;

        System.out.println("Round Robin Scheduling Results");
        System.out.println("--------------------------------");

        for (SchedulingResult result : results) {

            System.out.println(
                    result.getProcessId()
                    + " | AT: " + result.getArrivalTime()
                    + " | BT: " + result.getBurstTime()
                    + " | CT: " + result.getCompletionTime()
                    + " | WT: " + result.getWaitingTime()
                    + " | TAT: " + result.getTurnaroundTime()
                    + " | RT: " + result.getResponseTime()
            );

            totalWaitingTime += result.getWaitingTime();
            totalTurnaroundTime += result.getTurnaroundTime();
            totalResponseTime += result.getResponseTime();
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment
                : roundRobin.getExecutionSegments()) {

            System.out.println(segment);
        }

        System.out.println("\nAverage Waiting Time: "
                + totalWaitingTime / results.size());

        System.out.println("Average Turnaround Time: "
                + totalTurnaroundTime / results.size());

        System.out.println("Average Response Time: "
                + totalResponseTime / results.size());
    }
}
