package clienteserver;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer{
    public static void main(String argv[]){
        try{
            System.out.println("Iniciando o servidor...");
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ServerEmail", new ServerEmail());
        }catch(Exception e){
            System.out.println("Ocorre um problema na inicialização do servidor.\n" + e.toString());
        }
    }
}