package com.webdm.common.tools.collection;

import java.util.*;

/**
 * Created by fengbingjian on 15/9/11 15:27.
 */
public class CollectionsCreator {

     public static <T> Map<T, T>  createMap(T ... args){

         Map<T, T> map = new HashMap<T, T>();

         int id = 0;
         T key = null;
         for(T tmp: args){

             if(id % 2 == 0)
                 key = tmp;
             else {
                 map.put(key, tmp);
             }
             id++;

         }
         return map;
    }

    public static Map<String, Object>  createObjectMap(Object ... args){

        Map<String, Object> map = new HashMap<String, Object>();

        int id = 0;
        String key = null;
        for(Object tmp: args){

            if(id % 2 == 0)
                key = tmp.toString();
            else {
                map.put(key, tmp);
            }
            id++;

        }
        return map;
    }

    public static <T> List<T> createList(T ... args){

        List<T> list = new ArrayList<T>();

        for(T tmp: args){

            list.add(tmp);

        }
        return list;
    }

    public static <T> Set<T> createSet(T ... args){

        Set<T> set = new HashSet<T>();

        for(T tmp: args){

            set.add(tmp);
        }
        return set;
    }
}
