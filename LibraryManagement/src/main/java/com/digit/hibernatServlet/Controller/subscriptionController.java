package com.digit.hibernatServlet.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.digit.hibernateServlet.Model.HibernateManager;
import com.digit.javaTraining.bean.Plans;
import com.digit.javaTraining.bean.Subscription;

@WebServlet("/subcription")
public class subscriptionController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int sid = Integer.parseInt(req.getParameter("sid"));
		Plans plan = new Plans();
		int amount = plan.Amount(sid);
		HttpSession session = req.getSession();
		int curUser = (int) session.getAttribute("curUser");
//		System.out.println(curUser);
		HibernateManager hbm = new HibernateManager();
		int ranNumber = hbm.generateRandomInvoiceNumber();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);
		Subscription sub = new Subscription(sid, curUser, amount, ranNumber, date);
	boolean x =	hbm.saveObj(sub);
	if(x==true) {
		resp.sendRedirect("SuccessForUser.html");
	}
	else {
		resp.sendRedirect("FailForUser.html");
	}
		

	}

}
