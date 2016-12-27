package com.kaishengit.util;

import com.kaishengit.exception.ServiceException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sunny on 2016/12/20.
 */
public class QiNiu {
    private static Logger logger = LoggerFactory.getLogger(QiNiu.class);

    /**
     * 当用户上传新头像时删除旧头像
     *
     * @param zone
     * @param auth
     * @param bucketName
     * @param fileKey
     */
    public static void delete(Zone zone, Auth auth, String bucketName, String fileKey) {

        Configuration configuration = new Configuration(zone);
        BucketManager bucketManager = new BucketManager(auth, configuration);

        try {
            //调用delete方法移动文件
            bucketManager.delete(bucketName, fileKey);

            logger.debug("删除：{}", fileKey);
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;

            logger.error("删除：{}异常。{}", fileKey, r.toString());
        }
    }

    public static String upload(Auth auth, String bucketName, StringMap stringMap) {
        try {
            return auth.uploadToken(bucketName, null, 3600, stringMap);
        }catch (ServiceException e){
            logger.error("图片上传失败，{}",e.getMessage());
            throw new ServiceException("上传失败");
        }
    }
}
