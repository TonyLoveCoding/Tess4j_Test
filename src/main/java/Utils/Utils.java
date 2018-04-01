package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony Yao on 2018/4/1.
 */
public class Utils {
    private static List<File> list;
    //加载图片文件并生成图片到列表中
    public static List<File> getFiles(File file){
        list = new ArrayList<File>();
        loadImages(file);
        return list;
    }

    public static void loadImages(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        loadImages(fileArray[i]);
                    }
                }
            } else {
                String name = f.getName();
                if (name.endsWith("png") || name.endsWith("jpg")) {
                    list.add(f);
                }
            }
        }
    }
}
