package clienteserver;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

public interface ServerEmailInterface extends Remote{

	public void send(Email email) throws RemoteException;
	public ArrayList<Email> list(String username) throws RemoteException;
	public void delete(Email email) throws RemoteException;

}
