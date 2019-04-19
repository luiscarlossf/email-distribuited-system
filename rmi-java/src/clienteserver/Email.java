package clienteserver;
import java.io.Serializable;;

public class Email implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double id;
	private String sender;
	private String recipient;
	private String subject;
	private String body;
	
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
