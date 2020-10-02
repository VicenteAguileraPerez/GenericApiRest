package utility.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author vicenteaguilera
 * @author salvadormorado
 * @author antonio pulido
 * @author samuelbenitez
 */


public class FilesHelper 
{
	private final File path;
	private final File pathKey;
	public FilesHelper(String databasename,String keyname) 
	{
		//String pathFile = "GenericApiRest//WebContent//WEB-INF//lib//";
		path= new File(databasename);// la raiz del proyecto;
		
		if(!keyname.isBlank())
		{
			pathKey= new File(keyname);// la raiz del proyecto;
		}else
		{
			pathKey = null;
		}
	}
	
	public void saveData(String data,boolean mode)
	{
		
		FileWriter writer;
		
		try {
	
			
			writer = new FileWriter(path,mode);// si existe el archivo va a escribir si no va a crear
			writer.write(data);
			writer.write("\n");
			writer.close();	
			
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
			
		
	}
	public String saveData(JSONObject data,boolean mode)
	{
		
		FileWriter writer;
		
		try {
			String id = UUID.randomUUID().toString();
			
			data.put(StaticHelper.ID,id);
			writer = new FileWriter(path,mode);// si existe el archivo va a escribir si no va a crear
			writer.write(data.toString());
			writer.write("\n");
			writer.close();	
			return id;
			
		} 
		catch (IOException e) {
			return null;
			
		}
			
		
	}
	public String saveData(JSONObject data,String operation)
	{
		
		JSONArray jsonArray = readDataJson();
		System.out.println("arreglo"+jsonArray);
		FileWriter writer;
		String id = null;
		try 
		{
			
			writer = new FileWriter(path);
			if(jsonArray!=null)
			{
				for (int i = 0; i < jsonArray.length(); i++)
				{
					
					    JSONObject jsonObject = jsonArray.getJSONObject(i); 
						if(jsonObject.getString(StaticHelper.ID).equals(data.getString(StaticHelper.ID)))
						{
							
							//if updating is updating else is deleting
							if(operation.equals(StaticHelper.UPDATING)) 
							{
								
								writer.write(data.toString());
								writer.write("\n");
								//writer.write(new EncryptationHelper().encryptAES(data.toString()));
							}
							id=data.getString("id");
							
						}
						else
						{
							writer.write(jsonObject.toString());
							writer.write("\n");
							//writer.write(new EncryptationHelper().encryptAES(jsonArray.getJSONObject(i).toString()));
						}
						
						
					}
				writer.close();
			}
			return id;
			
		}
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			
			return id;
		}
			
		
	}
	
	public JSONArray readDataJson()
	{
	
		JSONArray jsonArray = new JSONArray();
		try 
		{
			
			FileReader reader = new FileReader(path);
			BufferedReader readerB = new BufferedReader(reader);
			
			String helper = null;
			
			while(readerB.ready()) 
			{
				//asignacion sobre evaluación y si el resultado se esa linea es difernete de null entonces entra al while
				while((helper=readerB.readLine())!=null)
				{
					
					//System.out.println(helper);
					//EncryptationHelper encryptationHelper=new EncryptationHelper();
					//JSONObject jsonObject = new JSONObject(/*encryptationHelper.decryptAES*/(helper));
					if(helper.isBlank()) 
						break;
				
					JSONHelper jsonObject = new JSONHelper();
					JSONObject jsonObject2= jsonObject.getNewJsonObject(helper);
					jsonArray.put(jsonObject2);
					
				}
			}
			readerB.close();
			reader.close();
			return jsonArray; 
				
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void readData()
	{
	
		
		try 
		{
			
			FileReader reader = new FileReader(path);
			BufferedReader readerB = new BufferedReader(reader);
			
			String helper = null;
			
			while(readerB.ready()) 
			{
				//asignacion sobre evaluación y si el resultado se esa linea es difernete de null entonces entra al while
				while((helper=readerB.readLine())!=null)
				{
					System.out.println(helper);
					new EncryptationHelper().decryptBase64(helper);
					
				}
			}
			
			readerB.close();
			reader.close();
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveKeySecret(String password)
	{
		
		FileWriter writer;
		String keySecret="";
		try 
		{
			if(!pathKey.exists())
			{
				writer = new FileWriter(pathKey);// si existe el archivo va a escribir si no va a crear
				for(int i=password.length();i<16;i++)
				{
					keySecret+=String.valueOf(((char)Math.floor(Math.random()*(125-33+1)+33))); // Valor entre M y N, ambos incluidos.
				}
				writer.write(keySecret);
				writer.write("\n");
				writer.close();
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String readKeySecret()
	{
		String helper = "";
		try 
		{
			
			FileReader reader = new FileReader(pathKey);
			BufferedReader readerB = new BufferedReader(reader);
			
			
			
			if(readerB.ready()) 
			{
				//asignacion sobre evaluación y si el resultado se esa linea es difernete de null entonces entra al while
				if((helper=readerB.readLine())!=null)
				{
					System.out.println(helper);
					
				}
			}
		
			readerB.close();
			return  helper;
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return helper;
		}
		
	}
	
	@Deprecated
    public void backup(File pathSaveKey)
    {
    	if(pathSaveKey.mkdir())
		{
    		File fileSaturno = new File(pathSaveKey, path.getName());
    		if(copyFiles(path.getAbsolutePath(),fileSaturno.getAbsolutePath()))
    		{
    			File fileKeys = new File(pathSaveKey, pathKey.getName());
        		if(copyFiles(pathKey.getAbsolutePath(),fileKeys.getAbsolutePath()))
        	    {
	    			JOptionPane.showMessageDialog(null, "The information was copy to "+pathSaveKey.getAbsolutePath());
        		}
    		}
    		
		}
    }
	@Deprecated
    public boolean copyFiles(String fromFile, String toFile)
    {
    	InputStream inputStream = null;
        OutputStream outputStream = null;
        try 
        {

			inputStream = new FileInputStream(fromFile);
	        outputStream = new FileOutputStream(toFile);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = inputStream.read(buffer)) > 0) {
	            outputStream.write(buffer, 0, length);
	        }
	        inputStream.close();
	        outputStream.close();
	        System.out.println("Archivo copiado.");
	        return true;
	    }
        catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
    }
}
