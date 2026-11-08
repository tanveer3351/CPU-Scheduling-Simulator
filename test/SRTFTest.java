
import java.util.ArrayList;
import java.util.List;

import algorithms.SRTF;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class SRTFTest {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        // processes.add(new Process("P1", 0, 8, 1));
        // processes.add(new Process("P2", 1, 4, 2));
        // processes.add(new Process("P3", 2, 2, 3));
        // processes.add(new Process("P4", 3, 1, 4));

        processes.add(new Process("P1", 2, 3, 1));
        processes.add(new Process("P2", 8, 2, 2));

        SRTF srtf = new SRTF();

        List<SchedulingResult> results = srtf.schedule(processes);

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        System.out.println("SRTF Scheduling Results");
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
        }

        System.out.println("\nExecution Segments:");

        for (ExecutionSegment segment : srtf.getExecutionSegments()) {
            System.out.println(segment);
        }

        System.out.println("\nAverage Waiting Time: "
                + totalWaitingTime / results.size());

        System.out.println("Average Turnaround Time: "
                + totalTurnaroundTime / results.size());
    }
}
