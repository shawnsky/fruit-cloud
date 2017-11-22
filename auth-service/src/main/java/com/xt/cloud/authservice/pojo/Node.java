package com.xt.cloud.authservice.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shawn on 2017/11/19.
 */
public class Node {
    private String value;
    private boolean isDir;
    private Set<Node> nodes;

    public Node(){
        this.nodes = new HashSet<>();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
}
