package com.demotwo.sell.util;

import com.demotwo.sell.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    //获取当前的classpath
    //private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String basePath = PathUtil.getImgBasePath();
    //设置时间格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //随机数对象
    private static final Random r = new Random();

    /**
     * 处理缩略图
     * @param imageHolder: 文件流和文件的名称
     * @param targetAddr: 图片的传出路径: 绝对路径
     * @return: 返回新生成图片的相对值路径
     */
    public static String generateThumbnail(ImageHolder imageHolder, String targetAddr) {
        //由于用户传过来的图片都是随意命名的，因此有很多文件是重名的，所以我们需要对其使用系统随机的不重名的方法重新命名！
        String realFileName = getRandomFileName();     //获取文件的随机名
        String extension = getFileExtension(imageHolder.getFileName());         //获取文件的拓展名
        makeDirPath(targetAddr);                       //有时候传过来的图片传出路径是不存在的，因此我们需要创建目录
        String relativeAddr =  targetAddr + realFileName + extension;               //图片文件的绝对路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);  //   真实文件输出的路径： 项目图片根路径 + 图片的绝对路径

        try {
            Thumbnails.of(imageHolder.getFileInputStream()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/" + "watermark.jpg")), 0.25f)
                    .outputQuality(0.8)    //压缩百分比
                    .toFile(dest);         //输出到
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 详情图的处理
     * @param imageHolder
     * @param targetAddr
     * @return
     */
    public static String generateItemImagesThumbnail(ImageHolder imageHolder, String targetAddr) {
        //由于用户传过来的图片都是随意命名的，因此有很多文件是重名的，所以我们需要对其使用系统随机的不重名的方法重新命名！
        String realFileName = getRandomFileName();     //获取文件的随机名
        String extension = getFileExtension(imageHolder.getFileName());         //获取文件的拓展名
        makeDirPath(targetAddr);                       //有时候传过来的图片传出路径是不存在的，因此我们需要创建目录
        String relativeAddr =  targetAddr + realFileName + extension;               //图片文件的绝对路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);  //   真实文件输出的路径： 项目图片根路径 + 图片的绝对路径

        try {
            Thumbnails.of(imageHolder.getFileInputStream()).size(300, 280)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/" + "watermark.jpg")), 0.25f)
                    .outputQuality(0.9)    //压缩百分比
                    .toFile(dest);         //输出到
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     *  生成随机文件名，当前年月日小时分钟秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        //获取随机的五位数: 这里也就是最小数是10000，最大数是99999
        int randomNumber = r.nextInt(89999) + 10000;
        String currentTimeFormat = sDateFormat.format(new Date());
        return currentTimeFormat + randomNumber;
    }

    /**
     *  获取输入文件流的拓展名(文件的扩展名)
     * @return
     */
    private static String getFileExtension(String fileName) {
        //获得输入文件流的文件名
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     *  创建目标路径所涉及到的目录
     * @param targetAddr: 图片传出的路径
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        //判断路径是否存在，不存在的话则递归的创建一个
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("C:/Users/45330/Pictures/Saved Pictures/键盘.jpg"))
                .size(200, 200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
                .outputQuality(0.8)    //压缩百分比
                .toFile(new File("d:"));
        System.out.println(basePath);

    }

    //删掉店铺的图片
    public static void deleteShopImg(String fileStr) {
        if(fileStr != null) {
            //完整的图片的路径: 项目路径 + 图片的绝对路径
            String fileOrPath = PathUtil.getImgBasePath() + fileStr;
            File file  = new File(fileOrPath);
            //判断路径是否存在
            if(file.exists()) {
                //判断路径是不是目录,是目录就遍历目录所有的文件，删除
                if(file.isDirectory()) {
                    File[] file1 = file.listFiles();
                    for (File file2 : file1) {
                        file2.delete();
                    }
                }
                //如果不是目录，那就直接删除文件
                file.delete();
            }
        }
    }

}
