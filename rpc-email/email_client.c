/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "email.h"
#include <time.h>

void
prog_1(char *host)
{
	CLIENT *clnt;
	void  *result_1;
	temail  send_1_arg;
	tinbox  *result_2;
	client_name  list_1_arg;
	void  *result_3;
	temail  delete_1_arg;

#ifndef	DEBUG
	clnt = clnt_create (host, PROG, VERSAO, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	result_1 = send_1(&send_1_arg, clnt);
	if (result_1 == (void *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_2 = list_1(&list_1_arg, clnt);
	if (result_2 == (tinbox *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_3 = delete_1(&delete_1_arg, clnt);
	if (result_3 == (void *) NULL) {
		clnt_perror (clnt, "call failed");
	}
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}

void show_options(int * resp){
    printf("O que deseja fazer?\n");
    printf("1-Enviar\n2-Listar\n");
    scanf("%d", resp);
    setbuf(stdin, NULL);
}

int ask(char function[]){
	int resp;
	printf("Deseja realmente %s o email? 1 - Sim , <outro> - Cancelar", function);
    scanf("%d", &resp);
	return resp;
}

//Elimina o \n da string
void eliminate_enter(char * str){
	int len = strlen(str);
	if (str[len - 1] == '\n') str[--len] = 0;
}

void send_message(CLIENT * clnt, char sender[]){
    temail email;
    int resp;
	time_t timer;
	struct tm y2k = {0};

    y2k.tm_hour = 0;   y2k.tm_min = 0; y2k.tm_sec = 0;
    y2k.tm_year = 119; y2k.tm_mon = 3; y2k.tm_mday = 17;

    printf("============ EMAIL =================\n");
	//Um email é identificado pela a hora que foi enviado e remetente(sender).
	strcpy(email.sender, sender);
    printf("Destinatário: ");
    fgets(email.recipient, MAX_SR, stdin);
	eliminate_enter(email.recipient);
    printf("Assunto: ");
    fgets(email.subject, MAX_SR, stdin);
    printf("Corpo da Mensagem:\n");
	eliminate_enter(email.subject);
    fgets(email.body, MAX_B, stdin);
	eliminate_enter(email.body);
	time(&timer);
	email.id = difftime(timer, mktime(&y2k)); //Segundos contados desde 17 de abril de 2019
    if (ask("enviar")== 1){
        send_1(&email, clnt);
    }
}

void delete_message(CLIENT * clnt, temail email){
	if (ask("apagar") == 1){
		delete_1(&email, clnt);
	}
}

void open_message(CLIENT * clnt, temail email){
	int resp;
	printf("============ EMAIL =================\n");
    setbuf(stdin, NULL);
    printf("Rementente: ");
    printf("%s\n", email.sender);
    printf("Assunto: ");
    printf("%s\n", email.subject);
    printf("Corpo da Mensagem:\n");
    printf("%s\n", email.body);
	printf("-------------------------------\n");
	printf("1-APAGAR / 2-ENCAMINHAR / 3-RESPONDER / <outro> - VOLTAR\n");
	scanf("%d", &resp);
	switch (resp)
	{
	case 1:
		delete_message(clnt, email);
		break;
	case 2:
		//forward_message(clnt, email);
		break;
	case 3:
		//reply_message(clnt, email);
		break;
	default:
		break;
	}

}

void list_message(CLIENT * clnt, char user[]){
	tinbox * e = NULL;
	int i, resp, flag = 1, exit = 0;//flag controla a exibição dos e-mails nos casos que atinge o limites
	client_name name_u;
	strcpy(name_u.name, user);
	e = list_1(&name_u, clnt);

	if(e != NULL){
		printf("================ CAIXA DE ENTRADA ==============\n");
		for(i = 0; i < e->cont;){
			if(flag){
				printf("-------------------------------\n");
				printf("Remetente: %s", e->emails[i].sender);
				printf("Assunto: %s", e->emails[i].subject);
				printf("-------------------------------\n");
				printf("0-ANTERIOR/ 1-PRÓXIMO/ 2-ABRIR / <outro> - SAIR\n");
			}
			scanf("%d", &resp);
			switch (resp)
			{
			case 0:
				if(i == 0){
					printf("Não há mais emails anteriores!\n");
					flag = 0;
				}else{
					i--;
					flag = 1;
				}
				break;
			case 1:
				if(i+1 == e->cont){
					printf("Não há mais emails!\n");
					flag = 0;
				}else{
					i++;
					flag = 1;
				}
				break;
			case 2:
				open_message(clnt, e->emails[i]);
				break;
			default:
			    exit = 1;
				break;
			}
			if(exit)
			    break;
		}
	}else{
		printf("Problemas no servidor de e-mail!\n");
	}
}

int
main (int argc, char *argv[])
{
	CLIENT *clnt;

	if (argc!=3) {
		fprintf(stderr,"Uso: %s hostname nome",argv[0]);
		exit(0); 
	}
	clnt = clnt_create(argv[1], PROG, VERSAO, "tcp"); 
	if (clnt == (CLIENT *) NULL) {
		clnt_pcreateerror(argv[1]);
		exit(1); 
	}
    int resp;
    show_options(&resp);
    switch (resp)
    {
    case 1:
	    //Passa o nome inserido no terminal como segundo parâmetro de send_message()
        send_message(clnt, argv[2]); 
        break;
	case 2:
	    list_message(clnt, argv[2]);
		break;
    default:
        break;
    }
	exit(0);
}
