import EmailServer.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;


class EmailImpl extends EmailInterfacePOA{
    private ORB orb;

    public void setORB(ORB orb_val){
        orb = orb_val;
    }

    public void send(Email email){
        System.out.println("Enviando email...");
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

			//Set o id com a hora atual em millisegundos
			email.setId(System.currentTimeMillis());

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

    public void delete(Email email){
        System.out.println("Deleletando email...");
		File directory = new File("./" + email.getRecipient());
		double idEmail = email.getId();
		if(directory.exists()){
			File file = new File(directory, email.getId()+".txt");
			if(file.delete()){
				System.out.println("O email "+ idEmail + " foi apagado.");
			}else{
				System.out.println("Erro! O email não pode ser apagado.");
			}
		}else{
			System.out.println("Não existem emails para "+ email.getRecipient());
		}
    }

    public Email[] list(String username){
        Email email;
		File directory = new File("./" + username);
		ArrayList<Email> emails= new ArrayList<Email>();
        
		if(!directory.exists()){
			boolean statusdir = directory.mkdir();
			if (statusdir){
				System.out.println("O diretório foi criado para " + username);
			}else{
				System.out.println("Erro! O diretório não foi criado para "+ username);
			}
		}
        try{
			for(File file: directory.listFiles()){
				FileReader fileReader = new FileReader(file);

				BufferedReader bufferedReader = new BufferedReader(fileReader);

				String linha = "";

				email = new Email();
				if ((linha=bufferedReader.readLine()) != null)
					email.setId(Long.parseLong(linha));
				if ((linha=bufferedReader.readLine()) != null)
					email.setSender(linha);
				if ((linha=bufferedReader.readLine()) != null)
					email.setRecipient(linha);
				if ((linha=bufferedReader.readLine()) != null)
					email.setSubject(linha);
				if ((linha=bufferedReader.readLine()) != null)
					email.setBody(linha);
				
				emails.add(email);
				fileReader.close();
			    bufferedReader.close();
			}
	    }catch(IOException e){
			e.printStackTrace();
		}
		System.out.println("Retornando "+emails.size()+" emails de " + username);
		return emails.toArray(new Email[emails.size()]);
    }
}