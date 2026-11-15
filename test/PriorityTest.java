
import java.util.ArrayList;
import java.util.List;

import algorithms.Priority;
import model.Process;
import model.SchedulingResult;
import model.ExecutionSegment;

public class PriorityTest {

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 5, 3));
        processes.add(new Process("P2", 1, 3, 1));
        processes.add(new Process("P3", 2, 4, 2));
        processes.add(new Process("P4", 3, 2, 4));

        Priority priorityScheduling = new Priority();

        List<SchedulingResult> results =
                priorityScheduling.schedule(processes);

        // Verify number of results
        assert results.size() == 4 : "Incorrect number of results";

        // Verify P1
        SchedulingResult p1 = results.get(0);

        assert p1.getProcessId().equals("P1");
        assert p1.getCompletionTime() == 5 : "Incorrect P1 CT";
        assert p1.getTurnaroundTime() == 5 : "Incorrect P1 TAT";
        assert p1.getWaitingTime() == 0 : "Incorrect P1 WT";

        // Verify P2
        SchedulingResult p2 = results.get(1);

        assert p2.getProcessId().equals("P2");
        assert p2.getCompletionTime() == 8 : "Incorrect P2 CT";
        assert p2.getTurnaroundTime() == 7 : "Incorrect P2 TAT";
        assert p2.getWaitingTime() == 4 : "Incorrect P2 WT";

        // Verify P3
        SchedulingResult p3 = results.get(2);

        assert p3.getProcessId().equals("P3");
        assert p3.getCompletionTime() == 12 : "Incorrect P3 CT";
        assert p3.getTurnaroundTime() == 10 : "Incorrect P3 TAT";
        assert p3.getWaitingTime() == 6 : "Incorrect P3 WT";

        // Verify P4
        SchedulingResult p4 = results.get(3);

        assert p4.getProcessId().equals("P4");
        assert p4.getCompletionTime() == 14 : "Incorrect P4 CT";
        assert p4.getTurnaroundTime() == 11 : "Incorrect P4 TAT";
        assert p4.getWaitingTime() == 9 : "Incorrect P4 WT";

        // Verify execution order
        List<ExecutionSegment> segments =
                priorityScheduling.getExecutionSegments();

        assert segments.size() == 4 : "Incorrect number of execution segments";

        assert segments.get(0).getProcessId().equals("P1");
        assert segments.get(1).getProcessId().equals("P2");
        assert segments.get(2).getProcessId().equals("P3");
        assert segments.get(3).getProcessId().equals("P4");

        System.out.println("Priority Test Passed!");
    }
}
