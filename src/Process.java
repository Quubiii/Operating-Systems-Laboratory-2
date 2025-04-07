import java.util.ArrayList;
import java.util.Random;

public class Process {
    public final int arrivalTime;
    public final int track;
    public final int deadline;

    public Process(int arrivalTime, int track, int deadline) {
        this.arrivalTime = arrivalTime;
        this.track = track;
        this.deadline = deadline;
    }

    /**
     * Metoda generuje początkową listę procesów.
     * Dla symulacji dynamicznej używana jest metoda dynamicEDF,
     * więc ta metoda pozostaje do testów algorytmów statycznych.
     */
    public static ArrayList<Process> generateProcesses(int n) {
        Random random = new Random();
        ArrayList<Process> listOfProcesses = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int randomArrivalTime = random.nextInt(200) + 1;
            int randomTrack = random.nextInt(200);
            int randomDeadlineOffset = random.nextInt(81) + 20; // deadline = arrival + [20, 100]
            int randomDeadline = randomArrivalTime + randomDeadlineOffset;
            Process generatedProcess = new Process(randomArrivalTime, randomTrack, randomDeadline);
            listOfProcesses.add(generatedProcess);
        }
        System.out.println("========== START LIST OF PROCESSES ==========");
        for (Process process : listOfProcesses) {
            System.out.println("Process arrival time: " + process.arrivalTime
                    + ", track: " + process.track + ", deadline: " + process.deadline);
        }
        System.out.println("========== END LIST OF PROCESSES ==========");
        return listOfProcesses;
    }
}
