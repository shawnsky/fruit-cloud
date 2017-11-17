package com.xt.cloud.fileservice.utils;

import com.xt.cloud.fileservice.pojo.FDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by shawn on 2017/11/17.
 */
public class FDFSFileManager implements IConfig{

    private static final long serialVersionUID = 1L;

    private static TrackerClient trackerClient;

    private static TrackerServer trackerServer;

    private static StorageServer storageServer;

    private static StorageClient storageClient;

    static {
        try {
            String confPath = CLIENT_CONFIG_FILE;
            ClientGlobal.init(confPath);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete FastDFS file.
     * 删除文件
     * @param groupName the group name LIKE "group1"
     * @param uri       the uri LIKE "M00/00/00/SSS_HHH_AAA_WWW_NNN.doc"
     * @return  0=success // 2=file not exist // other number=error
     */
    public static int delete(String groupName, String uri) {
        int i = 0;
        try {
            i = storageClient.delete_file(groupName, uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


    /**
     * Upload string.
     * 上传文件
     * @param file       the FDFSFile
     * @param valuePairs the file meta info
     * @return the file absolute path string
     */
    public static String upload(FDFSFile file, NameValuePair[] valuePairs) {
        String[] uploadResults = null;
        try {
            uploadResults = storageClient.upload_file(file.getContent(),file.getExtension(), valuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        String fileAbsolutePath = PROTOCOL
                + TRACKER_NGNIX_ADDR
                + SEPARATOR + groupName
                + SEPARATOR + remoteFileName;
        return fileAbsolutePath;
    }
}
