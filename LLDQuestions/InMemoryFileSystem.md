
---

# 🧠 In-Memory File System — Low-Level Design

An **In-Memory File System** is a simplified version of a real file system (like NTFS, ext4, or APFS), designed to operate entirely in memory.
It mimics the hierarchical structure of directories and files, supporting operations such as:

* Creating and managing folders
* Adding, reading, and updating file content
* Navigating between directories
* Listing folder contents
* Deleting files or directories

This chapter explores the **low-level design** of such a system, including requirements, assumptions, and interactions.

---

## 🗂️ 1. Clarifying Requirements

Before starting the design, it’s important to ask thoughtful questions to clarify assumptions and define scope precisely.
Here’s an example of how a discussion might unfold between the **candidate** and the **interviewer**:

---

### 💬 Discussion

**Candidate:** Should the file system support both files and directories in a hierarchical structure?
**Interviewer:** Yes. The system should model a typical UNIX-style file system with nested files and directories.

---

**Candidate:** How will a user interact with this file system? Should I design a GUI, or a command-line interface (CLI)?
**Interviewer:** A command-line interface (CLI) is the way to go. The system should be able to parse and execute a set of basic shell commands.

---

**Candidate:** What are the primary commands it should support?
**Interviewer:** Implement core navigation and manipulation commands:
`mkdir` (make directory), `cd` (change directory), `ls` (list contents),
`pwd` (print working directory), `touch` (create an empty file), and `cat` (view file content).
It would also be great to support writing content to a file, perhaps through a simplified `echo 'text' > file` syntax.

---

**Candidate:** Regarding the `ls` command, should it just list names, or support options like `ls -l` for a detailed view?
**Interviewer:** Good question — include both. The design should support a simple listing of names **and** a detailed view showing metadata (like whether an entry is a file or directory, and its creation time).

---

**Candidate:** How should the system handle paths? Should it support both absolute and relative paths?
**Interviewer:** Yes, both. The system must maintain a concept of a **current working directory** and correctly resolve both **absolute** (e.g., `/home/user`) and **relative** (e.g., `documents`, `../`) paths, including `.` and `..`.

---

**Candidate:** What about advanced file system features like user permissions, ownership, symbolic links, or file locking?
**Interviewer:** Let’s keep those out of scope. We’ll assume a **single-user environment** with no permission constraints. The focus should be on the **hierarchical data structure** and **command execution framework**.

---

## ✅ 2. Functional Requirements

1. **Hierarchical structure:**
   Support creation of files and directories organized in a nested tree-like structure.

2. **In-memory storage:**
   The entire file system (including all files and directories) should reside in memory.

3. **CLI-based interaction:**
   Users interact through a shell that parses and executes string-based commands.

4. **Supported commands:**

   * `mkdir` → Create a directory
   * `cd` → Change current directory
   * `ls` → List directory contents
   * `pwd` → Print current working directory path
   * `touch` → Create an empty file
   * `cat` → Display file content
   * `echo 'text' > file` → Write content to file

5. **Listing formats:**
   The `ls` command should support:

   * **Simple mode:** List only names
   * **Detailed mode (`ls -l`):** Show metadata (type, creation time, etc.)

6. **Path handling:**

   * Support **absolute paths** (`/home/user`)
   * Support **relative paths** (`../`, `./folder`)
   * Maintain **current working directory**

7. **File content:**
   Files should store simple **string-based content** that can be read or overwritten.

---

## ⚙️ 3. Non-Functional Requirements

| Requirement         | Description                                                                                                      |
| ------------------- | ---------------------------------------------------------------------------------------------------------------- |
| **Modularity**      | Follow OOP principles — separate core entities (File, Directory, Shell, Command Parser, etc.)                    |
| **Maintainability** | Code should be clean, testable, and easy to debug                                                                |
| **Extensibility**   | Should be easy to add new commands (e.g., `rm`, `mv`) or listing strategies for `ls` without changing core logic |
| **Error Handling**  | Provide clear, user-friendly error messages (e.g., “Path not found” or “Cannot `cd` into a file”)                |
| **Usability**       | Offer intuitive command syntax similar to UNIX shell commands                                                    |
| **Performance**     | Since data is in-memory, all operations should run in O(1) to O(n) time, depending on directory size             |

---

### 💡 Summary

> This In-Memory File System models a simplified UNIX-like environment with hierarchical file/directory structure, CLI interaction, path resolution, and core commands (`mkdir`, `cd`, `ls`, etc.).
> It focuses on **modularity**, **extensibility**, and **ease of interaction**, while omitting complex features like user permissions or disk persistence.

---

Would you like me to extend this next into a **class diagram + LLD code structure** (like `File`, `Directory`, `FileSystem`, `CommandExecutor` classes)? That’s the next natural step before implementation.


Low-Level Design: File System 📂
Introduction : 
A File System is a fundamental component of any operating system, responsible for organizing, storing, and managing files and directories on storage devices. It provides a structured way to manage data, ensuring efficient storage, retrieval, and manipulation of digital information.

‍

Rules and Core Concepts : 
Basic File System Principles:

1. Hierarchical Structure: Files and directories are organized in a tree-like hierarchy.

2. File Attributes: Each file has metadata like name, size, creation time, permissions.

3. Directory Management: Directories can contain files and other directories.

4. File Operations: Support for basic operations like create, read, update, delete (CRUD).

5. Path-Based Access: Resources are accessed using string paths like

"/documents/report.txt"
‍

Interview Setting 🤝
Point 1:Introduction and Vague Problem Statement:

Interviewer: Design a basic File System management system.

‍

Candidate: Let me outline the core requirements and key characteristics:

• Support hierarchical path structure using the "/" delimiter.

• Implement path creation and value association.

• Manage file metadata.

• Handle different file types

‍

Point 2: Clarifying Requirements : 

Interviewer: Specify the core requirements for our File System:

• Support creating files and directories

• Implement path navigation

• Manage file metadata.

• Support for different file types

‍

Candidate: To ensure clarity, I'll summarize the key requirements:

• Hierarchical path structure with "/" as delimiter

• CRUD operations (Create, Read, Update, Delete)

• Path validation including parent path existence

• Efficient storage and retrieval of files and directories. 

• Support for path-value associations.

‍

Point 3: Identifying Key Components : 

Key components of our File System design:

1. File System Node (Trie Node) :

• Common properties and methods for both files and directories

• Represents the base entity in the file system

‍

2. File Class:

• Represents individual files

• Stores file-specific metadata and content

‍

3. Directory Class:

• Manages collection of files and subdirectories

• Supports hierarchical organization

‍

4. File System Manager:

• Central manager for file system operations

• Handles path creation, validation, and retrieval

‍

Point 4: Design Challenges : 

Interviewer: What design challenges do you anticipate?

‍

Candidate: Key challenges include:

• Efficient path resolution and navigation.

• Handling nested path structures.

• Ensuring parent paths exist before creating child paths

• Managing path-value associations

• Supporting path traversal and retrieval

‍

Point 5: Design Approach : 

To address these challenges, I propose leveraging several design patterns:

1. Composite Pattern for Hierarchical Structure :

• Treat files and directories uniformly

• Enable recursive operations on file system nodes

‍

2. Singleton Pattern for File System Manager : 

• Centralized management of file system

• Ensure single point of control

‍

Interviewer: That sounds like a solid approach. Let's delve into the implementation details.

‍

Point 6: Implementation:

Interviewer: Ready to discuss implementation?

‍

Candidate: Yes. I'll focus on implementing the design patterns we discussed and show how they work together in the File System Problem :

‍

Design File System with Design Patterns : 
Path Representation : 
In our file system implementation, paths are represented using a trie data structure, which efficiently handles hierarchical data. This approach allows us to store and navigate the file system with optimal time complexity for operations like path creation, deletion, and retrieval.

‍

The trie representation maps each component of a path to a node in the tree, where:

• The root node represents the root directory "/"

• Each subsequent path component becomes a child node

• Directory nodes can have multiple children

• File nodes are always leaf nodes in the tree

• Each node contains metadata such as creation time and modification time.

‍

Path-to-Trie Mapping Example : 
Let's examine how a specific path is represented as a trie structure in our system. For example, the path:

/document/cwa_lld/solid_principles.java : Is broken down and represented in the trie as follows:
Article image

2. Now Suppose we add another file in the cwa_lld folder, let's say we add a new file "design_file_system.java", then it's corresponding  

     Path and trieNode structure will look like :  

/document/cwa_lld/design_file_system.java
Article image

‍

3. Now Suppose we add another folder in document folder, let's say we add a new folder "cwa_hld", and we create two files namely "horizontal_scaling.txt" and "consistent_hashing.java", then it's corresponding Path and trieNode structure will look like :  

/document/cwa_hld/horizontal_scaling.txt
/document/cwa_hld/consistent_hashing.java
‍

Article image

‍

Article image

‍

1. Composite Pattern for Managing File and Directory Hierarchical Structure :

‍

a. Define the Common Class / Interface : 

• FileSystemNode.java : 

• Abstract base class for file and directory nodes.

• Contains properties: name, createdAt, modifiedAt, and size.

Abstract methods: display(int depth), calculateSize(), and isDirectory().

‍

```Java
// Base class for File System Node (Composite Pattern)
public abstract class FileSystemNode {
  // Name of the node
  private String name;
  // Map of child nodes
  private Map<String, FileSystemNode> children;
  // Timestamp for node creation
  private LocalDateTime createdAt;
  // Timestamp for the last modification
  private LocalDateTime modifiedAt;
  // Constructor to initialize the node with a name
  public FileSystemNode(String name) {
    this.name = name;
    this.children = new HashMap<>();
    this.createdAt = LocalDateTime.now();
    this.modifiedAt = LocalDateTime.now();
  }

  // Add child node
  public void addChild(String name, FileSystemNode child) {
    this.children.put(name, child);
    this.modifiedAt = LocalDateTime.now();
  }

  // Check if child exists
  public boolean hasChild(String name) {
    return this.children.containsKey(name);
  }

  // Get child node by name
  public FileSystemNode getChild(String name) {
    return this.children.get(name);
  }

  // Remove child node
  public boolean removeChild(String name) {
    if (hasChild(name)) {
      children.remove(name);
      return true;
    }
    return false;
  }

  // Abstract methods for node operations
  public abstract boolean isFile();
  public abstract void display(int depth);

  // Getters and Setters
  public String getName() {
    return name;
  }

  public Collection<FileSystemNode> getChildren() {
    return children.values();
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  // Update the modification timestamp
  protected void updateModifiedTime() {
    this.modifiedAt = LocalDateTime.now();
  }
}
```
‍

b. Create Concrete File and Directory Classes : 

‍

File.java : 

• Represents individual files.

• Properties: content, type, and extension.

Methods: writeContent(String content), readContent(), display(int depth), calculateSize(), and isDirectory().

‍

```Java
// File class representing individual files (Leaf)
public class File extends FileSystemNode {
  // Content of the file
  private String content;
  // File extension
  private String extension;

  // Constructor for file with name
  public File(String name) {
    super(name);
    this.extension = extractExtension(name);
  }

  // Extract file extension from name
  private String extractExtension(String name) {
    int dotIndex = name.lastIndexOf('.');
    return (dotIndex > 0) ? name.substring(dotIndex + 1) : "";
  }

  // Set content of the file
  public void setContent(String content) {
    this.content = content;
    updateModifiedTime();
  }

  // Get content of the file
  public String getContent() {
    return content;
  }

  @Override
  public boolean isFile() {
    return true;
  }

  @Override
  public void display(int depth) {
    // Example: For a file at path "/document/cwa_lld/requirements.txt" at depth 3
    // indent = "      " (6 spaces: depth 3 * 2 spaces per depth)
    // Output would be: "      📄 requirements.txt"
    // For our example, if depth is 3 (meaning this file is at the 3rd level)
    // Generate indent string of 6 spaces (3*2)
    String indent = " ".repeat(depth * 2);
    // Print the file with appropriate indentation and emoji
    // e.g., "      📄 requirements.txt"
    System.out.println(indent + "📄 " + getName());
  }
}
‍```

Directory.java : 

• Represents directories.

• Contains a list of child nodes (children).

• Methods: addChild(FileSystemNode node), removeChild(FileSystemNode node), getChild(String name), display(int depth), and calculateSize().

‍

```Java
// Directory class representing directories (Composite)
public class Directory extends FileSystemNode {
  // Constructor for directory with name
  public Directory(String name) {
    super(name);
  }

  @Override
  public boolean isFile() {
    return false;
  }

  @Override
  public void display(int depth) {
    // Example: For a directory at path "/document/cwa_lld" at depth 2
    // indent = "    " (4 spaces: depth 2 * 2 spaces per depth)
    // Let's say it has 3 child items
    // Output would be: "    📁 cwa_lld (3 items)"
    // Then it will recursively display each child with depth 3


    // For our example, if depth is 2 (meaning this directory is at the 2nd level)
    // Generate indent string of 4 spaces (2*2)
    String indent = " ".repeat(depth * 2);

    // Print the directory name with appropriate indentation, emoji and number of children
    // e.g., "    📁 cwa_lld (3 items)"
    System.out.println(indent + "📁 " + getName() + " (" + getChildren().size() + " items)");
    // Then for each child (let's say we have "design_file_system" directory,
    // "requirements.txt" file, and "notes.md" file)
    // We recursively call display with depth+1 (3 in this case)
    // This will produce:
    //     "      📁 design_file_system (0 items)" (if empty directory)
    //     "      📄 requirements.txt"
    //     "      📄 notes.md"
    for (FileSystemNode child : getChildren()) {
      child.display(depth + 1);
    }
  }
}
```
‍

2. Implement The Core File System Class : 

‍

```Java
// Main File System class implementing the trie structure
public class FileSystem {
  // Root directory
  private FileSystemNode root;

  // Constructor to initialize the file system with a root directory
  public FileSystem() {
    this.root = new Directory("/");
  }
  // path = "/document/cwa_lld/design_file_system"
  // Checking if path is not null, not empty, and starts with
  // Validate file path to be non-empty and properly formatted
  public boolean isValidFilePath(String path) {
    // Returns true because path meets all criteria
    return path != null && !path.isEmpty() && path.startsWith("/");
  }

  // Create a new path
  // path = "/document/cwa_lld/design_file_system"
  public boolean createPath(String path) {
    // Validate path
    // path is valid, so continue
    if (!isValidFilePath(path))
      return false;
    // Split path into components
    // pathComponents = ["", "document", "cwa_lld", "design_file_system"]
    String[] pathComponents = path.split("/");
    // Start from root
    // current = root directory "/"
    FileSystemNode current = root;
    // Traverse to the parent directory
    // We need to process: "document" and "cwa_lld" (stopping before the last component)
    for (int i = 1; i < pathComponents.length - 1; i++) {
      String component = pathComponents[i];
      if (component.isEmpty())
        continue; // Skip empty components
      // First iteration: component = "document"
      // Second iteration: component = "cwa_lld"
      if (!current.hasChild(component)) {
        // If "document" doesn't exist, create it
        // If "cwa_lld" doesn't exist, create it
        FileSystemNode newDir = new Directory(component);
        current.addChild(component, newDir);
      }
      FileSystemNode child = current.getChild(component);
      if (child.isFile()) {
        // If "document" or "cwa_lld" is a file, we cannot navigate through it
        // Return false in that case
        return false;
      }
      // Move to the next level
      // First iteration: current = "document" directory
      // Second iteration: current = "cwa_lld" directory
      current = child;
    }
    // Get the last component (file or directory name)
    // lastComponent = "design_file_system"
    String lastComponent = pathComponents[pathComponents.length - 1];
    if (lastComponent.isEmpty())
      return false;
    // Check if the component already exists
    // If "design_file_system" already exists under "cwa_lld", return false
    if (current.hasChild(lastComponent)) {
      return false;
    }
    // Create new node based on whether it's a file (has extension) or directory
    // "design_file_system" has no dot, so create as directory
    FileSystemNode newNode;
    if (lastComponent.contains(".")) {
      newNode = new File(lastComponent);
    } else {
      newNode = new Directory(lastComponent);
    }
    // Add the new node to the parent
    // Add "design_file_system" directory to "cwa_lld"
    current.addChild(lastComponent, newNode);
    return true;
  }

  // Helper method to get node at path
  // path = "/document/cwa_lld/design_file_system"
  private FileSystemNode getNode(String path) {
    // Check if path is valid
    // Path is valid, so continue
    if (!isValidFilePath(path))
      return null;
    // For root path
    // Path is not "/", so skip this
    if (path.equals("/"))
      return root;
    // Split path into components
    // pathComponents = ["", "document", "cwa_lld", "design_file_system"]
    String[] pathComponents = path.split("/");
    // Start from root
    // current = root directory "/"
    FileSystemNode current = root;
    // Traverse through the path
    // We need to process: "document", "cwa_lld", and "design_file_system"
    for (int i = 1; i < pathComponents.length; i++) {
      String component = pathComponents[i];
      if (component.isEmpty())
        continue; // Skip empty components
      // First iteration: component = "document"
      // Second iteration: component = "cwa_lld"
      // Third iteration: component = "design_file_system
      if (!current.hasChild(component)) {
        // If any component doesn't exist at its level, return null
        return null;
      }
      // Move to the next level
      // First iteration: current = "document" directory
      // Second iteration: current = "cwa_lld" directory
      // Third iteration: current = "design_file_system" directory
      current = current.getChild(component);
    }
    // Return the node found at the path
    // Returns the "design_file_system" directory node
    return current;
  }

  // Delete path
  public boolean deletePath(String path) {
    // path = "/document/cwa_lld/design_file_system"
    // Check if path is valid
    // Path is valid, so continue
    if (!isValidFilePath(path))
      return false;
    // Can't delete root
    // Path is not "/", so continue
    if (path.equals("/"))
      return false;
    // Get parent path
    // parentPath = "/document/cwa_lld"
    String parentPath = getParentPath(path);
    // Get the parent node
    // parent = "cwa_lld" directory node
    FileSystemNode parent = getNode(parentPath);
    // If parent doesn't exist or is a file, can't delete
    // Assuming parent exists and is a directory, continue
    if (parent == null || parent.isFile())
      return false;
    // Get the last component of the path
    // lastComponent = "design_file_system"
    String lastComponent = path.substring(path.lastIndexOf('/') + 1);
    // Check if the component exists
    // If "design_file_system" doesn't exist under "cwa_lld", return false
    if (!parent.hasChild(lastComponent)) {
      return false;
    }
    // Remove the child from the parent
    // Remove "design_file_system" from "cwa_lld"
    return parent.removeChild(lastComponent);
  }

  // Helper method to get parent path
  private String getParentPath(String path) {
    // path = "/document/cwa_lld/design_file_system"
    // Find the last slash
    // lastSlash = 19 (position of last slash before "design_file_system")
    int lastSlash = path.lastIndexOf('/');
    // If there's no slash or only the root slash, return root
    // lastSlash is 19, which is > 0, so continue
    if (lastSlash <= 0) {
      return "/";
    }
    // Return the substring up to the last slash
    // Returns "/document/cwa_lld"
    return path.substring(0, lastSlash);
  }

  // Display the entire file system structure
  public void display() {
    root.display(0);
  }

  // Set content for file
  public boolean setFileContent(String path, String content) {
    FileSystemNode node = getNode(path);
    if (node == null || !node.isFile())
      return false;
    File file = (File) node;
    file.setContent(content);
    return true;
  }

  // Get content from file
  public String getFileContent(String path) {
    FileSystemNode node = getNode(path);
    if (node == null || !node.isFile())
      return null;
    File file = (File) node;
    return file.getContent();
  }
}
```
‍

3. Write the Main Method / Client Code to run the File System : 

‍

```Java
// Client code to test the file system
public class FileSystemClient {
  public static void main(String[] args) {
    // Create a new file system instance
    FileSystem fs = new FileSystem();
    // Create a scanner to handle user input
    Scanner scanner = new Scanner(System.in);
    boolean isRunning = true; // Flag to control the program loop
    // Display instructions for the user
    System.out.println("File System Manager - Commands:");
    System.out.println("1. create <path> - Create a new path");
    System.out.println("2. write <path> <content> - Write content to a file");
    System.out.println("3. read <path> - Read content from a file");
    System.out.println("4. delete <path> - Delete a path");
    System.out.println("5. display - Show the entire file system structure");
    System.out.println("6. exit - Exit the program");
    // Main program loop to process commands
    while (isRunning) {
      System.out.print("nEnter command: ");
      String input = scanner.nextLine().trim(); // Read and trim user input
      String[] parts =
          input.split("s+", 3); // Split input into command, path, and possibly content
      if (parts.length == 0)
        continue; // Skip empty input
      String command = parts[0].toLowerCase(); // Get the command
      try {
        switch (command) {
          case "create":
            // Create a new path
            if (parts.length >= 2) {
              String path = parts[1]; // Extract path
              boolean isCreated = fs.createPath(path); // Attempt to create the path
              System.out.println(isCreated ? "Path created successfully" : "Failed to create path");
            } else {
              System.out.println("Usage: create <path>");
            }
            break;
          case "write":
            // Write content to a file
            if (parts.length >= 3) {
              String path = parts[1]; // Extract path
              String content = parts[2]; // Extract content
              boolean isWritten = fs.setFileContent(path, content); // Attempt to write content
              System.out.println(
                  isWritten ? "Content written successfully" : "Failed to write content");
            } else {
              System.out.println("Usage: write <path> <content>");
            }
            break;
          case "read":
            // Read content from a file
            if (parts.length >= 2) {
              String path = parts[1]; // Extract path
              String content = fs.getFileContent(path); // Attempt to read content
              if (content != null) {
                System.out.println("Content: " + content);
              } else {
                System.out.println("Failed to read content");
              }
            } else {
              System.out.println("Usage: read <path>");
            }
            break;
          case "delete":
            // Delete a specific path from the file system
            if (parts.length >= 2) {
              String path = parts[1]; // Extract path
              boolean isDeleted = fs.deletePath(path); // Attempt to delete the path
              System.out.println(isDeleted ? "Path deleted successfully" : "Failed to delete path");
            } else {
              System.out.println("Usage: delete <path>");
            }
            break;
          case "display":
            // Display the entire file system structure
            System.out.println("nFile System Structure:");
            fs.display();
            break;
          case "exit":
            // Exit the program
            isRunning = false;
            System.out.println("Exiting...");
            break;
          default:
            // Handle unknown commands
            System.out.println(
                "Unknown command. Available commands: create, write, read, delete, display, exit");
        }
      } catch (Exception e) {
        // Handle general exceptions
        System.out.println("Error: " + e.getMessage());
      }
    }
    // Close the scanner to release system resources
    scanner.close();
  }
}
‍```

Interviewer: Looks good. What makes your approach effective?

‍

Candidate: Here are the key strengths of my approach for the File System:

Composite Pattern Implementation:
I've implemented the Composite pattern to represent the file system hierarchy, where both files and directories share a common interface. This allows uniform treatment of individual files and directories while supporting recursive operations.

‍

2. Single Responsibility Principle:

Each class has a distinct responsibility. The File class represents individual files, Directory manages collections of files and subdirectories, and the overall file system operations are handled cohesively without violating SRP.

‍

3. Extensibility:

The design allows easy addition of new file types or directory behaviors without modifying existing code. The common interface ensures that new components integrate seamlessly into the existing structure.

‍

4. Maintainability:

The separation of concerns makes the system easy to manage and extend. Operations like adding, removing, or searching files and directories are encapsulated within relevant classes, reducing code complexity.

‍

5. Real-world Modeling:

The implementation closely resembles an actual file system, where directories can contain files or other directories, and operations like traversal, searching, and organization follow a natural hierarchical structure.

‍

Conclusion : 
This low-level design for the File System demonstrates a well-structured, scalable, and extensible architecture. It emphasizes modularity by clearly defining components such as File, Directory, and the overall file system structure. The design leverages the Composite pattern to enable seamless management of files and directories, ensuring a flexible and hierarchical organization.

‍

In an interview setting, presenting this design highlights your ability to build robust and adaptable solutions, showcasing your expertise in applying design patterns, best practices, and architectural principles to real-world scenarios.
