package clienteserver;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

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
		File directory = new File("./" + email.getRecipient()); 
		File arq = new File(directory, email.getId()+".txt");
		if(!directory.exists()){
			boolean statusdir = directory.mkdir();
			if (statusdir){
				System.out.println("O diretório foi criado para "+ email.getRecipient());
			}else{
				System.out.println("Erro! O diretório não foi criado para "+ email.getRecipient());
			}
		}
		try{
			if(!arq.exists()){
				arq.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(arq, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			printWriter.println(email.getId());
			printWriter.println(email.getSender());
			printWriter.println(email.getRecipient());
			printWriter.println(email.getSubject());
			printWriter.println(email.getBody());

			//Libera o arquivo para escrita
			printWriter.flush();

            //No final precisamos fechar o arquivo
			printWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public ArrayList<Email> list(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Email email) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	

}
