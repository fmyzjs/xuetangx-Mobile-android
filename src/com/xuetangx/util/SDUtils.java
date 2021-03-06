package com.xuetangx.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;

public class SDUtils {
	public static boolean checkSDExit() {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true; //sd卡可读可写.
		}else {
			return false;
		}
	}
	public static File getImageDir(Context context) {
		File dir = null;
		if (!checkSDExit()) {
			dir = context.getDir(ConstantUtils.IMAGE_CACHE_PATH, context.MODE_WORLD_WRITEABLE);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}else{
			dir = new File(Environment.getExternalStorageDirectory(), ConstantUtils.IMAGE_CACHE_PATH);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return dir;
	}
	public static File getVideoDir() {
		if (!checkSDExit()) {
			return null;
		} else{
			File dir = new File(Environment.getExternalStorageDirectory(), ConstantUtils.VIDEO_CACHE_PATH);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir;
		}
		
	}
	public static int writeFile(String path, String name, String content) {
		if (!checkSDExit()) {
			return -1;  //sd卡不可用.
		}
		File dir = new File(Environment.getExternalStorageDirectory(), path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name);
		try {
			if (!file.exists()){
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.close();
		}catch(IOException e) {
			return -2;
		}
		return 0;
	}
	public static int writeFile(String path, String name, byte[] content) {
		if (!checkSDExit()) {
			return -1;  //sd卡不可用.
		}
		File dir = new File(Environment.getExternalStorageDirectory(), path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, name);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
			writer.write(content);
			writer.close();
		}catch(IOException e) {
			return -2;
		}
		return 0;
	}
	public static String readSDFile(String path, String name) {
		return readFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path, name);
	}
	public static String readFile(String path, String name) {
		File file = new File (path, name);
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String content = reader.readLine();
			while (content != null) {
				sb.append(content);
				sb.append('\n');
				content = reader.readLine();
			}
			reader.close();
			return sb.toString();
		}catch (IOException e) {
			return "";
		}
	}
	public static int writeStreamToSDCard(String path,String filename,InputStream input) {
		if (!checkSDExit()) {
			return -1;  //sd卡不可用.
		}
        OutputStream output=null;
        File dir = new File(Environment.getExternalStorageDirectory(), path);
        if (!dir.exists()) {
        	dir.mkdirs();
        }
        File file = new File (dir, filename);
        try {
        	if(!file.exists()) {
        		file.createNewFile();
        	}
            output=new FileOutputStream(file);
            byte[]bt=new byte[4*1024];
            while (input.read(bt)!=-1) {
                output.write(bt);
            }
            //刷新缓存，
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        finally{
           try{
        	   output.close();
           }catch (Exception e) {
                 e.printStackTrace();
              }
          }
         return 0;
	}
	public static boolean checkFile(String path, String name) {
		File file = new File(path, name);
		if (file.exists() && file.length() > 0) {
			return true;
		}else{
			return false;
		}
	}
	public static File getFile(String parent, String filename) {
		File dir = new File(Environment.getExternalStorageDirectory(), parent);
		if (!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dir, filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}

}

