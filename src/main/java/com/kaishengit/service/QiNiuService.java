package com.kaishengit.service;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;

import javax.security.auth.login.Configuration;

/**
 * Created by sunny on 2016/12/20.
 */
public class QiNiuService {
    /**
     *
     * @param bucketManager
     * @param bucketName 要测试的空间和key
     * @param key 要删除的文件
     */
    public void qiNiuDelete(BucketManager bucketManager, String bucketName,String key){
        try {
            //调用delete方法移动文件
            bucketManager.delete(bucketName, key);
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
        }
    }
}
