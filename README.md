# Email Distributed System
Implementation of distributed system using the RPC, RMI Java and CORBA technologies. The application is simple, a email service with the functions: 
- send
- list
- open
- delete 
- reply
- forward

All this on aclient-server architecture.

## Execution
### RPC
- Server

`./email_server`
- Client

`./email_client <host> <username>`


### RMI JAVA
- Server

`rmiregistry`

`java StartServer`

- Client

`./email_server <username>`
