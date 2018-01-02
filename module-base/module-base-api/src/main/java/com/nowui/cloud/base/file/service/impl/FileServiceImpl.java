package com.nowui.cloud.base.file.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nowui.cloud.base.file.entity.File;
import com.nowui.cloud.base.file.entity.enums.FileType;
import com.nowui.cloud.base.file.mapper.FileMapper;
import com.nowui.cloud.base.file.service.FileService;
import com.nowui.cloud.constant.Constant;
import com.nowui.cloud.mybatisplus.BaseWrapper;
import com.nowui.cloud.service.impl.BaseServiceImpl;
import com.nowui.cloud.util.FileUtil;
import com.nowui.cloud.util.Util;

/**
 * 文件业务实现
 *
 * @author marcus
 *
 * 2018-01-01
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<FileMapper, File> implements FileService {

    @Override
    public Integer adminCount(String appId, String fileName, String fileType) {
        Integer count = count(
                new BaseWrapper<File>()
                        .eq(File.APP_ID, appId)
                        .likeAllowEmpty(File.FILE_NAME, fileName)
                        .eq(File.SYSTEM_STATUS, true)
        );
        return count;
    }

    @Override
    public List<File> adminList(String appId, String fileName, String fileType, Integer m, Integer n) {
        List<File> fileList = list(
                new BaseWrapper<File>()
                        .eq(File.APP_ID, appId)
                        .likeAllowEmpty(File.FILE_NAME, fileName)
                        .eq(File.SYSTEM_STATUS, true)
                        .orderDesc(Arrays.asList(File.SYSTEM_CREATE_TIME)),
                m,
                n
        );

        return fileList;
    }

    @Override
    public List<File> imageUpload(String appId, String userId, CommonsMultipartFile[] commonsMultipartFiles) {
        String webRootPath = FileUtil.getWebRootPath(); 
        String path = Util.createPath(webRootPath, Constant.UPLOAD, appId, userId);
        String thumbnailPath = Util.createPath(webRootPath, Constant.UPLOAD, appId, userId, Constant.THUMBNAIL);
        String originalPath = Util.createPath(webRootPath, Constant.UPLOAD, appId, userId, Constant.ORIGINAL);

        FileUtil.createPath(path);
        FileUtil.createPath(thumbnailPath);
        FileUtil.createPath(originalPath);
        
        List<File> fileList = new ArrayList<File>();
        for (CommonsMultipartFile myfile : commonsMultipartFiles) {
            if (myfile.isEmpty()) {
                throw new RuntimeException("上传图片为空");
            } else {
                String originalFileName = myfile.getOriginalFilename();
                String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                String fileName = Util.getRandomUUID() + "." + fileSuffix;
                
                if (!".jpg.jpeg.gif.png.bmp.JPG.JPEG.GIF.PNG.BMP".contains(fileSuffix)) {
                    throw new RuntimeException("上传图片格式不对");
                }
                
                if (((int) myfile.getSize() / 1024 + 1) > 10) {
                    throw new RuntimeException("上传图片超过限定大小");
                }
                
                java.io.File file = new java.io.File(originalPath, fileName);
                try {
                    FileUtil.copyInputStreamToFile(myfile.getInputStream(), file);
                } catch (IOException e) {
                    throw new RuntimeException("上传图片失败");
                }
                
                path = path + java.io.File.separator + fileName;
                thumbnailPath = thumbnailPath + java.io.File.separator + fileName;
                originalPath = originalPath + java.io.File.separator + fileName;

                String fileType = FileType.IMAGE.getKey();

                FileUtil.resizeImage(file, fileSuffix, thumbnailPath, 100);
                FileUtil.resizeImage(file, fileSuffix, path, 360);
                
                Integer fileSize = (int) file.length();
                String filePath = path.replace(FileUtil.getWebRootPath(), "");
                String fileThumbnailPath = thumbnailPath.replace(FileUtil.getWebRootPath(), "");
                String fileOriginalPath = originalPath.replace(FileUtil.getWebRootPath(), "");
                Boolean fileIsExternal = false;
                
                File entity = new File();
                entity.setAppId(appId);
                entity.setFileName(originalFileName);
                entity.setFileIsExternal(fileIsExternal);
                entity.setFileOriginalPath(fileOriginalPath);
                entity.setFilePath(filePath);
                entity.setFileSize(fileSize);
                entity.setFileSuffix(fileSuffix);
                entity.setFileThumbnailPath(fileThumbnailPath);
                entity.setFileType(fileType);
                entity.setFileCoverImage("");
                Boolean result = save(entity, userId);

                if (!result) {
                    throw new RuntimeException("上传不成功");
                }

                fileList.add(entity);
            }
        }
        return fileList;
    }
    
    @Override
    public File uploadBase64(String appId, String userId, String base64Data) {
        String webRootPath = FileUtil.getWebRootPath(); 
        String path = Util.createPath(webRootPath, Constant.UPLOAD, appId, userId);
        String thumbnailPath = Util.createPath(webRootPath, Constant.UPLOAD, appId, userId, Constant.THUMBNAIL);
        String originalPath = Util.createPath(webRootPath, Constant.UPLOAD, appId, userId, Constant.ORIGINAL);
        
        FileUtil.createPath(path);
        FileUtil.createPath(thumbnailPath);
        FileUtil.createPath(originalPath);
        
        String fileSuffix = base64Data.substring(11, base64Data.indexOf(";base64,"));
        
        String fileName = Util.getRandomUUID() + "." + fileSuffix;

        String imageString = base64Data.substring(base64Data.indexOf(","));

        byte[] imageByte = Base64.decodeBase64(imageString.getBytes());
        
        path = Util.createPath(path, fileName);
        thumbnailPath = Util.createPath(thumbnailPath, fileName);
        originalPath = Util.createPath(originalPath, fileName);
        
        java.io.File imageFile = new java.io.File(originalPath);
        
        try {
            FileImageOutputStream fos = new FileImageOutputStream(imageFile);
            fos.write(imageByte);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!".jpg.jpeg.gif.png.bmp.JPG.JPEG.GIF.PNG.BMP".contains(fileSuffix)) {
            throw new RuntimeException("上传图片格式不对");
        }

        FileUtil.resizeImage(imageFile, fileSuffix, thumbnailPath, 100);
        FileUtil.resizeImage(imageFile, fileSuffix, path, 360);
        
        String fileType = FileType.IMAGE.getKey();
        Integer fileSize = (int) imageFile.length();
        String filePath = path.replace(FileUtil.getWebRootPath(), "");
        String fileThumbnailPath = thumbnailPath.replace(FileUtil.getWebRootPath(), "");
        String fileOriginalPath = originalPath.replace(FileUtil.getWebRootPath(), "");
        Boolean fileIsExternal = false;

        File entity = new File();
        entity.setAppId(appId);
        entity.setFileName(fileName);
        entity.setFileIsExternal(fileIsExternal);
        entity.setFileOriginalPath(fileOriginalPath);
        entity.setFilePath(filePath);
        entity.setFileSize(fileSize);
        entity.setFileSuffix(fileSuffix);
        entity.setFileThumbnailPath(fileThumbnailPath);
        entity.setFileType(fileType);
        entity.setFileCoverImage("");
        Boolean result = save(entity, userId);

        if (!result) {
            throw new RuntimeException("上传不成功");
        }
        
        return entity;
    }

    @Override
    public List<File> videoUpload(String appId, String userId, String fileCoverImage,
            CommonsMultipartFile[] commonsMultipartFiles) {
        // TODO Auto-generated method stub
        return null;
    }

}