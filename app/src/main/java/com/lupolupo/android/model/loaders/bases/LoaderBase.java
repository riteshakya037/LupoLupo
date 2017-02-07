package com.lupolupo.android.model.loaders.bases;

/**
 * @author Ritesh Shakya
 */
public interface LoaderBase {

    void setProgress(String imgFile, int bytesWritten, int written);
}
