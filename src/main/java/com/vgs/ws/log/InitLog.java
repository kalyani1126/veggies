package com.vgs.ws.log;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Santosh.Kumar 
 * Date : Oct 28, 2016
 */
public class InitLog extends HttpServlet implements ServletContextListener, Filter, HttpSessionListener {

    public static final String SESSION_ID = "sessionId";

    public static final String IP_ADDRESS = "IP";

    /**
     * The response content type: text/html
     */
    private static final String CONTENT_TYPE = "text/html";

    /**
     * The root appender.
     */
    private static final String ROOT = "Root";

    /**
     * The name of the class / package.
     */
    private static final String CLASS = "CLASS";

    /**
     * The logging level.
     */
    private static final String LEVEL = "LEVEL";

    //mandatory for reflexion
    public InitLog() {
        super();
    }

   
    @Override
    public void init()
            throws ServletException {
        InitShutdownController.initializeLog4j(this.getServletContext());
        System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");
        System.setProperty("snmp4j.LogFactory", "org.snmp4j.log.Log4jLogFactory");
        Logger operationLog = Logger.getLogger("com.nab.wsg.prov.oprlog");
        operationLog.warn("Webapp " + getServletContext().getServletContextName() + " starting");
    }

    /**
     * J2EE initializer
     *
     * @param parm1
     */
    @Override
    public void contextInitialized(ServletContextEvent parm1) {
        InitShutdownController.initializeLog4j(parm1.getServletContext());
        parm1.getServletContext().log(" Log4J context Initialized");
        Logger operationLog = Logger.getLogger("com.nab.wsg.prov.oprlog");
        operationLog.warn("Webapp " + parm1.getServletContext().getServletContextName() + " starting");
    }


    @Override
    public void contextDestroyed(ServletContextEvent parm1) {
        Logger operationLog = Logger.getLogger("com.nab.wsg.prov.oprlog");
        operationLog.warn("Webapp " + parm1.getServletContext().getServletContextName() + " shutdown");
        InitShutdownController.shutdownLog4j(parm1.getServletContext());
        parm1.getServletContext().log(" Log4J context Destroyed");
    }


    //Clean up resources
    @Override
    public void destroy() {
        // This crashes at Tomcat shutdown:
//        InitShutdownController.shutdownLog4j(this.getServletContext());
    }

    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter out = response.getWriter();
        List loggers = getSortedLoggers();
        Logger logger = null;
        String loggerName = null;
        int loggerNum = 0;

        // print title and header
        printHeader(out, request);

        out.println("<table width=\"50%\" border=\"1\">");
        out.println("<tr BGCOLOR=\"#5991A6\">");
        out.println("<td><FONT  COLOR=\"BLACK\" FACE=\"Helvetica\"><B>Class</B></FONT></td>");
        out.print("<td><FONT  COLOR=\"BLACK\" FACE=\"Helvetica\"><B>Level</B></FONT>");
        out.println("</td>");
        out.println("</tr>");

        // print the root Logger
        displayLogger(out, Logger.getRootLogger(), loggerNum++);

        // print the rest of the loggers
        Iterator ii = loggers.iterator();

        while (ii.hasNext()) {
            displayLogger(out, (Logger) ii.next(), loggerNum++);
        }

        out.println("</table>");
        out.println("<FONT SIZE=\"-3\" COLOR=\"BLACK\" FACE=\"Helvetica\">* " +
                "Inherits LEVEL From Parent.</FONT><BR>");
        out.println("<A href=\"" + request.getRequestURI() + "\">Refresh</A><HR>");

        // print set options
        out.println("<FORM action=\"" + request.getRequestURI() + "\" method=\"post\">");
        out.println("<FONT  SIZE=\"+2\" COLOR=\"BLACK\" FACE=\"Helvetica\"><U>" + "Set Log4J Option</U><BR><BR></FONT>");
        out.println("<FONT COLOR=\"BLACK\" FACE=\"Helvetica\">");
        out.println("<table width=\"50%\" border=\"1\">");
        out.println("<tr BGCOLOR=\"#5991A6\">");
        out.println("<td><FONT COLOR=\"BLACK\" " + "FACE=\"Helvetica\"><B>Class Name:</B></FONT></td>");
        out.println("<td><SELECT name=\"CLASS\">");
        out.println("<OPTION VALUE=\"" + ROOT + "\">" + ROOT + "</OPTION>");

        ii = loggers.iterator();

        while (ii.hasNext()) {
            logger = (Logger) ii.next();
            loggerName = (logger.getName().equals("") ? "Root" : logger.getName());
            out.println("<OPTION VALUE=\"" + loggerName + "\">" + loggerName + "</OPTION>");
        }

        out.println("</SELECT><BR></td></tr>");

        // print logging levels
        printLevelSelector(out);

        out.println("</table></FONT>");
        out.println("<input type=\"submit\" name=\"Submit\" value=\"Set Option\"></FONT>");
        out.println("</FORM>");
        out.println("</body></html>");

        out.flush();
        out.close();
    }


    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String className = request.getParameter(CLASS);
        String level = request.getParameter(LEVEL);

        if (className != null) {
            setClass(className, level);
        }

        doGet(request, response);
    }


    //initialize log4j to log session id
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpSession currentSession = ((HttpServletRequest) request).getSession(false);
        if (currentSession != null) {
            MDC.put(SESSION_ID, currentSession.getId());
        } else {
            MDC.put(SESSION_ID, "");
        }
        MDC.put(IP_ADDRESS, request.getRemoteAddr());
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(SESSION_ID);
            MDC.remove(IP_ADDRESS);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        MDC.put(SESSION_ID, se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        MDC.put(SESSION_ID, "");
    }


    /**
     * Print a Logger and its current level.
     *
     * @param out     the output writer.
     * @param logger  the logger to output.
     * @param row     the row number in the table this logger will appear in.
     *
     */
    private void displayLogger(PrintWriter out, Logger logger, int row) {
        String color = null;
        String loggerName = (logger.getName().equals("") ? ROOT : logger.getName());

        color = ((row % 2) == 1) ? "#E1E1E1" : "#FBFBFB";

        out.println("<tr BGCOLOR=\"" + color + "\">");
        out.println("<td><FONT SIZE=\"-2\" COLOR=\"BLACK\" FACE=\"Helvetica\">" + loggerName + "</FONT></td>");
        out.println("<td><FONT SIZE=\"-2\" COLOR=\"BLACK\" FACE=\"Helvetica\">" +
                ((logger.getLevel() == null) ? (logger.getEffectiveLevel().toString() + "*") :
                        logger.getLevel().toString()) + "</FONT></td>");
        out.println("</tr>");
    }


    /**
     * Set a logger's level.
     *
     * @param className class name of the logger to set.
     * @param level     the level to set the logger to.
     * @return String return message for display.
     */
    private synchronized String setClass(String className, String level) {
        Logger logger = null;

        try {
            logger = (className.equals(ROOT)) ? Logger.getRootLogger() : Logger.getLogger(className);

            logger.setLevel(Level.toLevel(level));
        } catch (Exception e) {
            System.out.println("ERROR Setting LOG4J Logger:" + e);
        }

        return "Message Set For " + (logger.getName().equals("") ? ROOT : logger.getName());
    }


    /**
     * Get a sorted list of all current loggers.
     *
     * @return List the list of sorted loggers.
     */
    private List<Logger> getSortedLoggers() {
        Enumeration<Logger> enumLoggers = LogManager.getCurrentLoggers();
        Comparator<Logger> comp = new LoggerComparator();
        List<Logger> list = new ArrayList<Logger>();

        // Add all current loggers to the list
        while (enumLoggers.hasMoreElements()) {
            list.add(enumLoggers.nextElement());
        }

        // sort the loggers
        Collections.sort(list, comp);

        return list;
    }


    /**
     * Prints the page header.
     *
     * @param out     The output writer
     * @param request The request
     */
    private void printHeader(PrintWriter out, HttpServletRequest request) {
        out.println("<html><head><title>Log4J Control Console</title></head>" + "<body><H3>Log4J Control Console</H3>");
        out.println("<A href=\"" + request.getRequestURI() + "\">Refresh</A><HR>");
    }


    /**
     * Prints the Level select HTML.
     *
     * @param out The output writer
     */
    private void printLevelSelector(PrintWriter out) {
        out.println("<tr BGCOLOR=\"#5991A6\"><td><FONT COLOR=\"BLACK\" " +
                "FACE=\"Helvetica\"><B>Level:</B></FONT></td>");
        out.println("<td><SELECT name=\"" + LEVEL + "\">");
        out.println("<OPTION VALUE=\"" + Level.OFF + "\">" + Level.OFF + "</OPTION>");
        out.println("<OPTION VALUE=\"" + Level.FATAL + "\">" + Level.FATAL + "</OPTION>");
        out.println("<OPTION VALUE=\"" + Level.ERROR + "\">" + Level.ERROR + "</OPTION>");
        out.println("<OPTION VALUE=\"" + Level.WARN + "\">" + Level.WARN + "</OPTION>");
        out.println("<OPTION VALUE=\"" + Level.INFO + "\">" + Level.INFO + "</OPTION>");
        out.println("<OPTION VALUE=\"" + Level.DEBUG + "\">" + Level.DEBUG + "</OPTION>");
        out.println("<OPTION VALUE=\"" + Level.ALL + "\">" + Level.ALL + "</OPTION>");
        out.println("</SELECT><BR></td></tr>");
    }


    /**
     * Compare the names of two <code>Logger</code>s. Used for sorting.
     */
    private class LoggerComparator implements Comparator<Logger> {

        @Override
        public int compare(Logger logger1, Logger logger2) {
            String logger1Name = "";
            String logger2Name = "";
            if (logger1 != null) {
                logger1Name = (logger1.getName().equals("") ? ROOT : logger1.getName());
            }
            if (logger2 != null) {
                logger2Name = (logger2.getName().equals("") ? ROOT : logger2.getName());
            }
            return logger1Name.compareTo(logger2Name);
        }

        /**
         * Return <code>true</code> if the <code>Object</code> is a
         * <code>LoggerComparator</code> instance.
         */
        @Override
        public boolean equals(Object o) {
            return (o instanceof LoggerComparator);
        }

        /**
         * Returns the parent's hashcode.
         *
         * @return int The hashcode value
         */
        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

}
