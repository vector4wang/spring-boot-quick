package com.quick.simhash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.StringTokenizer;

public class ICTCLAS50
{
    static {
        try { 
            String libpath = System.getProperty("java.library.path"); 
            String path = null; 
            StringTokenizer st = new StringTokenizer(libpath, System.getProperty("path.separator"));
            if ( st.hasMoreElements() ) { 
                path = st.nextToken(); 
            }
            
            // copy all dll files to java lib path
            File dllFile = null;
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            byte[] array = null;
            
            dllFile = new File(new File(path), "ICTCLAS50.dll"); 
            if (!dllFile.exists()) {
            	System.out.println("222" + path);
                inputStream = ICTCLAS50.class.getResource("/lib/ICTCLAS50.dll").openStream(); 
                outputStream = new FileOutputStream(dllFile); 
                array = new byte[1024]; 
                for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) { 
                    outputStream.write(array, 0, i); 
                } 
                outputStream.close(); 
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        try {
            // load JniCall.dll
            System.loadLibrary("ICTCLAS50");
        } catch (Error e) {
            e.printStackTrace();
        }
    }
	//public enum eCodeType
	//¡¢
	//    CODE_TYPE_UNKNOWN,//type unknown 
	//    CODE_TYPE_ASCII,//ASCII
	//    CODE_TYPE_GB,//GB2312,GBK,GB10380
	//    CODE_TYPE_UTF8,//UTF-8
	//    CODE_TYPE_BIG5//BIG5
	//}
	
	public native boolean ICTCLAS_Init(byte[] sPath);
	public native boolean ICTCLAS_Exit();
	public native int ICTCLAS_ImportUserDictFile(byte[] sPath,int eCodeType);
	public native int ICTCLAS_SaveTheUsrDic();
	public native int ICTCLAS_SetPOSmap(int nPOSmap);
	public native boolean ICTCLAS_FileProcess(byte[] sSrcFilename, int eCodeType, int bPOSTagged,byte[] sDestFilename);
	public native byte[] ICTCLAS_ParagraphProcess(byte[] sSrc, int eCodeType, int bPOSTagged);
	public native byte[] nativeProcAPara(byte[] sSrc, int eCodeType, int bPOStagged);
	/* Use static intializer */
//	static
//	{
//		String libpath = System.getProperty("java.library.path");
////		//String dir = "D:\\myfc\\javaAPI\\ICTCLAS50.dll";
//////		System.loadLibrary("ICTCLAS50");
//		System.load(libpath+File.separator+"ICTCLAS50.dll");
// 
//	}
}