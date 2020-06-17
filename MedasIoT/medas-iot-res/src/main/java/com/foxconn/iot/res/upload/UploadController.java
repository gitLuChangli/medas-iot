package com.foxconn.iot.res.upload;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

@RestController
public class UploadController {
	
	@Value("${iot.upload.path}")
	private String uploadPath;
	
	@PostMapping(value = "/upload")
	public String upload(@RequestParam("file") MultipartFile multipartFile) {
		String tempPath = uploadPath + "/temp/";
        Map<String, Object> result = new HashMap<>();
        File dir = new File(tempPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));
        fileName = Snowflaker.getId() + ext;
        try {
            multipartFile.transferTo(new File(tempPath + fileName));
            result.put("code", 1);
            result.put("filePath", fileName);
            result.put("message", "Upload success");
        } catch (IOException e) {
            e.printStackTrace();
            result.put("code", 128);
            result.put("message", e.getMessage());
        }
        return JSON.toJSONString(result);
	}
	
	@PostMapping(value = "/move")
	public String movieFile(@RequestParam(value = "type", required = true) String type, @RequestParam(value = "file", required = true) String fileName) {
        File file = new File(uploadPath + "/temp/" + fileName);
        Map<String, Object> result = new HashMap<>();
        if (file.exists()) {
        	String newPath = String.format("%s/%s/", uploadPath, type);
            File dir = new File(newPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (file.renameTo(new File(newPath + fileName))) {
                result.put("code", 1);
                result.put("filePath", type + fileName);
                file.delete();
            } else {
            	result.put("code", 128);
            	result.put("message", "Move failed");
            }
        } else {
        	result.put("code", 128);
            result.put("message", "File not exist");
        }
        return JSON.toJSONString(result);
    }
	
	@DeleteMapping(value = "/delete")
	public String deleteFile(@RequestParam(value = "file", required = true) String fileName) {
		File file = new File(uploadPath + fileName);
		Map<String, Object> result = new HashMap<>();
		if (file.exists()) {
			file.delete();
			result.put("code", 1);
			result.put("message", "Delete success");
		} else {
			result.put("code", 2);
			result.put("message", "File not found");
		}
		return JSON.toJSONString(result);
	}
}
