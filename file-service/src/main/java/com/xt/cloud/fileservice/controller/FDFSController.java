package com.xt.cloud.fileservice.controller;

import com.xt.cloud.fileservice.pojo.FDFSFile;
import com.xt.cloud.fileservice.utils.FDFSFileManager;
import org.csource.common.NameValuePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by shawn on 2017/11/17.
 */
@RestController
public class FDFSController {
    @PostMapping(value = "/upload")
    public String upload(MultipartFile target) throws IOException {
        byte[] content = target.getBytes();
        String originalName = target.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf('.')+1);
        String length = String.valueOf(target.getSize());//byte

        FDFSFile file = new FDFSFile(content, originalName, extension, length);
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", file.getName());
        metaList[1] = new NameValuePair("fileLength", file.getLength());
        metaList[2] = new NameValuePair("fileExtension", file.getExtension());

        return FDFSFileManager.upload(file, metaList);
    }

    @PostMapping(value = "/delete")
    public String delete(String groupName, String uri){
        return String.valueOf(FDFSFileManager.delete(groupName, uri));
    }
    

}
