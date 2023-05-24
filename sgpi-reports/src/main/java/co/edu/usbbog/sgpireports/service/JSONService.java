package co.edu.usbbog.sgpireports.service;

import org.json.JSONObject;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class JSONService {
	
	public List<String> getList (String JsonString){
		List<String> list = new ArrayList<String>();  
		JSONObject jsonObject = new JSONObject(JsonString);
		JSONArray jsonArray = jsonObject.getJSONArray("actividades"); 
		if (jsonArray != null) { 
		   int len = jsonArray.length();
		   for (int i=0;i<len;i++){ 
		    list.add(jsonArray.get(i).toString());
		   } 
		} 
		return list;
	}
	
}
