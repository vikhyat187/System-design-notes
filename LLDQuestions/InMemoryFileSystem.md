
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
