#include <stdio.h>

// using namespace std;

int *p, x, y;
int a[5] = {100,200,300,400,500};
int *p2;

int main() {
  p=NULL;
  x = 10;
  p=&x;
  printf("%d %d %p %p %p ",x,*p,p,&x,&p);
  p2 = &x;
  return 0;
}