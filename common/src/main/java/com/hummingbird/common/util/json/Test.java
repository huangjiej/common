package com.hummingbird.common.util.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Test class. This file is not formally a member of the org.json library.
 * It is just a casual test tool.
 */
public class Test {

    /**
     * Entry point.
     * @param args 
     */ 
    public static void main(String args[]) {
    	JSONArray array=new JSONArray();
    	List list=new ArrayList();
    	list.add("风华");
    	Iterator i=list.iterator();
    	JSONObject object=new JSONObject();
    	while(i.hasNext())
    	{
    		String s =(String)i.next();
    		try {
				object.put("id",s);
			} catch (JSONException e) {
				e.printStackTrace();
			}
    		array.put(object);
    	}
    	System.out.println(object.toString());
    	System.out.println(array.toString());
    }
}
