package com.example.webchatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	 
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	//static so this set is unique
    public static Set<String> rooms = new HashSet<>();
    /**
     * Method generates unique room codes
     * **/
    public String generatingRandomUpperAlphanumericString(int length) {
        String generatedString = RandomStringUtils.randomAlphanumeric(length).toUpperCase();
        // generating unique room code
        while (rooms.contains(generatedString)){
            generatedString = RandomStringUtils.randomAlphanumeric(length).toUpperCase();
        }
        rooms.add(generatedString);

        return generatedString;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter printwriter= response.getWriter();
		HttpSession httpsession = request.getSession(true);
		String room =request.getParameter("room");
		if(room.equals("")|| room==null)
		{
			room = generatingRandomUpperAlphanumericString(6);
			rooms.add(room);
		}
		String username = request.getParameter("username");
		httpsession.setAttribute("username", username);
		httpsession.setAttribute("rooms", rooms);
		if(username!=null)
		{
			response.sendRedirect("index.jsp?username="+username+"&room="+room);
		}
		
		
	}

}
