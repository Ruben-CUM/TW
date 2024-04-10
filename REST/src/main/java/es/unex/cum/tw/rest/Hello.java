package es.unex.cum.tw.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/HolaMundo")
public class Hello {
// Texto
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey (TEXTO)";
	}

// XML
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey (XML)" + "</hello>";
	}

// HTML
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey (HTML)"
				+ "</body></h1>" + "</html> ";
	}

// JSON
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayJSONHello() {
		return "HelloWorld";
	}
}