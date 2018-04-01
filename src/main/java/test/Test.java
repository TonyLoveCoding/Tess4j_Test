package test;

import Utils.Utils;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony Yao on 2018/3/31.
 */
public class Test {
    private static List<File> fileList;

    public static void main(String[] args) {
        long stime = System.currentTimeMillis();
        File fileDir = new File("E:\\TestCode\\trans");
        File destDir = new File("E:\\TestCode\\output\\output.txt");
        fileList = Utils.getFiles(fileDir);
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        instance.setLanguage("normal");//添加中文字库

        try {
            PrintStream ps = new PrintStream(new FileOutputStream(destDir));
            ps.println("一共有" + fileList.size() + "个输出文本");
            for(int i = 0; i < fileList.size(); i++){
                ps.append("\n第" + (i+1) + "个:\n");
                String result = instance.doOCR(fileList.get(i));
                ps.append(result.trim());

            }
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        long etime = System.currentTimeMillis();
        System.out.println("耗费了" + (float)(etime - stime)/1000 + "s");
    }
}
