How Many Ways you can create Strings in Java?
- We can create Strings in many ways and some of them are listed below:

- String Literals: This is the most common way to create strings in Java. When you create a string using string literals, Java first looks for the string in the string constant pool.

- If found, it returns a reference to the pooled instance. If not, it creates a new string instance in the pool and returns a reference to this new instance. This process is automatic and helps save memory by reusing immutable strings

- ``` String s = "hello"; ```
- When you create a string with the new keyword:
- Java always creates a new string object in the heap memory, even if the same string already exists in the string constant pool. This way, you can create distinct objects that have the same value.
- String s2 = new String("Hello");


StringBuilder and StringBuffer:
- You can create strings by first constructing a mutable sequence of characters with StringBuilder or StringBuffer and then converting it to a string.
StringBuilder sb = new StringBuilder("Hello");
String s3 = sb.toString();

Character Arrays:
- You can convert a character array into a string.

```
char[] charArray = {'H', 'e', 'l', 'l', 'o'};
String s4 = new String(charArray);
```


## Exception heirarchy

Here is the exception hierarchy in Java:

- `java.lang.Throwable`
  - `java.lang.Error`
    - `java.lang.AssertionError`
    - `java.lang.LinkageError`
      - `java.lang.ClassCircularityError`
      - `java.lang.ClassFormatError`
      - `java.lang.NoClassDefFoundError`
      - `java.lang.UnsupportedClassVersionError`
    - `java.lang.OutOfMemoryError`
    - `java.lang.StackOverflowError`
    - `java.lang.VirtualMachineError`
      - `java.lang.InternalError`
      - `java.lang.UnknownError`
  - `java.lang.Exception`
    - `java.lang.RuntimeException`
      - `java.lang.ArithmeticException`
      - `java.lang.ArrayIndexOutOfBoundsException`
      - `java.lang.ArrayStoreException`
      - `java.lang.ClassCastException`
      - `java.lang.IllegalArgumentException`
        - `java.lang.NumberFormatException`
      - `java.lang.IllegalStateException`
      - `java.lang.IndexOutOfBoundsException`
      - `java.lang.NullPointerException`
      - `java.lang.SecurityException`
      - `java.lang.UnsupportedOperationException`
    - `java.lang.ReflectiveOperationException`
      - `java.lang.ClassNotFoundException`
      - `java.lang.IllegalAccessException`
      - `java.lang.InstantiationException`
      - `java.lang.NoSuchFieldException`
      - `java.lang.NoSuchMethodException`
    - `java.io.IOException`
      - `java.io.FileNotFoundException`
      - `java.io.InterruptedIOException`
      - `java.io.UTFDataFormatException`
      - `java.io.UnsupportedEncodingException`
    - `java.sql.SQLException`
    - `java.net.MalformedURLException`
    - `java.net.UnknownHostException`

This hierarchy shows the main classes and some common subclasses. Note that there are many more specific exceptions in the Java standard library.
