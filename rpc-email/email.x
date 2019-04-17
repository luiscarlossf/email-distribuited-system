
struct temail{
  long id; 
  char sender[100];
  char recipient[100];
  char subject[100];
  char body[240];
};

struct tinbox{
  struct temail emails[100];
  int cont;
};

program PROG { 
  version VERSAO { 
     void SEND(temail) = 1;
     tinbox LIST() = 2;
     void DELETE(temail) = 3;
  } = 1;
} = 0x31234567;
