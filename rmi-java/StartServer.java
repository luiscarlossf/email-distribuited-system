import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer{
    public static void main(String argv[]){
        try{
            System.out.println("Iniciando o servidor...");
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ServerEmail", new ServerEmail());
            System.out.println("Servidor inicializado com sucesso na porta " + registry.REGISTRY_PORT);
        }catch(Exception e){
            System.out.println("Ocorre um problema na inicialização do servidor.\n" + e.toString());
        }
    }
}