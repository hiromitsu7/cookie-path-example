package com.example.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SampleServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    res.setContentType("text/html");

    PrintWriter w = res.getWriter();

    w.println("<html><head><style>body {font-size: x-small;} td {font-size: x-small}</style></head><body>");

    writeGeneralInfo(req, w);

    writeCookie(req, w);

    writeHeader(req, w);

    w.println("</body></html>");

    // セッション作成
    req.getSession(true);
    // 別のWAR向けのCookie追加
    Cookie cookie = new Cookie("MYCOOKIE", "aaaaaa");
    cookie.setPath("/foo");
    res.addCookie(cookie);
  }

  private void writeGeneralInfo(HttpServletRequest req, PrintWriter w) {
    w.println("<h3>general info</h3>");
    w.println("<table border=\"1\"><tr><td>name</td><td>value</td></tr>");
    addRow(w, "request URL", req.getRequestURL().toString());
    addRow(w, "servlet path", req.getServletPath());
    addRow(w, "context path", req.getServletContext().getContextPath());

    HttpSession ses = req.getSession(false);
    if (ses != null) {
      addRow(w, "sessionId", ses.getId());
    } else {
      addRow(w, "sessionId", "NULL");
    }
    w.println("</table>");
  }

  private void writeHeader(HttpServletRequest req, PrintWriter w) {
    w.println("<h3>request header</h3>");
    w.println("<table border=\"1\"><tr><td>name</td><td>value</td></tr>");
    Iterator<String> ite = req.getHeaderNames().asIterator();
    while (ite.hasNext()) {
      String name = ite.next();
      String value = req.getHeader(name);
      addRow(w, name, value);
    }
    w.println("</table>");
  }

  private void writeCookie(HttpServletRequest req, PrintWriter w) {
    w.println("<h3>cookie</h3>");
    w.println("<table border=\"1\"><tr><td>name</td><td>value</td></tr>");
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie c : cookies) {
        addRow(w, c.getName(), c.getValue());
      }
    }
    w.println("</table>");
  }

  private void addRow(PrintWriter w, String... str) {
    w.print("<tr>");
    for (String s : str) {
      w.print("<td>" + s + "</td>");
    }
    w.print("</tr>");
  }
}