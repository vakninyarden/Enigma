package engine.statistic;

public class ProcessRecord {
    public final  String sorceMessage;
    public final String processedMessage;
    private final long timeInNanos;

    public ProcessRecord(String sorceMessage, String processedMessage, long timeInNanos) {
        this.sorceMessage = sorceMessage;
        this.processedMessage = processedMessage;
        this.timeInNanos = timeInNanos;
    }

    public String getSorceMessage() {
        return sorceMessage;
    }

    public String getProcessedMessage() {
        return processedMessage;
    }

    public long getTimeInNanos() {
        return timeInNanos;
    }
}
