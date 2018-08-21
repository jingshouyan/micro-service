package io.jing.server.gateway.controller;

import com.google.common.collect.Maps;
import io.jing.base.util.rsp.RspUtil;
import io.jing.server.gateway.constant.AppCode;
import io.jing.server.gateway.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class FileController implements AppConstant {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy/MM/dd/");
    /**
     * 单文件上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            String sFilename = UUID.randomUUID().toString().replace("-","").toLowerCase();
            if (!StringUtils.isBlank(suffix)) {
                sFilename += "."+suffix;
            }
            String datePath = LocalDate.now().format(DTF);
            String savePath = UPLOAD_PATH + datePath + sFilename ;
            String fileUrl = UPLOAD_PROFFIX + "/" + datePath+sFilename;
            File saveFile = new File(savePath);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));) {
                out.write(file.getBytes());
                out.flush();
                Map<String,String> map = Maps.newHashMap();
                map.put("filename",filename);
                map.put("fileUrl",fileUrl);
                return RspUtil.success(map);
            } catch (Exception e) {
                log.error("file upload faild.",e);
                return RspUtil.error(AppCode.FILE_UPLOAD_ERROR,e);
            }
        } else {
            return ("上传失败，因为文件为空.");
        }
    }

    /**
     * 多文件上传
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadFiles")
    public String uploadFiles(HttpServletRequest request) throws IOException {
        File savePath = new File(request.getSession().getServletContext().getRealPath("/upload/"));
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    if (stream != null) {
                        stream.close();
                        stream = null;
                    }
                    return "第 " + i + " 个文件上传有错误" + e.getMessage();
                }
            } else {
                return "第 " + i + " 个文件为空";
            }
        }
        return "所有文件上传成功";
    }

}
