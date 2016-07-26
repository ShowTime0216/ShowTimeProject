package com.showtime.lp.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者:liupeng
 * 16/7/26 11:10
 * 注释: 文件和IO流工具类
 */
public class FileUtils {

    /**
     * @param
     * @return boolean
     * @Description: 判断SD卡是否存在
     */
    public static boolean sdCardIsExit() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * @param
     * @return String
     * @Description: 获取SD卡路径
     */
    public static String getSDCardPath() {
        if (sdCardIsExit()) {
            return Environment.getExternalStorageDirectory().toString() + "/";
        }
        return null;
    }

    /**
     * @param
     * @return File
     * @Description: 在SD卡上创建(文本)
     */
    public static File createSDFile(String fileName) {
        File file = new File(Constants.PATH_PROJECT_FILE + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @param
     * @return void
     * @Description: 数据保存到文件
     */
    public static void saveDataForFile(String data, String fileName) {
        File dirFile = new File(Constants.PATH_PROJECT_FILE + fileName);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileOutputStream outFileStream = new FileOutputStream(Constants.PATH_PROJECT_FILE + fileName);
                outFileStream.write(data.getBytes());
                outFileStream.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * @param
     * @return String
     * @Description: 从文件上取到数据
     */
    public static String getDataForFile(String path) {
        StringBuffer strsBuffer = new StringBuffer();
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(Constants.PATH_PROJECT_FILE + path);
                if (file.exists()) {
                    FileInputStream fileR = new FileInputStream(file); // 打开文件输入流
                    BufferedReader reads = new BufferedReader(new InputStreamReader(fileR));
                    String st = null;
                    while ((st = reads.readLine()) != null) {
                        strsBuffer.append(st);
                    }
                    fileR.close();
                } else {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strsBuffer.toString();
    }

    /**
     * @param
     * @return List<String>
     * @Description: 得到目录下所有的文件名和文件夹名
     */
    public static List<String> getFileNameList(String path) {
        List<String> fileNameList = new ArrayList<>();
        File file = new File(path);
        File[] fileArr = file.listFiles();
        if (fileArr != null) {
            int size = fileArr.length;
            for (int i = 0; i < size; i++) {
                fileNameList.add(path + "/" + fileArr[i].getName());
            }
            if (file != null) {
                file = null;
            }
        }
        return fileNameList;
    }

    /**
     * @param
     * @return int
     * @Description: 获取文件大小
     */
    public static int getFileKbSize(String fileName) {
        int size = 0;
        File file = null;
        FileInputStream fis = null;
        try {
            file = new File(fileName);
            fis = new FileInputStream(file);
            size = fis.available();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                file = null;
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fis = null;
            }
        }
        return size;
    }

    /**
     * @param
     * @return String
     * @Description: 获取文件大小并带"k"或"b"
     */
    public static String getSelectFileSize(String fileName) {
        int size = 0;
        File file = null;
        FileInputStream fis = null;
        try {
            file = new File(fileName);
            fis = new FileInputStream(file);
            size = fis.available();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                file = null;
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fis = null;
            }
        }
        if (size < 1024) {
            return size + "b";
        } else {
            return size / 1024 + "k";
        }
    }


    /**
     * 根据文件路径删除文件
     **/
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
