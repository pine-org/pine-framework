package com.pineframework.core.datamodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
public interface TransientBlobSupport {

    @JsonIgnore
    <T> T getDynamicData(String name);

    @JsonProperty("defaultBlob")
    default byte[] getDefaultBlob() {
        return getDynamicData("defaultBlob");
    }

    @JsonProperty("blobs")
    default byte[][] getBlobs() {
        return getDynamicData("blobs");
    }
}
