package com.ransa.portal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Esta clase construye y envia un correo electronico a partir de una Mail
 * Session ya existente, con soporte para HTML en el body del mensaje
 * 
 * @author César Bardález Vela
 */
public class Email {
	public static final int PRIORITY_HIGH = 1;
	public static final int PRIORITY_NORMAL = 3;
	public static final int PRIORITY_LOW = 5;

	private static final Address[] ADDRESS_ARRAY = new Address[0];

	private Session session;
	private Collection<Address> to;
	private Collection<Address> cc;
	private Collection<Address> bcc;
	private Address from;
	private Collection<Address> replyTo;
	private String subject;
	private String body;
	private int priority;

	/**
	 * Contruye un correo a partir de un Mail Session existente
	 */
	public Email(Session session) {
		this.session = session;
		to = new ArrayList<Address>();
		cc = new ArrayList<Address>();
		bcc = new ArrayList<Address>();
		replyTo = new ArrayList<Address>();
		priority = PRIORITY_NORMAL;
	}

	/**
	 * Dirección de origen del correo
	 */
	public void setFrom(Address from) {
		this.from = from;
	}

	/**
	 * Coleccion de direcciones a los que se envia el correo
	 */
	public void addTO(Collection<Address> to) {
		this.to.addAll(to);
	}

	/**
	 * Coleccion de direcciones a los que se copia el correo
	 */
	public void addCC(Collection<Address> cc) {
		this.cc.addAll(cc);
	}

	/**
	 * Coleccion de direcciones a los que se copia de manera oculta el correo
	 */
	public void addBCC(Collection<Address> bcc) {
		this.bcc.addAll(bcc);
	}

	/**
	 * Coleccion de direcciones a las que se indica se responda
	 */
	public void addReplyTo(Collection<Address> replyTo) {
		this.replyTo.addAll(replyTo);
	}

	/**
	 * Direccion a la que se envia el correo
	 */
	public void addTO(Address to) {
		this.to.add(to);
	}

	/**
	 * Direccion a la que se copia el correo
	 */
	public void addCC(Address cc) {
		this.cc.add(cc);
	}

	/**
	 * Direccion a la que se copia de manera oculta el correo
	 */
	public void addBCC(Address bcc) {
		this.bcc.add(bcc);
	}

	/**
	 * Direccion a la que se solicita se reponda
	 */
	public void addReplyTo(Address replyTo) {
		this.replyTo.add(replyTo);
	}

	/**
	 * Asunto del correo
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Cuerpo del correo con soporte para HTML
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Envio del correo con los datos seteados.
	 */
	public void send() throws MessagingException {
		MimeMessage message = new MimeMessage(session);

		if (from != null) {
			message.setFrom(from);
		} else {
			throw new NullPointerException();
		}

		if (!to.isEmpty()) {
			message.addRecipients(Message.RecipientType.TO, to.toArray(ADDRESS_ARRAY));
		}

		if (!cc.isEmpty()) {
			message.addRecipients(Message.RecipientType.CC, cc.toArray(ADDRESS_ARRAY));
		}

		if (!bcc.isEmpty()) {
			message.addRecipients(Message.RecipientType.BCC, bcc.toArray(ADDRESS_ARRAY));
		}

		if (!replyTo.isEmpty()) {
			message.setReplyTo(replyTo.toArray(ADDRESS_ARRAY));
		}

		message.setSubject(subject != null ? subject : "");
		message.setHeader("X-Priority", String.valueOf(priority));
		message.setSentDate(new Date());

		BodyPart bodyPart;
		MimeMultipart multipart = new MimeMultipart();

		bodyPart = new MimeBodyPart();
		bodyPart.setContent(body != null ? body : "", "text/html");
		multipart.addBodyPart(bodyPart);

		message.setContent(multipart);

		Transport transport = session.getTransport();
		transport.connect();
		Transport.send(message);
		transport.close();
	}

	/**
	 * Prioridad del correo enviado
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
}
