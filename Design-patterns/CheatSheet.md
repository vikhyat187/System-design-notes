

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
