package com.xt.cloud.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by shawn on 2017/11/18.
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
    /**
     * upload
     * delete
     * rename
     * share
     *
     */
    public String upload(MultipartFile file, String userId){
        return null;
    }

    public String delete(String fileId){
        return null;
    }

    public String rename(String fileId, String newName){
        return null;
    }

    public String moveTo(String fileId, String dirId){
        return null;
    }


}
