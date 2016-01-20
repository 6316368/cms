package org.konghao.basic.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**ClassName:HashMap2ObjUntil
 * Function: 利用jackson把一个hashmap转换成某个对象
 * @author   lh
 * @Date	 2015	2015年8月12日		下午11:06:15
 */
@SuppressWarnings({"rawtypes"})
public class HashMap2ObjUntil {
	public static  HashMap2ObjUntil hashMapToObject;
	private HashMap2ObjUntil(){
	}
	public static HashMap2ObjUntil  getInstance(){
		if(hashMapToObject==null){
			hashMapToObject=new HashMap2ObjUntil();
		}
		return hashMapToObject;
	}
	private static String getjsondata(HashMap<String , String >configbase){
		String string = "{";  
        for (Iterator it = configbase.entrySet().iterator(); it.hasNext();) {  
            Entry e = (Entry) it.next();  
            string += "'" + e.getKey() + "':";  
            string += "'" + e.getValue() + "',";  
        }  
        string = string.substring(0, string.lastIndexOf(","));  
        string += "}";  
        return string;  
	}
	
	/**function(利用jackson把一个hashmap转换成某个对象)
	 * @param  @param configbase 要转换的hashmap
	 * @param  @param cla   要转换成的某个对象
	 * @param  @return      返回这个对象的一个实列
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月12日
	*/
	public Object hashMap2Obj(HashMap<String,String >configbase,Class cla){
		Object  o=null;
		try {
			String getjsondata = HashMap2ObjUntil.getjsondata(configbase);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
			JsonNode df = mapper.readValue(getjsondata, JsonNode.class);
			o=JsonUtil.getInstance().json2obj(df.toString(), cla);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	 

//	// 把一个字符串的第一个字母大写、效率是最高的、
//	 private static String getMethodName(String fildeName) throws Exception{
//	  byte[] items = fildeName.getBytes();
//	  items[0] = (byte) ((char) items[0] - 'a' + 'A');
//	  return new String(items);
//	 }
	public static void main(String[] args) throws Exception {
//		ConfigBase   configbase=new ConfigBase("baseinfo.properties");
//		
//		configbase.put("phone", "77777");
//		configbase.saveConfigfile();
//		BaseInfo b=(BaseInfo)HashMap2ObjUntil.getInstance().hashMap2Obj(configbase, BaseInfo.class);
//	    System.out.println(b.getPhone());;
//		BaseInfo  s=new BaseInfo();
//		getObjectValue();
	}
}

