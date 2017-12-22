#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
#include <string.h>
#include "Fibonacci.h"

//Helper Functions

int Max_length(int a, int b)
{
    //returns the bigger number or just stops at a and returns if they're equal
    if(a >= b)
        return a;
    else return b;
}

unsigned int my_pow(int n)
{
    //uses the variable p as the base for an exponent calculating 10^n
    int i;
    unsigned int p = 1;
    for(i=0;i<n;i++)
        p *= 10;
    return p;
}

unsigned int concatenate(unsigned int x, int y)
{
    //uses pow as a base to determine the number of digits added to X and fit in Y at the end
    unsigned pow = 10;
    while(y >= pow)
        pow *= 10;
    return (x*pow) + y;
}

//Required functions

HugeInteger *hugeAdd(HugeInteger *p, HugeInteger *q)
{
    int i;
    //check for NULL pointers and successful dynamic memory allocation for both pointers to add
    if(p == NULL)
        return NULL;

    if(p->digits == NULL)
    {
        hugeDestroyer(p);
        return NULL;
    }

    if(q == NULL)
        return NULL;
    if(q->digits ==  NULL)
    {
        hugeDestroyer(q);
        return NULL;
    }
    //create new HugeInteger pointer to store the sum of the inputs and check its dynamic memory allocation
    HugeInteger *sum = malloc(sizeof(HugeInteger));
    if(sum == NULL)
        return NULL;
    sum->digits = malloc(sizeof(HugeInteger)*(Max_length(p->length,q->length)+1));
    if(sum->digits == NULL)
    {
        hugeDestroyer(sum);
        return NULL;
    }
    /*since adding 2 numbers together can increase the digits by 1 at most
     sum's length is slightly bigger by 1*/
    sum->length = Max_length(p->length,q->length)+1;
    //initialize sum digits array to  0, for easy adding
    for(i=0;i < sum->length;i++)
        sum->digits[i] = 0;
    //add p's digits, at this point no digit inside should go over 10
    for(i=0;i < p->length;i++)
        sum->digits[i] += p->digits[i];
    //add q's digits to sum making a few numbers larger than 10
    for(i=0;i<q->length;i++)
        sum->digits[i] += q->digits[i];
    //any number larger than 10 gets cut down to a single digit and the 1 carries over
    for(i=0;i < sum->length-1;i++)
        if(sum->digits[i] > 9)
        {
            sum->digits[i] = sum->digits[i]%10;
            sum->digits[i+1] += 1;
        }
    /*if there was never a number added at the end of the array's extra size
    modify sum's length for the appropriate amount of numbers*/
        if (sum->digits[sum->length-1] == 0)
            sum->length -= 1;
    return sum;
}

HugeInteger *hugeDestroyer(HugeInteger *p)
{
    //need to check for NULL before any calls to free
    if(p == NULL)
        return NULL;
    //free the struct from the inside out
    if(p->digits == NULL)
    {
        free(p);
        return NULL;
    }

    free(p->digits);
    free(p);
    return NULL;
}

HugeInteger *parseString(char *str)
{
    //check is the string input is empty
    if(str == NULL)
        return NULL;

    int i,j = 0;
    //verify the length to know how long to make HugeInteger
    int length = strlen(str);
    //dynamic memory allocation
    HugeInteger *p = malloc(sizeof(HugeInteger));
    if(p == NULL)
    {
        hugeDestroyer(p);
        return NULL;
    }
    //dynamic memory allocation inside p
    p->digits = malloc(sizeof(int)*length);
    if(p->digits == NULL)
    {
        hugeDestroyer(p);
        return NULL;
    }
    //an empty string means the number would be 0
    if(strcmp(str,"") == 0)
    {
        p->digits[0] = 0;
        return p;
    }
    //save the string in p backwards for easy adding
    for(i=length - 1;i>=0;i--)
    {
        p->digits[j] = (str[i] - '0');
        j++;
    }
    //set p's length for other necessary function calls
    p->length = length;
    free(str);
    return p;
}

HugeInteger *parseInt(unsigned int n)
{
    int length = 1, i;
    //setup a variable to compare how big the length is going to be
    unsigned int mod;
    //dynamic memory allocation
    HugeInteger *p = malloc(sizeof(HugeInteger));
    if(p == NULL)
    {
        return NULL;
    }
    /*while loop determines the length once mod and n are equal
    by parsing through one digit at a time
    if the int is near it's capacity it's length is set to max or just under for smaller numbers*/
    while(mod != n)
    {
        if(n >= (INT_MAX/10))
        {
            mod = n;
            if(n < 1000000000)
            length = 10;
            else length = 11;
        }
        else if(n >= (UINT_MAX/10))
        {
            mod = n;
            if(n < 1000000000)
                length = 10;
            else length = 11;
        }
        else
        {
            mod = n%my_pow(length);
            length++;
        }
    }

    //p's length is one less since i had to start length at 1 for proper exponent calculation
    p->length = length-1;
    //dynamic memory allocation
    p->digits = malloc(sizeof(int) * p->length);
    if(p->digits == NULL)
    {
        hugeDestroyer(p);
        return NULL;
    }
    //goes digit per digit adding it into p->digits backwards	
	for(i=0;i<p->length;i++)
		p->digits[i] = (n/my_pow(i))%10;

	if(n == 0)
	{
		p->digits[0] = 0;
	}
	
	if(n == 0)
	{
		p->length = 1;
	}
	

    return p;
}

unsigned int *toUnsignedInt(HugeInteger *p)
{
    int i;
    //define pointer and variable to store current state of reading HugeInteger
    unsigned int *unsignedIntValue;
    unsigned long int currentValue;
    //dynamic memory allocation
    unsignedIntValue = malloc(sizeof(unsigned int));
    if(unsignedIntValue == NULL)
        return NULL;
    //check for empty HugeInteger
    if(p == NULL)
        return NULL;
    if(p->digits == NULL)
    {
        hugeDestroyer(p);
        return NULL;
    }
    //the length of an unsigned int can't be bigger than 10
    if(p->length > 10)
        return NULL;
    //start with the last digit for current value then add the others one by one
    currentValue = p->digits[p->length-1];

    for(i = (p->length - 2);i>=0;i--)
    {
        currentValue = concatenate(currentValue,p->digits[i]);
    }
    //unsigned int can't be bigger than 4294967295
    if(currentValue>UINT_MAX)
        return NULL;
    /* if the current value is within the range of unsigned in
    assign it to the unsigned int value and return it */
    *unsignedIntValue = currentValue;

    return unsignedIntValue;
}

HugeInteger *fib(int n)
{
    //define the pointers to use and variables
    int i;
    HugeInteger *p, *q, *sum;
    //F(0) and F(1) return their input
    if(n < 2)
    {
        //makes a pointer out of the int type input
        p = parseInt(n);
        return p;
    }
    //initialize the numbers to add
    p = parseInt(0);
    q = parseInt(1);

    for(i=1 ;i<n;i++)
    {
        //add pointer together working towards F(n)
        sum = hugeAdd(p,q);
        //free the first pointer since it's no longer going to be used
        hugeDestroyer(p);
        //pointers move up one and prepared to be added once again
        p = q;
        q = sum;
    }
    return sum;
}

double difficultyRating(void)
{
    //most difficult parts about this assignment don't seem as complex after figuring it out
    return 3.5;
}

double hoursSpent(void)
{
    return 45.0;
}
