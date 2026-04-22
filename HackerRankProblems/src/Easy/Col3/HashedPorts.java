package Easy.Col3;

import java.util.ArrayList;
import java.util.List;

public class HashedPorts {
    public static List<Integer> sentTimes(int numberOfPorts, int transmissionTime, List<Integer> packetIds) {
        int n = packetIds.size();
        int[] freeTime = new int[numberOfPorts];
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int time = i + 1;
            int port = packetIds.get(i) % numberOfPorts;
            while (true) {
                if (freeTime[port] <= time) {
                    result.add(port);
                    freeTime[port] = time + transmissionTime;
                    break;
                }
                port = (port + 1) % numberOfPorts;
            }
        }
        return result;
    }
}
