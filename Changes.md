# changes

## changes from CG_Descent 6.8 C code

- 16 bit C int used as boolean replaced by Java boolean
- 16 bit C int used as int replaced by 32 bit Java int.
- 32 bit C long int replaced by 32 bit Java int.
- C double* plus int replaced by Java double[].
- C pointer to structure replaced by instance reference
- C structure field reference '->' replaced by Java instance field
reference '.'
- (double *) malloc replaced by new double[]
- C printf replaced by System.out.printf
- goto replaced by break where possible, throw/catch otherwise.
- char * replaced by String
- !strcmp replaced by String.equals
- value, grad, and valuegrad function args replaces by Functional
interface and single objective arg
- Com.cg_valgrad != null replaced by true, since Functional
default method handles that case.
- #ifdef NOBLAS is assumed true everywhere
 
## Acknowledgments

### ![Yourkit](https://www.yourkit.com/images/yklogo.png)

YourKit is kindly supporting open source projects with its full-featured Java
Profiler.

YourKit, LLC is the creator of innovative and intelligent tools for profiling
Java and .NET applications. Take a look at YourKit's leading software products:

* <a href="http://www.yourkit.com/java/profiler/index.jsp">YourKit Java Profiler</a> and
* <a href="http://www.yourkit.com/.net/profiler/index.jsp">YourKit .NET Profiler</a>.

