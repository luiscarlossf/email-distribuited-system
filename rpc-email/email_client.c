/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "email.h"


void
prog_1(char *host)
{
	CLIENT *clnt;
	void  *result_1;
	temail  send_1_arg;
	tinbox  *result_2;
	char *list_1_arg;
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
	result_2 = list_1((void*)&list_1_arg, clnt);
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


int
main (int argc, char *argv[])
{
	char *host;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	prog_1 (host);
exit (0);
}
