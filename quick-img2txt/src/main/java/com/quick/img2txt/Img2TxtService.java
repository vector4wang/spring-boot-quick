package com.quick.img2txt;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/10 0010.
 */
@Component
public class Img2TxtService {

    public static String toChar = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:, ^`'. ";
    public static int width = 150, height = 150; // 大小自己可设置

    @Value("${upload.file.path}")
    private String filePath;

    @Value("${error.file.path}")
    private String errPath;

    public String getErrorPath(){
        return errPath;
    }

//    public static void main(String[] args) throws IOException {
//        File file = ResourceUtils.getFile("classpath:spider.jpg");
//        File temfile = new File("C:\\Users\\bd2\\Desktop\\sb.png");
//        img2txt(file);
//    }

    public File save(byte[] bytes,String name) throws IOException {
        File newFile = new File(filePath + File.separator + name);
        if(!newFile.exists()){
            newFile.createNewFile();
        }
        IOUtils.write(bytes,new FileOutputStream(newFile));
        return img2txt(newFile);
    }

    private File img2txt(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        BufferedImage scaled = getScaledImg(image);
        char[][] array = getImageMatrix(scaled);
        StringBuffer sb = new StringBuffer();
        for (char[] cs : array) {
            for (char c : cs) {
                sb.append(c);
//                System.out.print(c);
            }
            sb.append("\r\n");
//            System.out.println();
        }
        String outName = file.getAbsolutePath() + ".txt";
        File outFile = new File(outName);
        IOUtils.write(sb.toString(), new FileOutputStream(outFile));
        return outFile;
    }


    private static char[][] getImageMatrix(BufferedImage img) {
        int w = img.getWidth(), h = img.getHeight();
        char[][] rst = new char[w][h];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                int rgb = img.getRGB(i, j);
                // 注意溢出
                int r = Integer.valueOf(Integer.toBinaryString(rgb).substring(0, 8), 2);
                int g = (rgb & 0xff00) >> 8;
                int b = rgb & 0xff;
                int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

                // 把int gray转换成char
                int len = toChar.length();
                int base = 256 / len + 1;
                int charIdx = gray / base;
                rst[j][i] = toChar.charAt(charIdx); // 注意i和j的处理顺序，如果是rst[j][i],图像是逆时针90度打印的，仔细体会下getRGB(i，j)这
            }
        return rst;
    }

    private static BufferedImage getScaledImg(BufferedImage image) {
        BufferedImage rst = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        rst.getGraphics().drawImage(image, 0, 0, width, height, null);
        return rst;
    }
}
