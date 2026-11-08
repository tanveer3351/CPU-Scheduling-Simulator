import model.ExecutionSegment;

public class ExecutionSegmentTest {

    public static void main(String[] args) {

        ExecutionSegment segment =
                new ExecutionSegment("P1", 0, 3);

        System.out.println("Process ID: " + segment.getProcessId());
        System.out.println("Start Time: " + segment.getStartTime());
        System.out.println("End Time: " + segment.getEndTime());
        System.out.println("Duration: " + segment.getDuration());

        System.out.println("\nExecution Segment:");
        System.out.println(segment);
    }
}