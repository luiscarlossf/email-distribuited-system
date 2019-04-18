/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "email.h"
void show(char * str){
	int i;
	printf("Eliminate_enter: %s\n", str);
	for(i=0; i< strlen(str); i++){
		printf("%d - %c\n", i, str[i]);
	}
}

void *
send_1_svc(temail *email, struct svc_req *rqstp)
{
	static char * result;

	FILE * arq;
    char filename[103];
	time_t timer;
    printf("Enviando email de %s para %s\n", email->sender, email->recipient);
    strcpy(filename, email->recipient);
    arq = fopen(strcat(filename, ".txt"), "a"); //Abre arquivo nomeado como name_file.txt(inclui '\n')
    if(arq == NULL){
        printf("ERRO! Arquivo não pode ser aberto.");
    }else{
		fprintf(arq, "%lf %s\n", email->id, email->sender);
        fputs(email->sender, arq);
		fputs("\n", arq);
        fputs(email->subject, arq);
		fputs("\n", arq);
        fputs(email->body, arq);
		fputs("\n", arq);
    }
    fclose(arq);

	return (void *) &result;
}

tinbox *
list_1_svc(client_name * user, struct svc_req *rqstp)
{
	static tinbox  result;
	printf("Hi");
	FILE * arq;
    temail email;
	int cont = 0;
    char aux[6];
    char filename[103];
    printf("Listando os e-mails de %s!\n", user->name);
	//printf("================ Chekcpoint 4 ==============\n");
    strcpy(filename, user->name);
    arq = fopen(strcat(filename, ".txt"), "r+");
    if(arq == NULL){
        printf("ERRO! Arquivo não pode ser aberto.");
    }else{
        while(fscanf(arq,"%lf", &(email.id))>0){
			fgets(email.sender, MAX_SR, arq);
			strcpy(email.recipient, user->name);
			fgets(email.sender, MAX_SR, arq);
			fgets(email.subject, MAX_SR, arq);
			fgets(email.body, MAX_B, arq);
			result.emails[cont] = email;
			cont++;
			
	    }
		result.cont = cont;
    }
    fclose(arq);
    printf("Foram encontrados %d emails!\n", result.cont);
	return &result;
}

void *
delete_1_svc(temail *email, struct svc_req *rqstp)
{
	static char * result;

	FILE * arq, * arq2;
    temail temp;
    char id[100];
    char aux[400];
    char buf[10];
    char aux2[100];
    char filename[103];
	char filename_temp[103];
    char sender[MAX_SR];
    int cont =0, i;
	strcpy(filename, email->recipient);
	strcpy(filename_temp, email->recipient);
    arq = fopen(strcat(filename, ".txt"), "r+");
    arq2 = fopen(strcat(filename_temp, "_temp.txt"), "w+");
	
    if((arq == NULL) && (arq2 == NULL)){
        printf("ERRO! Arquivo não pode ser aberto.");
    }else{
        while(fscanf(arq, "%lf", &(temp.id)) >0){
			fgets(temp.sender, MAX_SR, arq);//Pega o \n
            if(temp.id != email->id){
                fprintf(arq2, "%lf", temp.id);
				fputs(temp.sender, arq2);
                fgets(temp.sender, MAX_SR, arq);
                fputs(temp.sender, arq2);
                fgets(temp.subject, MAX_SR, arq);
                fputs(temp.subject, arq2);
                fgets(temp.body, MAX_B, arq);
                fputs(temp.body, arq2);
            }else{
                fgets(temp.sender, MAX_SR, arq);
                fgets(temp.subject, MAX_SR, arq);
                fgets(temp.body, MAX_B, arq);
			}
        }
    }
    remove(filename);
    rename(filename_temp, filename);
    fclose(arq);
    fclose(arq2);

	return (void *) &result;
}
