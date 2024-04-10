package es.unex.cum.tw.basico;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Ejemplo para ver como capturar los parámetros cuando estos
 * son conocidos (param1, param2 y param3)
 */
public class Parametros extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,
            IOException {

        PrintWriter out;
        String title = "Leyendo 3 parámetros";

        response.setContentType("text/html");
        out = response.getWriter();
        out.println("<HTML><HEAD><TITLE>");
        out.println(title);


        out.println("</TITLE></HEAD><BODY>");
        out.println("<H1 ALIGN=CENTER>" + title + "</H1>");
        out.println("<UL>");
        out.println("<LI>param1: " + request.getParameter("param1"));
        out.println("<LI>param2: " + request.getParameter("param2"));
        out.println("<LI>param3: " + request.getParameter("param3"));
        out.println("</UL>");
        out.println("</BODY></HTML>");
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
// </editor-fold>
}
