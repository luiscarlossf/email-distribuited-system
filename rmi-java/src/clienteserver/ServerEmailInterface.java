package clienteserver;
import clienteserver.Email;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerEmailInterface extends Remote{

	public void send(Email email) throws RemoteException;
	public ArrayList<Email> list(String username) throws RemoteException;
	public void delete(Email email) throws RemoteException;

}
