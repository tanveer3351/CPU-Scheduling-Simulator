import model.Process;
public class Main {

    public static void main(String[] args) {

        // Create processes
        Process p1 = new Process("P1", 0, 5, 1);
        Process p2 = new Process("P2", 1, 3, 2);
        Process p3 = new Process("P3", 2, 8, 3);

        // Display process information
        System.out.println("CPU Scheduling Simulator");
        System.out.println("-------------------------");

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
    }
}
