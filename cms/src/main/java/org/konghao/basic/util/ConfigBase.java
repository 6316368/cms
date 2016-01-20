package org.konghao.basic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * User: lh
 * @Date  2015	2015年8月9日		上午10:24:14
 * Time: 9:49:48
 * 配置文件
 */
public class ConfigBase extends HashMap<String,String> {
	/**
	 * serialVersionUID:
	 * @author   lh
	 * @Date	 2015	2015年8月9日		上午10:24:14
	 */
	private static final long serialVersionUID = 1L;
	File configfile=null;
    public ConfigBase(String name) {
	    URL resource = ConfigBase.class.getClassLoader().getResource(name);
	    String path = resource.toString();
	    int indexOf = path.indexOf("/");
	    path=path.substring(indexOf+1, path.length());
	    configfile=new File(path);
        loadConfigfile();
    }
    private void loadConfigfile() {
        BufferedReader rd=null;
        try {
            if(!configfile.exists()){
                return;
            }
            rd = new BufferedReader(new FileReader(configfile));
            String line=null;

            while((line=rd.readLine())!=null){
                int p=line.indexOf("=");
                if(p>0){
                    String name=line.substring(0,p).trim();
                    String value=line.substring(p+1).trim();
                    put(name,value);
                }
            }
        } catch (Exception e) {
        } finally {
            if(rd!=null){
                try {
                    rd.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void saveConfigfile(){
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(configfile));

            LinkedList linkedkey = new LinkedList(keySet());
            Collections.sort(linkedkey);

            Iterator it = linkedkey.iterator();
            while (it.hasNext()) {
                String name = (String) it.next();
                String value=get(name);
                out.println(name+"="+value);
            }
        } catch (Exception e) {
        } finally {
            if(out!=null){
                out.close();
            }
        }
    }
}
