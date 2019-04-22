package com.pipipan.demo.common;

import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.domain.User;

public class Constants {
    public static String user_id = "";
    public static User user;
    public static Store store;
    public static Order order;
    public static String BaiduMapKey = "vkX9wsQivFUOSALFZLX2ozLzlt8EBGzp";
    public static Address address;
    //OSS
    public static final String OSS_AccessKeyId = "LTAIqMIT5KX4oGAT";
    public static final String OSS_AccessKeySecret = "wYwZdNHrnvAiM9GNddiXqaeHcB4xfz";
    public static final String OSS_BUCKET = "face-file";
    public static final String OSS_DIR_FACE = OSS_BUCKET + "/face-file";
    public static final String OSS_DIR_FEATURE = "face-feature-file";
    public static final String OSS_endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    public static final String OSS_AUTH_SERVER = "http://47.106.8.44:7080/";
}
