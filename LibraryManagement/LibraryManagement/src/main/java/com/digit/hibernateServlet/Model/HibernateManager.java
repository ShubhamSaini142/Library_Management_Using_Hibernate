package com.digit.hibernateServlet.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import com.digit.javaTraining.bean.Admin;
import com.digit.javaTraining.bean.Book;
import com.digit.javaTraining.bean.Plans;
import com.digit.javaTraining.bean.PurchaseHistory;

import com.digit.javaTraining.bean.Subscription;
import com.digit.javaTraining.bean.User;

public class HibernateManager {
	public static Session session;
	private int secret_pin;

	private int id;

	private String upwd;

	private int uid;

	private int status;

	public HibernateManager() {

		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

		ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());

		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.buildServiceRegistry());

		session = sessionFactory.openSession();

		System.out.println("Connected to pf..........");
	}

//REMOVE BOOK
	public void removeBook(int bid) {
		Transaction tran = session.beginTransaction();
		Book s = (Book) session.get(Book.class, bid); // value is based on Primary key
		s.setStatus(0);
		session.update(s);

		System.out.println("Delete Success");
		tran.commit();

	}

	// REMOVE USER
	public void removeUser(int user_id) {
		Transaction tran = session.beginTransaction();
		User s = (User) session.get(User.class, user_id); // value is based on Primary key
		s.setStatus(0);
		session.update(s);

		System.out.println("Delete Success");
		tran.commit();

	}

//PURCHASE 
	public boolean purchase(PurchaseHistory a1) {

		Transaction tran = session.beginTransaction();

		Serializable save = session.save(a1);

		tran.commit();

		System.out.println("object saved");
		if (save == null) {
			return false;
		}

		return true;

	}

//
	public boolean saveObj(Object object) {

		Transaction tran = session.beginTransaction();

		Serializable save = session.save(object);

		tran.commit();

		if (save != null) {

			return true;

		}

		return false;

	}

	public static boolean Adminlogin(int adminID, int password) {
		HibernateManager hbm = new HibernateManager();
		hbm.session.beginTransaction();

		Query q = hbm.session.createQuery("FROM Admin where admin_id=:aid and secret_pin=:apin");
		q.setInteger("aid", adminID);
		q.setInteger("apin", password);
		List l = q.list();
		if (l.isEmpty()) {
			return false;
		}
		return true;

	}

	public boolean userLogin(int uids, String pwd) {

		System.out.println(uids);

		Transaction tr = session.beginTransaction();

		Query q = session.createQuery("FROM User where user_id=:uid and pwd=:pwd and status=:status");
		q.setInteger("status", 1);
		q.setInteger("uid", uids);
		q.setString("pwd", pwd);

		List l = q.list();
		if (l.isEmpty()) {
			return false;
		}
		return true;

	}

	public boolean authUsers(int user_id, int status) {

		Transaction transaction = session.beginTransaction();

		User ad = (User) session.get(User.class, user_id);

		ad.setStatus(status);

		session.update(ad);

		System.out.println("Update Success");

		transaction.commit();

		return true;

	}

	public boolean authoriseBook(int bid, int status) {

		Transaction tran = session.beginTransaction();

		Book b = (Book) session.get(Book.class, bid);

		b.setStatus(status);

		session.update(b);

		System.out.println("Update success");

		tran.commit();

		return true;

	}

	public ArrayList<Subscription> readAll() {

		ArrayList<Subscription> a = new ArrayList<Subscription>();
		Transaction trn = session.beginTransaction();
		Query q = session.createQuery("From Subscription");
		List list = q.list();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Subscription s = (Subscription) itr.next();
			int sub_id = s.getSub_id();
			int user_id = s.getUser_id();
			int amount = s.getAmount();
			int invoice = s.getInvoice();
			String date = s.getDate();
			Subscription sub = new Subscription(sub_id, user_id, amount, invoice, date);
			a.add(sub);
		}
		trn.commit();
		return a;
	}

	public static int generateRandomInvoiceNumber() {
		int curGenNum = 0;
		curGenNum = (int) (Math.random() * (99999 - 1000) + 10000);
		return curGenNum;
	}

	public static ArrayList<Subscription> getAllSubscriptions() {
		ArrayList<Subscription> ViewAllSubscriptions = new ArrayList<Subscription>();
		Query ListQuery = session.createQuery("FROM Subscription");
		List list = ListQuery.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Subscription currSub = (Subscription) it.next();
			ViewAllSubscriptions.add(currSub);
		}
		return ViewAllSubscriptions;
	}

	public static ArrayList<Book> getAllBooks() {
		ArrayList<Book> ViewAllBook = new ArrayList<Book>();
		Query ListQuery = session.createQuery("FROM Book");
		List list = ListQuery.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Book currBook = (Book) it.next();
			ViewAllBook.add(currBook);
		}
		return ViewAllBook;
	}

	public static ArrayList<User> getAllUser() {
		ArrayList<User> ViewAllUser = new ArrayList<User>();
		Query ListQuery = session.createQuery("FROM User");
		List list = ListQuery.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			User currUser = (User) it.next();
			ViewAllUser.add(currUser);
		}
		return ViewAllUser;
	}

	public static ArrayList<PurchaseHistory> getAllPurchasesHistory() {
		ArrayList<PurchaseHistory> allPurchaseHistory = new ArrayList<PurchaseHistory>();
		Query allList = session.createQuery("FROM PurchaseHistory");
		List list = allList.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			PurchaseHistory curSub = (PurchaseHistory) it.next();
			allPurchaseHistory.add(curSub);
		}
		return allPurchaseHistory;
	}

	public static boolean purchaseBook(int bookID, int userID) {
		Transaction trn = session.beginTransaction();
		Book book = (Book) session.get(Book.class, bookID);
		User user = (User) session.get(User.class, userID);
		PurchaseHistory curPH = new PurchaseHistory();
		curPH.setBook_id(book.getBid());
		curPH.setBname(book.getBname());
		curPH.setUser_id(user.getUser_id());
		curPH.setAmount(book.getCost());
		curPH.setInvoice(generateRandomInvoiceNumber());
		Serializable save = session.save(curPH);
		trn.commit();
		if(save!=null)
		{
			return true;
		}
		return false;
	}
	


}
