

A good software developer understands the importance of design patterns and knows how to effectively utilise them in their code.

A design pattern is a general repeatable solution to a commonly occurring problem in software design. it provide guidelines, principles, and best practices for structuring and organising software components to achieve specific objectives, such as reusability, maintainability, scalability, and flexibility.

Design patterns are categorised into three main types:

1. Creational Patterns: focus on object creation mechanisms, providing flexibility in creating objects while decoupling the code from the specific classes or objects being instantiated. These patterns help in creating objects in a manner that is suitable for the situation at hand.

Eg :
  - Singleton Pattern: `java.lang.Runtime`
  - Builder Pattern: `java.lang.StringBuilder`
  - Factory Method Pattern: `java.util.Calendar`
  - Prototype Pattern: `java.lang.Object#clone()`
  - Abstract Factory Pattern: `javax.xml.parsers.DocumentBuilderFactory`

2. Structural Patterns: Deal with the composition of classes and objects to form larger structures or relationships. They focus on achieving efficient object composition by providing mechanisms to represent relationships between objects. Structural patterns help in designing flexible and reusable code structures

Eg :
  - Adapter Pattern: `java.util.Arrays#asList()`
  - Composite Pattern: `javax.swing.JComponent`
  - Proxy Pattern: `java.lang.reflect.Proxy`
  - Decorator Pattern: `java.io.BufferedInputStream`
  - Bridge Pattern: `java.util.logging.Logger`
  - Flyweight Pattern: `java.lang.Integer#valueOf()`
  - Facade Pattern: `javax.faces.context.FacesContext`

3. Behavioural Patterns: focus on the interaction and communication between objects, defining how they collaborate and distribute responsibilities. These patterns help in designing the flow of communication and behavior among objects to achieve more flexible and maintainable systems. Behavioral patterns enable different objects to work together effectively.

Eg :
  - Observer Pattern: `java.util.Observable` and `java.util.Observer`
  - Strategy Pattern: `java.util.Comparator`
  - Iterator Pattern: `java.util.Iterator` and `java.util.Enumeration`
  - Template Method Pattern: `javax.servlet.http.HttpServlet`
  - Command Pattern: `java.util.concurrent.Executor`
  - State Pattern: `java.util.concurrent.ThreadPoolExecutor`
  - Chain of Responsibility Pattern: `javax.servlet.Filter`
  - Interpreter Pattern: `java.util.regex.Pattern`
  - Visitor Pattern: `java.nio.file.FileVisitor`

Design patterns are like tools in your toolbox. Use them wisely to tackle most of programming challenges.
Spring bean 
Two types its created lazy and eager

eager - startup of application
lazily - when required
Scope = prototype - lazy beans

how are the beans looked 
- @Component scan
- @Configuration - bean initialised using @Bean



## Prototype - clone of object
1. private memebers can be only present for the class level, we might not be able to access them via constructor or attr.
2. Here the client has to be knowing what to copy and what not copy

** Prototype interface **
- here the class will expose the method clone.
** Advantages **
- Class is aware of what fields to show / expose and what not.


## Singleton pattern
1. Make the constructor is private

- Eager initialisation
private static DBConnection connObject = new DBConnection();

- Lazy initialisation
initialise only when required
```
private static DBConnection connObject;

private DBConnection(){
	
}

public static DBConnection getInstance(){
	if (connObject == null){
		return new DBConnection();
	}
	return connObject;
}
```
**Problem**
if two threads parallely calls this then it creates two objects.

- Synchronised method

```
public class DBConnection{
	private DBConnection(){

	}

	private static DBConnection connObject;

	synchronized public static DBConnection getInstance(){
		if (connObject == null){
			return new DBConnection();
		}
	return connObject;
	}
}
```

The problem with this approach is every time a request comes, it has to take a lock on the method, even though we have initialised the object, the locking is a pretty expensive operation.


- Double locking
Here we check two times if the object is created then only we create it else we skip creation

```

public class DBConnection{
	private static DBConnection connObject;

	private DBConnection(){

	}

	public static DBConnection getInstance(){
		if (connObject == null){
			syncrhonized (DBConnection.class){
				if (connObject == null){
					connObject = new DBConnection();
				}
			}
		}
		return connObject;
	}
}
```


## Factory pattern
If we wanted to have a change in the condition of object creation then we will have the changes at only one place instead of multiple places

## Abstract factory pattern 
In this you return a factory of factory and from the second factory you get the actual objects


## Builder pattern
when you wanted to create the object in step by step manner

## Decorator pattern
- the decorator pattern is helping to add the extra functionality to the base object dynamically without creating a combined class before hand.
- A decorator pattern class extends the base class and add extra functions to the base class

## Proxy design pattern
- THe proxy design pattern is a proxy to access your objects, this can be used if you wanted to add the restrictions on some object methods access, like admin can only access them..

Having a controlled access to the methods

## Composite pattern
- 
