package com.lupolupo.android.preseneters.events;

/**
 * @author Ritesh Shakya
 */
public class DownloadProgressEvent {
    private int totalFileSize;
    private float smoothingVariable;
    private int totalBytesWritten;

    public DownloadProgressEvent(int totalBytesWritten, int totalFileSize, float smoothingVariable) {
        this.totalBytesWritten = totalBytesWritten;
        this.totalFileSize = totalFileSize;
        this.smoothingVariable = smoothingVariable;
    }

    public int getTotalFileSize() {
        return totalFileSize;
    }

    public int getTotalBytesWritten() {
        return totalBytesWritten;
    }

    @Override
    public String toString() {
        return "DownloadProgressEvent{" +
                "totalFileSize=" + totalFileSize +
                ", totalBytesWritten=" + totalBytesWritten +
                ", smoothingVariable=" + smoothingVariable +
                '}';
    }

    public float getSmoothingVariable() {
        return smoothingVariable;
    }
}
