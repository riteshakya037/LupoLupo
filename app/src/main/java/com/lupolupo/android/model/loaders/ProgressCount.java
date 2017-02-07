package com.lupolupo.android.model.loaders;

/**
 * @author Ritesh Shakya
 */
class ProgressCount {
    final int bytesWritten;
    final int totalSize;

    ProgressCount(int bytesWritten, int totalSize) {
        this.bytesWritten = bytesWritten;
        this.totalSize = totalSize;
    }
}
