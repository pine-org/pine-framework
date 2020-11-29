package com.pineframework.core.datamodel.persistence;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface BlobSupport {

    byte[] getDefaultBlob();

    byte[][] getBlobs();
}
