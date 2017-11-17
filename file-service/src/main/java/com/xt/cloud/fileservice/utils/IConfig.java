package com.xt.cloud.fileservice.utils;

import java.io.Serializable;

/**
 * Created by shawn on 2017/11/17.
 */
public interface IConfig extends Serializable{

    String PROTOCOL = "http://";

    String SEPARATOR = "/";

    String TRACKER_NGNIX_ADDR = "120.24.92.198";

    String TRACKER_PORT = "22122";

    String CLIENT_CONFIG_FILE = "fdfs-client.conf";
}
