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

