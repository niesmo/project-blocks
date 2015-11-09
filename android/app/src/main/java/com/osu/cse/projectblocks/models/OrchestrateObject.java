package com.osu.cse.projectblocks.models;

/**
 * Created by niesmo on 11/8/2015.
 */
public class OrchestrateObject {
    private String key;
    private String collection;
    private String ref;

    public OrchestrateObject(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
