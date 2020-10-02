package controllers;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import utility.helpers.FilesHelper;
import utility.helpers.JSONHelper;
import utility.helpers.StaticHelper;

@WebServlet("/students")
public class Students extends HttpServlet 
{

	private FilesHelper files = new FilesHelper(StaticHelper.DATABASENAME, "");
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String page = request.getParameter(StaticHelper.PAGE);
		String idStudent = request.getParameter(StaticHelper.ID);
		response.setContentType("text/json");
		if(page!=null && page.equals(StaticHelper.STUDENTS) && idStudent==null)
		{
			
			
			
			try
			{
				JSONArray jsonArray = files.readDataJson();
				
				if(jsonArray.isEmpty()) 
				{
					response.sendError(HttpServletResponse.SC_NO_CONTENT);
				}
				else
				{
					response.getWriter().println(jsonArray);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("It has a mistake");
				files=null;
				response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		}
		else if(page!=null && page.equals(StaticHelper.STUDENT))
		{
			
			try
			{
				
				if(idStudent!=null && !idStudent.isBlank()) 
				{
					System.out.println("params"+page+" "+idStudent);
					JSONArray jsonArray = new JSONHelper().searchInformation(files.readDataJson(), idStudent, "id");
					if(jsonArray.isEmpty()) 
					{
						response.sendError(HttpServletResponse.SC_NO_CONTENT);
					}
					else
					{
						System.out.println("Student" + jsonArray);
						response.getWriter().println(jsonArray);
					}
					
				}
				else 
				{
					response.sendError(HttpServletResponse.SC_NO_CONTENT);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("It has a mistake");
				response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
				files=null;
			}
			
			
			
		}
		else
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			//getServletContext().getRequestDispatcher("/notFound.jsp").forward(request, response);
			System.out.println("Error");
			//response.setContentType("text/html");
			//response.getWriter().println("<h1>Hello</h1>");
		}
		
		
		// obtener todos los estudiantes
		
		

		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//No id wee need to send all the data and return id
		//200 o 500
		
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			System.out.println(data);
			response.setContentType("text/html");
		
		
			JSONHelper jsonHelper = new JSONHelper();
			JSONObject jsonObject = jsonHelper.getNewJsonObject(data);
			System.out.println(jsonObject);
			String id = files.saveData(jsonObject,  true);
			
			if(id!=null)
			{
				//response.getWriter().println("<h3>Successful register of Student the id is: "+id+"</h3>");
				response.sendError(HttpServletResponse.SC_OK);
				
			}
			else {
				//response.getWriter().println("<h3>Failed the registration of the student</h3>");
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				
			}
			
		}
		catch(Exception e) 
		{
			
			System.out.println("It has a mistake");
	        
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			
		}
		
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//No id wee need to send all the data and return id
		//200 o 500
		
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			System.out.println(data);
			response.setContentType("text/html");
		
		
			JSONHelper jsonHelper = new JSONHelper();
			JSONObject jsonObject = jsonHelper.getNewJsonObject(data);
			System.out.println(jsonObject);
			String id = files.saveData(jsonObject,StaticHelper.UPDATING);
			
			if(id!=null)
			{
				response.getWriter().println("<h3>Successful updating of Student the id is: "+id+"</h3>");
				
			}
			else {
				response.getWriter().println("<h3>Failed the updating of the student</h3>");
				
			}	
		}
		catch(Exception e) 
		{
			
			System.out.println("It has a mistake");
	        
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			
		}
		
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//No id wee need to send all the data and return id
		//200 o 500
		
		try {
			String data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			
			System.out.println(data);
			response.setContentType("text/html");
		
		
			JSONHelper jsonHelper = new JSONHelper();
			JSONObject jsonObject = jsonHelper.getNewJsonObject(data);
			System.out.println(jsonObject);
			String id = files.saveData(jsonObject,StaticHelper.DELETING);
			
			if(id!=null)
			{
				response.getWriter().println("<h3>Successful deleting of Student the id is: "+id+"</h3>");
				
			}
			else {
				response.getWriter().println("<h3>Failed the deleting of the student</h3>");
				
			}	
		}
		catch(Exception e) 
		{
			
			System.out.println("It has a mistake");
	        
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			
		}
		
	}
}

/** parte del menu
 *if(page!=null && page.equals("students"))
		{
			
			getServletContext().getRequestDispatcher("/students.jsp").forward(request, response);
		}
		else if(page!=null && page.equals("student"))
		{
			getServletContext().getRequestDispatcher("/student.jsp").forward(request, response);
			
		}
		else
		{
			getServletContext().getRequestDispatcher("/notFound.jsp").forward(request, response);
			
			//response.setContentType("text/html");
			//response.getWriter().println("<h1>Hello</h1>");
		}
 */
