package utility.helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 
 * @author vicenteaguilera
 * @author salvadormorado
 * @author antonio pulido
 * @author samuelbenitez
 */

public class JSONHelper 
{
	private JSONObject data;
	
	/**
	 * This method set an object type  JSONObject
	 * @param data is a JSONObject
	 */
	public void setJSONObject(JSONObject data)
	{
	
		this.data=data;
	}
	/**
	 * 
	 * @return the JSONObject setter by the method setJSONObject(JSONObject data)
	 * @see #setJSONObject(JSONObject)
	 */
	public JSONObject getJsonObject()
	{
		return data;
	}
	/**
	 * Creates JSONObjects
	 * @return the JSONObject generated
	 */
	public JSONObject getNewJsonObject() 
	{
	   
		return new JSONObject();
	}
	/**
	 * Creates JSONObjects with information of other JSONObjet
	 * @return the JSONObject generated
	 */
	public JSONObject getNewJsonObject(JSONObject data) 
	{
	   
		return new JSONObject(data.toString());
	}
	/**
	 * Creates JSONObjects with information of String
	 * @return the JSONObject generated
	 */
	public JSONObject getNewJsonObject(String data) 
	{
	   
		return new JSONObject(data);
	}

  
   
   
    
    //solo muestra las keys
    public void getKeys(JSONObject data)
    {
    	 Iterator<String> iteratorKeys= data.keys();
    	 
    	 while(iteratorKeys.hasNext()) 
    	 {
    		 String header = iteratorKeys.next();
    		 System.out.println(header);
    	 }
    	 
     }
    public JSONArray getValuesJSONArray()
    {
    	JSONArray jsonArrayData = new JSONArray();
    	EncryptationHelper encryptationHelper = new EncryptationHelper();
    	try
    	{
    		FileReader reader = new FileReader("saturno.bat");
    		//buffer: asignación de memoria dada para almacenar datos. Apunto a mi archivo y el buffer me ayuda a almacenar todos los datos de manera secuencial
    		//para yo leerlos o darles un trato o de algun lado
    		//entre mas grande sea, mas datos puedo manupular
    	    BufferedReader bufferedReader = new BufferedReader(reader);
    	    String line = "";
    	    while(bufferedReader.ready())
    	    {
    	    	while((line = bufferedReader.readLine())!=null)
    	    	{
    	    		jsonArrayData.put(new JSONObject(encryptationHelper.decryptAES(line)));
    	    	}
    	    }
    	    bufferedReader.close();
    	}
    	catch(FileNotFoundException ex)
    	{
    		jsonArrayData = null;
    		System.out.println("Something happen come here");
    	}
    	catch(IOException ioe)
    	{
    		jsonArrayData = null;
    		System.out.println("Something horrible happen come here");
    	}
    	
    	
    	
    	return jsonArrayData;
    }
  
  	
  	//todas las keys en el array list
  	public ArrayList<String> returnKey(JSONObject data)
  	{
  		ArrayList <String> headers  = new ArrayList<>();
  		Iterator<String> iteratorKeys= data.keys();

	   	 while(iteratorKeys.hasNext()) 
	   	 {
	   		 headers.add(iteratorKeys.next());
	   	 }
  		
  		return headers;
  	}
  	public String[] returnKey(ArrayList <String> data)
  	{
  		String headers[]  = new String[data.size()];
  		

	   	 for (int i = 0; i < headers.length; i++)
	   	 {
	   		 headers[i]=data.get(i);
	   	 }
  		
  		return headers;
  	}
    //accion polimorfica
  	public String [][] returnBodyAll(ArrayList<String> returnKey,JSONArray data, String... buttons)
  	{ 
  		String result[][]= null;
  		
  		if(buttons!=null && data!=null)
  		{
	
	  		result=new String[data.length()][returnKey.size()+buttons.length];
			
			for (int i = 0; i <data.length(); i++)
			{
				JSONObject jsonObjectRow = data.getJSONObject(i);
				int getButtonPosition=0;
				// mapeo cuando se requieren obtener los datos de dos estructuras
				for (int j = 0; j < returnKey.size()+buttons.length; j++) 
				{
					if(j<returnKey.size())
					{
						if(jsonObjectRow.has(returnKey.get(j)))
					    {
							result[i][j]= jsonObjectRow.getString(returnKey.get(j));
						}
						
					}
					else
					{
						result[i][j]= buttons[getButtonPosition];
						getButtonPosition++;
					}
				}
	        }
  		}
  		else if(data!=null)
  		{
  			result=new String[data.length()][returnKey.size()];
			
			for (int i = 0; i <data.length(); i++)
			{
				JSONObject jsonObjectRow = data.getJSONObject(i);
				// mapeo cuando se requieren obtener los datos de dos estructuras
				for (int j = 0; j < returnKey.size(); j++) 
				{
					
						result[i][j]= jsonObjectRow.get(returnKey.get(j)).toString();
					
				}
	        }
  		}
  		
  		
  		
  		return result;
  	}
  	
  	public JSONArray forEach(JSONArray defaultData,String keyFilter)
  	{
  		
  		try
  		{
  		
  			JSONArray jsonArrayCopied=new JSONArray();
  			if(keyFilter!=null)
  			{
  				defaultData.forEach(item->
  		   		{
  		   			
  		   			JSONObject jsonObject = (JSONObject) item;
  		   			if(jsonObject.getString(keyFilter).equals("true"))
  		   			{
  		   				jsonArrayCopied.put(item);
  		   			}
  		   			
  		  		});
  			}
  			else
  			{
  				defaultData.forEach(item->
  		   		{
  		   			
  		   				jsonArrayCopied.put(item);
  		   			
  		  		});
  			}
	   		
	   		return jsonArrayCopied;
  		}
  		catch (Exception e) {
  			return null;
		}
  
  		
  	}
  	public JSONArray searchInformation(JSONArray defaultData, String data,String keyFilter)
  	{
  		JSONArray jsonArrayFilter = new JSONArray();
  		
  		defaultData.forEach(item->{
  		
  			if(item.toString().contains(data))
  			{
  				if(keyFilter!=null)
  	  			{
  						
  	  		   			JSONObject jsonObject = (JSONObject) item;
  	  		   			
  	  		   			if(jsonObject.getString(keyFilter).equals(data))
  	  		   			{
  	  		   				jsonArrayFilter.put(item);
  	  		   			}
  	  		   			
  	  			}
  	  			else
  	  			{
	
  	  				jsonArrayFilter.put(item);
  	  		   			
  	  			}
  					
	   			
  			}
  		});
  		return  jsonArrayFilter;
  	}
  	
    

}
