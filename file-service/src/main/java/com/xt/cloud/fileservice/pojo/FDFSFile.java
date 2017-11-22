package com.xt.cloud.fileservice.pojo;

import com.xt.cloud.fileservice.utils.IConfig;

/**
 * Created by shawn on 2017/11/16.
 */

public class FDFSFile implements IConfig {
    private static final long serialVersionUID = 1L;

    private byte[] content;

    private String name;

    private String extension;

    private String length;

    public FDFSFile(byte[] content, String name, String extension, String length) {
        this.content = content;
        this.name = name;
        this.extension = extension;
        this.length = length;

    }


    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
