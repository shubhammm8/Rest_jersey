package org.kalinga.Rest2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Query;
import org.hibernate.Session;
import org.kalinga.Rest2.Model.Message;
import org.kalinga.Rest2.Model.hibernateUtility;

@Path("/messages")
public class Main {
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> show(){
		List<Message> lm=new ArrayList<Message>();
		Session session=org.kalinga.Rest2.Model.hibernateUtility.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query q=session.createQuery("FROM Message");
		List l=q.list();
		Iterator i=l.iterator();
		while(i.hasNext()){
			
			Message m=(Message)i.next();
			Message x=new Message();
			x.setId(m.getId());
			x.setFrom(m.getFrom());
			x.setTo(m.getTo());
			x.setMsg(m.getMsg());

			lm.add(x);
			
			System.out.println(m.getId()+"    "+m.getFrom()+"   ");
			
			
		}	
		
		session.getTransaction().commit();
		session.close();
		return lm;
		
	}
	
	@Path("/query")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public String Posting(
			@QueryParam("id") int id,
			@QueryParam("fromm") String from,
			@QueryParam("too") String to,
			@QueryParam("msg") String str){
		
		Session s=hibernateUtility.getSessionFactory().openSession();
		s.beginTransaction();
		Message m=new Message();
		m.setId(id);
		m.setFrom(from);
		m.setMsg(str);
		m.setTo(to);
		
		s.save(m);
		s.getTransaction().commit();
		s.close();
		
		
		

		return "done";

	}
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{id}")
	public Message showOne(@PathParam("id") int id){
		
		Session session=hibernateUtility.getSessionFactory().openSession();
		session.beginTransaction();
	
		Query q=session.createQuery("FROM Message where id=:idd");
		q.setInteger("idd",id);
		Message m=(Message)q.uniqueResult();
	
//		List l=q.list();
//		Iterator i=l.iterator();
//		while(i.hasNext()){
//			
//			Message m=(Message)i.next();
//		
//
//			if(m.getId()==id){
//				return m;
//			}
//			
//			System.out.println(m.getId()+"    "+m.getFrom()+"   ");
//			
//			
//		}	
		
		return m;
	}
	
	
	
	
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	public List<Minds> show(){
//		List<Minds> lm=new ArrayList<Minds>();
//		Session session=org.kalinga.Rest2.Model.hibernateUtility.getSessionFactory().openSession();
//	session.beginTransaction();
//	Query q=session.createQuery("from Minds");
//	List l=q.list();
//	Iterator i=l.iterator();
//	while(i.hasNext()){
//		
//		Minds m=(Minds)i.next();
//		Minds x=new Minds();
//		x.setMid(m.getMid());
//		x.setMname(m.getMname());
//		lm.add(x);
//	}
//		return lm;
//	}
	
	
}
