package utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class SignUtils {

    /**
     * 国通渠道签名
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static String sign(Object obj) throws IllegalAccessException{
        Map<String, Object> params = objectToMap(obj);
        String signStr = SignUtils.sort(params)+GtConstant.KEY;
        return DigestUtils.md5Hex(signStr.getBytes());
    }
    /**
     * 属性赋值
     * @param source
     * @param dest
     * @throws Exception
     */
    public static void Copy(Object source, Object dest) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(),Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();
        try {
            for (int i = 0; i < sourceProperty.length; i++) {
                for (int j = 0; j < destProperty.length; j++) {
                    if (sourceProperty[i].getName().equals(destProperty[j].getName())  && sourceProperty[i].getPropertyType() == destProperty[j].getPropertyType()) {
                        // 调用source的getter方法和dest的setter方法
                        destProperty[j].getWriteMethod().invoke(dest,sourceProperty[i].getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * String排序按字母小写升序
     * @param params
     * @return
     */
    public static StringBuffer stringSort(Map<String,String> params){
        List<String> keys = new ArrayList<String>(params.keySet());
        // 字段排序
        StringBuffer sb = new StringBuffer();
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (StringUtils.isBlank(value)) {
                continue;
            }
            sb.append(value);
        }
        return sb;
    }
    /**
     * map按照key的英文字母排序遍历value
     * @param map
     * @return
     */
    public static String sort(Map<String, Object> map){
        //遍历map修改map的key为小写
        HashMap<String, String> newMap = new HashMap<String, String>();
        for (Entry<String,Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = "";
            try{
                value = entry.getValue().toString();
            }catch(NullPointerException e){

            }
            if (!key.equals(key.toLowerCase())) {
                String newKey = key.toLowerCase();
                newMap.put(newKey, value);
            }else {
                newMap.put(key, value);
            }
        }
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(newMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(val);
                    }
                }

            }
            result = sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
