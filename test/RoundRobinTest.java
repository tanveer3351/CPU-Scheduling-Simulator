
import java.util.ArrayList;
import java.util.List;

import algorithms.RoundRobin;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class RoundRobinTest {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 5, 2));
        processes.add(new Process("P2", 1, 4, 1));
        processes.add(new Process("P3", 2, 2, 2));

        int timeQuantum = 2;

        RoundRobin roundRobin = new RoundRobin();

        List<SchedulingResult> results =
                roundRobin.schedule(processes, timeQuantum);

        // Verify number of results
        assert results.size() == 3 : "Incorrect number of results";

        // Verify P1
        SchedulingResult p1 = results.get(0);

        assert p1.getProcessId().equals("P1");
        assert p1.getCompletionTime() == 11 : "Incorrect P1 CT";
        assert p1.getTurnaroundTime() == 11 : "Incorrect P1 TAT";
        assert p1.getWaitingTime() == 6 : "Incorrect P1 WT";
        assert p1.getResponseTime() == 0 : "Incorrect P1 RT";

        // Verify P2
        SchedulingResult p2 = results.get(1);

        assert p2.getProcessId().equals("P2");
        assert p2.getCompletionTime() == 10 : "Incorrect P2 CT";
        assert p2.getTurnaroundTime() == 9 : "Incorrect P2 TAT";
        assert p2.getWaitingTime() == 5 : "Incorrect P2 WT";
        assert p2.getResponseTime() == 1 : "Incorrect P2 RT";

        // Verify P3
        SchedulingResult p3 = results.get(2);

        assert p3.getProcessId().equals("P3");
        assert p3.getCompletionTime() == 6 : "Incorrect P3 CT";
        assert p3.getTurnaroundTime() == 4 : "Incorrect P3 TAT";
        assert p3.getWaitingTime() == 2 : "Incorrect P3 WT";
        assert p3.getResponseTime() == 2 : "Incorrect P3 RT";

        // Verify execution order
        List<ExecutionSegment> segments =
                roundRobin.getExecutionSegments();

        assert segments.size() == 6 : "Incorrect number of execution segments";

        assert segments.get(0).getProcessId().equals("P1");
        assert segments.get(1).getProcessId().equals("P2");
        assert segments.get(2).getProcessId().equals("P3");
        assert segments.get(3).getProcessId().equals("P1");
        assert segments.get(4).getProcessId().equals("P2");
        assert segments.get(5).getProcessId().equals("P1");

        System.out.println("Round Robin Test Passed!");
    }
}
