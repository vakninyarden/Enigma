package engine.statistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class StatisticsManager {
    private Map<String, List<ProcessRecord>> statisticsData;
    private String currentCode;

    public StatisticsManager() {
        this.statisticsData = new HashMap<>();
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }
    public void addStatistic(ProcessRecord message){
        statisticsData.computeIfAbsent(currentCode, k -> new ArrayList<>()).add(message);

    }

    public void printStatistics() {
        if(statisticsData.isEmpty()) {
            System.out.println("No statistics available.");
            return;
        }
        for (Map.Entry<String, List<ProcessRecord>> entry : statisticsData.entrySet()) {
            String code = entry.getKey();
            List<ProcessRecord> messages = entry.getValue();
            System.out.println("Code: " + code);

            int counter=1;
            for (ProcessRecord message : messages) {
                System.out.print(counter +". <" +message.getSorceMessage() + "> --> <" +
                        message.getProcessedMessage()
                       +" (" + message.getTimeInNanos() + " nano-seconds)\n");
                counter++;
            }
        }
    }


}
