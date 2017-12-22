#### Fibonacci

This program uses 2 input files Fibonacci.c and the testcase files  
to demonstrate the parsing of both an int and a string to a HugeInteger struct  
as well as calculating Fibonacci to the 1000th term in O(nk)  
where n is the parameter passed to the function and k is the number of digits in F(n)  

##### Requirements

* Compiling
  * Linux / Mac terminal with gcc
  * Windows with bash and gcc or an ide
* Files
  * Fibonacci.c
  * Fibonacci.h
  * testcase0#.c

##### Compilation and Testting (Linux/Mac Command Line)

* Compile multiple source files (.c files) at the command line:
  * gcc Fibonacci.c testcase01.c
* By default, this will produce an executable file called a.out, which you can run by typing:
  * ./a.out
* Redirect output from the screen to a .txt file:
  * ./a.out > output.txt
* Use diff to compare against the test cases
  * diff output.txt output01.txt  
If the files differ it will output information about the lines that aren't the same
* Use the test all shell script to test all test cases at once after compiling
  * ./test-all.sh
