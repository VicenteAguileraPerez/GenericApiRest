package utility.helpers;

import java.util.regex.Pattern;


/**
 * 
 * @author vicenteaguilera
 * @author salvadormorado
 * @author antonio pulido
 * @author samuelbenitez
 */

public class StringHelper 
{
	
	
	private enum Dominios
	{
		GMAIL_COM("gmail.com"),
		HOTMAIL_COM("hotmail.com"),
		OUTLOOK_COM("outlook.com"),
		OUTLOOK_ES("outlook.es"),
		ICLOUD_COM("icloud.com"),
		TECURUAPAN_EDU_MX("tecuruapan.edu.mx"),
		ITSURUAPAN_EDU_MX("itsuruapan.edu.mx"),
		IBM_COM("ibm.com");

		private String email;
		Dominios(String email) {
			// TODO Auto-generated constructor stub
			this.email=email;
		}
		public String dominio()
		{
			return email;
		}
	}
	
	
	/**
	 * 
	 * @param text
	 * @param limit
	 * @return true if the length of the text is equal to the limit
	 */
    private boolean symbolsIsEqual(String text,int limit)
    {
    	return text.length()==limit;	
    }
    /**
	 * 
	 * @param text
	 * @param limit
	 * @return true if the length of the text is greater than or equal to the limit
	 */
    private boolean symbolsIsGreaterThanOREqual(String text,int limit)
    {
    	return text.length()>=limit;
    }
	
	public boolean justNumbers(String text,int limit) 
	{
		String EXPNUM="(\\d{"+limit+"})";
		
		return Pattern.matches(EXPNUM, text);
	}
	public boolean justLetters(String text,int limit) 
	{
		String EXPNUM="(([a-zA-Z]|\\s){1,"+limit+"})";
		
		return Pattern.matches(EXPNUM, text);
	}
	public boolean justLettersUpper(int limit,String text) 
	{
		String EXPNUM="(([A-Z]|\\s){1,"+limit+"})";
		
		return Pattern.matches(EXPNUM, text);
	}
	
	public String CapitalLetterEachWordStatic(String text) 
	{
		text=text.trim();
		String words[] = text.split(" ");
		text="";
		int i=0;
		for (String string : words) 
		{
			words[i]=String.valueOf(string.charAt(0)).toUpperCase()+string.substring(1).toLowerCase();
			text+=words[i]+" ";
			i++;
		}
		return text.trim();
	}
	
	public void firstCapitalLetter(String text) 
	{
		text=text.toLowerCase();
		text=text.substring(0,1).toUpperCase().concat(text).substring(1);
	}
	
	
	public boolean email(String email)
	{
		// dominio debe de tener por lo menos 3 caracteres. internacionalizacion y 2 para la nacionalidad@XXX.XXX.XX 2 o mas
		if(email.contains("@"))
		{
			String parts[]=email.split("@");
			if(parts.length==2)
			{
				if(Dominios.IBM_COM.dominio().equals(parts[1])           ||
				   Dominios.GMAIL_COM.dominio().equals(parts[1])         || 
				   Dominios.ICLOUD_COM.dominio().equals(parts[1])        ||
				   Dominios.OUTLOOK_ES.dominio().equals(parts[1])        || 
				   Dominios.HOTMAIL_COM.dominio().equals(parts[1])       || 
				   Dominios.OUTLOOK_COM.dominio().equals(parts[1])       || 
				   Dominios.ITSURUAPAN_EDU_MX.dominio().equals(parts[1]) ||
				   Dominios.TECURUAPAN_EDU_MX.dominio().equals(parts[1]) )
				{
					return true;
				}
				else if(emailAnother(parts[1]))
				{
					return true;
				}
			}
		}
		
		return false;
	
	}
	private boolean emailAnother(String email)
	{
		if(email.contains("."))
		{
			
			String parts[]=email.split("\\.");
			if(parts.length==2)
			{
				if(symbolsIsGreaterThanOREqual(parts[0], 3) && symbolsIsGreaterThanOREqual(parts[1], 2) ) 
				{
						 return true;
				}
				return false;
			}
			else if(parts.length==3)
			{
				if(symbolsIsGreaterThanOREqual(parts[0], 3)  && symbolsIsGreaterThanOREqual(parts[1], 2) && symbolsIsEqual(parts[2],2)) 
				{
						 return true;
				}
				return false;
			}
			
		}
		return false;
		
		
	}
	// url
	public boolean URL(String url)
	{
		if(url.contains("."))
		{
			String parts[]=url.split("\\.");//www.google.com
			if(parts.length==3)
			{
				System.out.println("tengo 3");
				if(parts[0].equals("www") &&  !parts[1].equals("") && parts[2].length()>=2 && parts[2].length()<=4) 
				{
					System.out.println(parts[0]+" "+parts[1]+" "+parts[2]);
				    return true;
				}
				return false;
			}
			else if(parts.length==4)//www.google.com.mx
			{
				System.out.println("tengo 4");
				System.out.println(parts[0]+"\n"+parts[1]+"\n"+parts[2]+"\n"+parts[3]);	
				if(parts[0].equals("www") &&  !parts[1].equals("") && parts[2].length()>=2 && parts[2].length()<=4 && parts[3].length()==2) 
				{
					
				    return true;
				}
				return false;
			}
			
		}
		return false;
	}
	public boolean verifyPassword(String text1, String text2) {
		if(text1.equals(text2)) {
			return true;
		}else {
			return false;
		}
	}
	public boolean date(String date)
	{
		String EXPNUM="(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/\\d{2}";
		
		return Pattern.matches(EXPNUM, date);
		
	}
	
}

