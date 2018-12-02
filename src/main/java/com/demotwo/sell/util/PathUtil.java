package com.demotwo.sell.util;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {

    /*
     * 根据不同的操作系统，设置储存图片文件不同的根目录
     */
    private static String seperator = System.getProperty("file.separator");

    private static String winBasePath;

    public static String linuxBasePath;

    public static String shopRelevantPath;

    @Value("${win.base.path}")
    public void setWinBasePath(String winBasePath) {
        PathUtil.winBasePath = winBasePath;
    }

    @Value("${linux.base.path}")
    public void setLinuxBasePath(String linuxBasePath) {
        PathUtil.linuxBasePath = linuxBasePath;
    }

    @Value("${shop.relevant.path}")
    public void setShopRelevantPath(String shopRelevantPath) {
        PathUtil.shopRelevantPath = shopRelevantPath;
    }

    /**
     *
     * @return 项目图片的根路径
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")) {
            basePath = winBasePath;
        } else {
            basePath = linuxBasePath;
        }
        //这里因为如果在windows下运行的话，其路径的地址会转换为反斜杠"\"，故而这里手动的强制转换为正斜杠"/",
        // 也就是说统一转换为"/"正斜杠
        return basePath.replace("/", seperator);
    }

    /**
     *
     * @param productId
     * @return 项目的店铺的图片的路径（根据不同的业务需求返回不同的子路径）
     */
    public static String getShopImagePath(String productId) {
        String basePath = shopRelevantPath + productId + "/";
        return basePath.replace("/", seperator);
    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("file.separator"));
    }
}
