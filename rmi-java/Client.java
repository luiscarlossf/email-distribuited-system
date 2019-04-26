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
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
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
        Scanner scanner = new Scanner(con.reader());
        resp = scanner.nextInt();
        scanner.close();
        return resp;
    }

    public boolean ask(String function) {
        int resp = 0;
        System.out.println("Deseja realmente " + function + " o email? 1 - Sim , <outro> - Cancelar");
        Console con = System.console();
        Scanner scanner = new Scanner(con.reader());
        resp = scanner.nextInt();
        scanner.close();
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
        //Set o id com a hora atual em millisegundos
		email.setId(System.currentTimeMillis());
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
        for(int i = 0; i <= emails.size(); ){
            if(flag){ //Não exibe a caixa de entrada novamente no casos que atinge o limite
                System.out.println("=================CAIXA DE ENTRADA================");
                if (emails.size() == 0)
                    System.out.println("Nenhum email foi enviado a você.\n Digite 0 para voltar ao menu anterior.");
                else{
                    System.out.println("-------------------------------\n");
                    System.out.println("Remetente: " + emails.get(i).getSender());
                    System.out.println("Assunto: " + emails.get(i).getSubject());
                    System.out.println("-------------------------------\n");
                    System.out.println("1-ANTERIOR/ 2-PRÓXIMO/ 3-ABRIR / <outro> - SAIR\n");
                }
            }
            switch(scanner.nextInt()){
                case 1:
                    if(i == 0){
                        System.out.println("Não há mais emails anteriores!\n");
                        flag = false;
                    }else{
                        i--;
                        flag = true;
                    }
                    break;
                case 2:
                    if(i+1 == emails.size()){
                        System.out.println("Não há mais emails!\n");
                        flag = false;
                    }else{
                        i++;
                        flag = true;
                    }
                    break;
                case 3:
                    if (open_email(emails.get(i)) == 1)
                        emails.remove(i);
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

    public void forward_email(Email email) throws RemoteException{
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
        email.setBody("Email encaminhado\n"+ email.getBody());
        scanner.close();
        msi.send(email);

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

    public int open_email(Email email) throws RemoteException {
        Console con = System.console();
        Scanner scanner = new Scanner(con.reader());
        int resp;
        System.out.println("============ EMAIL =================");
        System.out.println("Rementente: ");
        System.out.println(email.getSender());
        System.out.println("Assunto: ");
        System.out.println(email.getSubject());
        System.out.println("Corpo da Mensagem:\n");
        System.out.println(email.getBody());
        System.out.println("-------------------------------");
        System.out.println("1-APAGAR / 2-ENCAMINHAR / 3-RESPONDER / <outro> - VOLTAR");
        resp = scanner.nextInt();
        switch (resp)
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
        return resp;
    }

    public static void main(String argv[]){
        if (argv.length != 1){
            System.out.println("Você precisa passar o nome do usuário. Java Client nome_usuario");
            System.exit(1);
        }else{
            int resp;
            Client client = new Client(argv[0]);
            
            try{
                while(true){
                    resp = client.show_options();
                    switch (resp)
                    {
                    case 1:
                        client.send_email(); 
                        break;
                    case 2:
                        client.list_emails();
                        break;
                    default:
                        break;
                    }
                    if(resp == 0)
                        break;
                }
            }catch(Exception e){
                System.out.println("Exceção durante chamadas remotas:" + e);
                e.printStackTrace();
            }
        }
    }

    private ServerEmailInterface msi;

}