package com.quick.controller;

import com.quick.tika.TikaUtil;
import com.quick.util.BaseResp;
import com.quick.util.ResultStatus;
import com.quick.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/18
 * Time: 10:14
 * Description:
 */
@RestController
@RequestMapping("/web")
@Api(value = "Tika本地服务接口", description = "抽取文件的文本内容")
public class WebController {


    @ApiOperation("上传文本，返回文本内容")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/content", method = RequestMethod.POST)
    public
    @ResponseBody
    BaseResp<String> getFileContent(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String fileContent = TikaUtil.handleStreamContent(file.getBytes());
                return new BaseResp<String>(ResultStatus.SUCCESS, fileContent);
            } catch (Exception e) {
                e.printStackTrace();
                return new BaseResp<String>(ResultStatus.error_file_upload, "You failed to upload  => " + e.getCause());
            }
        } else {
            return new BaseResp<String>(ResultStatus.error_file_upload, "You failed to upload  because the file was empty.");
        }
    }

    @ApiOperation("上传文本，返回文本MetaData信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/metaData", method = RequestMethod.POST)
    public
    @ResponseBody
    BaseResp<Map<String, String>> getFileMetaData(@RequestParam("file") MultipartFile file) {
        Map<String, String> fileMetadata = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                fileMetadata = TikaUtil.handleStreamMetaDate(file.getBytes());
                return new BaseResp<Map<String, String>>(ResultStatus.SUCCESS, fileMetadata);
            } catch (Exception e) {
                fileMetadata.put("cause", "You failed to upload  => " + e.getCause());
                return new BaseResp<Map<String, String>>(ResultStatus.error_file_upload, fileMetadata);
            }
        } else {
            fileMetadata.put("cause", "You failed to upload  => file is empty");
            return new BaseResp<Map<String, String>>(ResultStatus.error_file_upload, fileMetadata);
        }
    }

}
