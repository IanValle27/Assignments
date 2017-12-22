#### SkipList

Language: Java   

This program uses 2 input files Hw02.java and the H2in-_#.txt files  
to build a skip list data structure to support the traversal, searching, addition and deletion of integers.  
this implementation supports building a skip list to support some number of occurrences of integers  
in the range of 1 to 1000. 

##### Requirements

* Compiling
  * Linux / Mac terminal with javac
  * Windows with bash and javac or an ide
* Files
  * Hw02.java
  * H2in-_#.txt

##### Compilation and Testing (Linux/Mac Command Line)

* Compile source file (.java files) at the command line:
  * javac Hw02.java
* By default, this will produce an executable file called Hw02.class, which you can run by typing:
  * java Hw02 H2in-_#.txt
* Redirect output from the screen to a .txt file:
  * java Hw02 H2in-_#.txt > output.txt
* Use diff to compare against the test cases
  * diff output.txt H2ExpectedOutPut_#.txt  
If the files differ it will output information about the lines that aren't the same
