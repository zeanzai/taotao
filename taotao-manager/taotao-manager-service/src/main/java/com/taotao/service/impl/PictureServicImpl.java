package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;

/**
 * 图片上传服务
 * <p>Title: PictureServicImpl</p>
 * <p>Description: </p>
 * <p>Company: http://www.posun.cn/</p>
 *
 * @author ShawnWang
 * @version 1.0
 * @date 2017年2月10日下午2:11:30
 */
@Service
public class PictureServicImpl implements PictureService {
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;

    @Value("${FTP_PORT}")
    private Integer FTP_PORT;

    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;

    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;

    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;

    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;


    /***
     * 图片上传功能
     * 注意，如果上传之后不能显示出来，或者是上传之后没有反应，可以debug一下，最后查明原因可能是建立的图片所在位置的文件夹的权限不够。更改一下图片服务器上面图片上传路径的权限即可。
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param uploadFile
     * @return
     * @see com.taotao.service.PictureService#uploadFile(MultipartFile)
     */
    @Override
    public Map uploadFile(MultipartFile uploadFile) {
        Map resultMap = new HashMap<>();
        try {
            // 获取源文件扩展名
            String oldName = uploadFile.getOriginalFilename();

            // 形成新的文件扩展名
            String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));

            // 图片上传路径
            String imgPath = new DateTime().toString("/yyyy/MM/dd");

            // 上传
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, imgPath, newName, uploadFile.getInputStream());
            if (!result) {
                resultMap.put("error", 1);
                resultMap.put("message", "文件上传失败");
                return resultMap;
            }
            resultMap.put("error", 0);
            resultMap.put("url", IMAGE_SERVER_BASE_URL + imgPath + "/" + newName);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传发生异常");
            return resultMap;
        }
    }

}
