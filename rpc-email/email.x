
const MAX_SR = 100;
const MAX_B = 240;

struct temail{
  double id; 
  char sender[MAX_SR];
  char recipient[MAX_SR];
  char subject[MAX_SR];
  char body[MAX_B];
};

struct client_name{
  char name[MAX_SR];
};

struct tinbox{
  struct temail emails[MAX_SR];
  int cont;
};

program PROG { 
  version VERSAO { 
     void SEND(temail) = 1;
     tinbox LIST(client_name) = 2;
     void DELETE(temail) = 3;
  } = 1;
} = 0x31234567;
