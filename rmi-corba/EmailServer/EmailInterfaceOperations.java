package EmailServer;


/**
* EmailServer/EmailInterfaceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from email_interface.idl
* Domingo, 21 de Abril de 2019 09h05min28s BRT
*/

public interface EmailInterfaceOperations 
{
  void send (EmailServer.Email email);
  void delete (EmailServer.Email email);
  EmailServer.Email[] list (String username);
} // interface EmailInterfaceOperations
