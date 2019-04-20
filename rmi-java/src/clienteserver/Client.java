package clienteserver;
import clienteserver.Email;

import java.io.Console;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private String username;

    public Client(String username) {
        this.username = username;
        System.out.println("Iniciando o CLiente ...");
        try {
            Registry registry = LocateRegistry.getRegistry("local do server");
            msi = (ServerEmailInterface) registry.lookup("ServerEmail");
        } catch (Exception e) {
            System.out.println("Falhou a inicialização do Cliente.\n" + e);
            System.exit(0);
        }
    }

    public int show_options() {
        int resp = 0;
        System.out.println("O que deseja fazer?\n");
        System.out.println("1-Enviar\n2-Listar\n0-Sair\n");
        Console con = System.console();
        if(con != null){
            Scanner scanner = new Scanner(con.reader());
            resp = scanner.nextInt();
            scanner.close();
        }
        return resp;
    }

    public boolean ask(String function) {
        int resp = 0;
        System.out.println("Deseja realmente " + function + " o email? 1 - Sim , <outro> - Cancelar");
        Console con = System.console();
        if(con != null){
            Scanner scanner = new Scanner(con.reader());
            resp = scanner.nextInt();
            scanner.close();
        }
        if (resp == 1)
            return true;
        else
            return false;
    }

    public void send_email() throws RemoteException {
        Email email = new Email();
        Console con = System.console();
        Scanner scanner = new Scanner(con.reader());
        System.out.println("=================ENVIO DE EMAIL================");
        email.setSender(this.username);
        System.out.println("Destinatário:");
        email.setRecipient(scanner.nextLine());
        System.out.println("Assunto:");
        email.setSubject(scanner.nextLine());
        System.out.println("Corpo da Mensagem:\n");
        email.setBody(scanner.nextLine());
        scanner.close();
        msi.send(email);
    }
    public void delete_email(Email email) throws RemoteException {
        if (this.ask("apagar")){
            msi.delete(email);
        }
    }
    public void list_emails() throws RemoteException {
        ArrayList<Email> emails = msi.list(this.username);
        Console con = System.console();
        Scanner scanner = new Scanner(con.reader());
        boolean exit = false, flag = true;
        for(int i = 0; i < emails.size(); ){
            if(flag){ //Não exibe a caixa de entrada novamente no casos que atinge o limite
                System.out.println("=================CAIXA DE ENTRADA================");
                System.out.println("-------------------------------\n");
                System.out.println("Remetente: " + emails.get(i).getSender());
                System.out.println("Assunto: " + emails.get(i).getSubject());
                System.out.println("-------------------------------\n");
                System.out.println("0-ANTERIOR/ 1-PRÓXIMO/ 2-ABRIR / <outro> - SAIR\n");
            }
            switch(scanner.nextInt()){
                case 0:
                    if(i == 0){
                        System.out.println("Não há mais emails anteriores!\n");
                        flag = false;
                    }else{
                        i--;
                        flag = true;
                    }
                    break;
                case 1:
                    if(i+1 == emails.size()){
                        System.out.println("Não há mais emails!\n");
                        flag = false;
                    }else{
                        i++;
                        flag = true;
                    }
                    break;
                case 2:
                    //open_message(clnt, e->emails[i]);
                    break;
                default:
                    exit = true;
                    break;
            }
            if(exit){
                scanner.close();
                break;
            }
        }
    }

    public void forward_email(Email email){
        Console con = System.console();
        Scanner scanner = new Scanner(con.reader());
        System.out.println("============ ENCAMINHAR EMAIL =================\n");
        email.setSender(email.getRecipient());
        System.out.println("Destinatário: ");
        email.setRecipient(scanner.nextLine());
        System.out.println("----Assunto: ");
        System.out.println("----" + email.getSubject());
        System.out.println("----Corpo da Mensagem:\n");
        System.out.println("----" + email.getBody());
        scanner.close();

    }

    public void reply_email(Email email) throws RemoteException{
        Console con = System.console();
        Scanner scanner = new Scanner(con.reader());
        Email answer = new Email();
        System.out.println("============ RESPONDER EMAIL =================");
        System.out.println("Rementente: ");
        System.out.println(email.getSender());
        System.out.println("Assunto: ");
        System.out.println(email.getSubject());
        System.out.println("Corpo da Mensagem:");
        System.out.println(email.getBody());
        System.out.println("Escreva a resposta: ");
        answer.setSender(email.getRecipient());
        answer.setRecipient(email.getSender());
        answer.setSubject(email.getSubject());
        answer.setBody(scanner.nextLine());
        scanner.close();
        msi.send(answer);
    }

    public void open_email(Email email) throws RemoteException {
        Console con = System.console();
        Scanner scanner = new Scanner(con.reader());

        System.out.println("============ EMAIL =================");
        System.out.println("Rementente: ");
        System.out.println(email.getSender());
        System.out.println("Assunto: ");
        System.out.println(email.getSubject());
        System.out.println("Corpo da Mensagem:\n");
        System.out.println(email.getBody());
        System.out.println("-------------------------------");
        System.out.println("1-APAGAR / 2-ENCAMINHAR / 3-RESPONDER / <outro> - VOLTAR");
        switch (scanner.nextInt())
        {
        case 1:
            delete_email(email);
            break;
        case 2:
            forward_email(email);
            break;
        case 3:
            reply_email(email);
            break;
        default:
            break;
        }
        scanner.close();
    }

    public static void main(String argv[]){
        if (argv.length != 3){
            System.out.println("Você precisa passar o nome do usuário. Java Client.java nome_usuario");
            System.exit(1);
        }else{
            Client client = new Client(argv[2]);
        
            try{
                switch (client.show_options())
                {
                case 1:
                    //Passa o nome inserido no terminal como segundo parâmetro de send_message()
                    client.send_email(); 
                    break;
                case 2:
                    client.list_emails();
                    break;
                default:
                    break;
                }
            }catch(Exception e){
                System.out.println("Exceção durante chamadas remotas:" + e);
            }
        }
    }

    private ServerEmailInterface msi;

}