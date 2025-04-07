import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Process> listOfProcesses = Process.generateProcesses(80);

        ArrayList<Process> processesForFCFS = new ArrayList<>(listOfProcesses);
        ArrayList<Process> processesForSSTF = new ArrayList<>(listOfProcesses);
        ArrayList<Process> processesForSCAN = new ArrayList<>(listOfProcesses);
        ArrayList<Process> processesForCSCAN = new ArrayList<>(listOfProcesses);

        Disk disk = new Disk(200, 56);
        System.out.println("Disk properties (Size: " + disk.size + " | Init Head Position: " + disk.headInit + ")");
        disk.FCFS(processesForFCFS);
        disk.SSTF(processesForSSTF);
        disk.SCAN(processesForSCAN, 1);
        disk.cSCAN(processesForCSCAN, 0);

        System.out.println("\n==== Starting EDF simulation ====");
        disk.dynamicEDF(500);
    }
}
