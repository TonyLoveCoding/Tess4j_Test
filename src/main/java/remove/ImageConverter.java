package remove;

import Utils.Utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tony Yao on 2018/4/1.
 */
public class ImageConverter {
    private static List<File> fileList = new ArrayList<File>();

    public static void main(String[] args) {
        //原图片源目录
        String dir = "C:\\Users\\Tony Yao\\Desktop\\天猫工商信息执照";
        //输出目录
        String saveDir = "C:\\Users\\Tony Yao\\Desktop\\天猫工商信息执照\\trans";
        convertAllImages(dir, saveDir);
    }

    private static void convertAllImages(String dir, String saveDir) {
        File dirFile = new File(dir);
        File saveDirFile = new File(saveDir);
        dir = dirFile.getAbsolutePath();
        saveDir = saveDirFile.getAbsolutePath();
        fileList = Utils.getFiles(new File(dir));
        for (File file : fileList) {
            String filePath = file.getAbsolutePath();

            String dstPath = saveDir + filePath.substring(filePath.indexOf(dir) + dir.length(), filePath.length());
            System.out.println("converting: " + filePath);
            replaceColor(file.getAbsolutePath(), dstPath);
        }
    }
    //对每张图片执行替换色块操作
    private static void replaceColor(String srcFile, String dstFile) {
        try {
            Color color = new Color(255, 255, 255, 255);
            replaceImageColor(srcFile, dstFile, color, Color.WHITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //对每张图片执行替换色块操作
    public static void replaceImageColor(String file, String dstFile, Color srcColor, Color targetColor) throws IOException {
        URL http;
        if (file.trim().startsWith("https")) {
            http = new URL(file);
            HttpsURLConnection conn = (HttpsURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else if (file.trim().startsWith("http")) {
            http = new URL(file);
            HttpURLConnection conn = (HttpURLConnection) http.openConnection();
            conn.setRequestMethod("GET");
        } else {
            http = new File(file).toURI().toURL();
        }
        BufferedImage bi = ImageIO.read(http.openStream());
        if(bi == null){
            return;
        }

        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                //System.out.println(bi.getRGB(i, j));
                int color = bi.getRGB(i, j);
                Color oriColor = new Color(color);
                int red = oriColor.getRed();
                int greed = oriColor.getGreen();
                int blue = oriColor.getBlue();
                //常见的灰色水印字样，直接替换为白色
                if ((red >= 229 && greed >= 229 && blue >= 229)) {
                    bi.setRGB(i, j, srcColor.getRGB());
                }
                //将无色区域替换为白色
                if(red == 0 && greed == 0 && blue == 0){
                    bi.setRGB(i, j, srcColor.getRGB());
                }
            }
        }
        String type = file.substring(file.lastIndexOf(".") + 1, file.length());
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(type);
        ImageWriter writer = it.next();
        File f = new File(dstFile);
        f.getParentFile().mkdirs();
        ImageOutputStream ios = ImageIO.createImageOutputStream(f);
        writer.setOutput(ios);
        writer.write(bi);
        bi.flush();
        ios.flush();
        ios.close();
    }
}