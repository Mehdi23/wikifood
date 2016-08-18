package com.wikifood.util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
    static {
    	//Session session = HibernateUtil.getSessionFactory().openSession();
        //session.beginTransaction();
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // ...
    }
}
