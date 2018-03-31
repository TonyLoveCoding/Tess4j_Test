package test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * Created by Tony Yao on 2018/3/31.
 */
public class Test {
    public static void main(String[] args) {
        File imageFile = new File("E:\\TestCode\\1.png");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
//         ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setLanguage("chi_sim");//添加中文字库
        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
