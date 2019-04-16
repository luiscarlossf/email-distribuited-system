package clienteserver;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.io.File;

public class ServerEmail extends UnicastRemoteObject implements ServerEmailInterface{

	/**
	 * Manipulação de arquivos
	 * http://www.mballem.com/post/manipulando-arquivo-txt-com-java/?i=1
	 */
	private static final long serialVersionUID = 1L;
	
    public ServerEmail() throws RemoteException {
    	super();
    }
    
	@Override
	public void send(Email email) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Email> list() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Email email) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	

}
