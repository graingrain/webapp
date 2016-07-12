
<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">
</html>
<html>
  <head>
    <title>alain JSP Error PAGE</title>
  </head>

  <body>
    <h1>alain JSP   PAGE  ERREUR</h1>

    un probleme est survenu, ....

    <%@ page isErrorPage="true"  %>

<%
    if (exception == null) {
	out.println("<p>Sorry no exception<p>");
    }
    else {

%>
    <p>
    The name of the exception was:
    <%= exception.toString() %>
    <p>
    The message of the exception was:
    <%= exception.getMessage() %>
    <p>

	The stack trace was:<br>
	 <pre>
    <% 
	 // damn ugly Java, CLOS was an OO language not this faschist crap.
	 java.io.PrintWriter outstream = new java.io.PrintWriter(out);
         exception.printStackTrace(outstream);
    }
%>
    </pre>
    <hr>
    <address><a href="mailto:alain.grainville@ac-reunion.fr">A. Grainville</a></address>
 
  </body>
</html>