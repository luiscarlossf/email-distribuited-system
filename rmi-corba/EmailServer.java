import EmailServer.*;

public class EmailServer{
    public static void main(String args[]){
        try{
            //Cria e inicializa o ORB
            ORB orb = ORB.init(args, null);

            //Referência do rootPOA e ativação do POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            //Cria o servente e registra-o no ORB
            EmailImpl emailImpl = new EmailImpl();
            emailImpl.setORB(org);

            //Referência a partir do servente
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(emailImpl);
            EmailInterface href = EmailHelper.narrow(ref);

            //Obtém o root naming context NameService
            org.omg.CORBA.OBject objRef = org.resolve_initial_references("NameService");

            //Referência do NamingContext
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            //Vinculação da referência do objeto ao nome
            String name = "Email";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("EmailServer pronto  e aguardando...");

            //Espera por invocações
            orb.run();

        }catch(Exception e){
            System.err.println("ERROR: "+ e);
            e.printStackTrace(System.out);
        }

        System.out.println("EmailServer desligando ...");
    }
}