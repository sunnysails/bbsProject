package com.kaishengit.service;

import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.QiNiu;
import com.qiniu.common.Zone;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Created by sunny on 2016/12/20.
 */
public class QiNiuService {
    private Auth auth = Auth.create(Config.get("qiniu.ak"), Config.get("qiniu.sk"));
    private Zone zone = Zone.zone1();
    private String  bucketName = Config.get("qiniu.bucket");
    private  StringMap stringMap = new StringMap();

    public void delAfterUpdate(User user) {
        QiNiu.delete(zone, auth, bucketName,user.getAvatarFileName());
    }

    public String upload() {
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.get("qiniu.domain")+"${key}\"}");
        return QiNiu.upload(auth,bucketName,stringMap);
    }
}