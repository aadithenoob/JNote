# JNote

Hobby-project and command-line utility for creating and managing text files with metadata tracking, only for Windows as of right now.\
*(Future releases will have MacOS & Linux support)*

## Installation

### Option 1: Portable

Download the latest portable release from [Releases](https://github.com/aadithenoob/JNote/releases).

A trimmed JRE is bundled with the portable ZIP.

1. Extract `JNote-0.2.0-alpha-portable.zip`
2. Run `install.bat` to add JNote to your PATH
3. Use `jnote` from any terminal

### Option 2: Build from source

Requires Java 21 or higher (tested with Java 21):
```powershell
git clone https://github.com/aadithenoob/JNote
cd JNote
mvn clean package # or use .\mvnw clean package if Maven is not installed globally
java -jar target/JNote-1.0-SNAPSHOT-shaded.jar [command]
```

* Maven will automatically download dependencies (Gson 2.10.1, PicoCLI 4.7.0).
* The JAR is fully shaded, so no external dependencies are needed.
* Verified on Windows 11 x64 with Java 21.0.3.

## Commands

### create
```powershell
jnote create <filename> [filepath]
```
Create a file. Enter content, then type `EOF` on a new line to save. 
* Optional filepath; `.md` appended automatically if no extension is given.
* Prompts before overwriting an existing file.

### list
```powershell
jnote list
```
Shows all tracked files with timestamp, filepath, and UUID.
Outputs `No notes found.` if there are none.

### delete
```powershell
jnote delete <filename>
```
Removes file and metadata.
Outputs `Note not found.` if specified file is not found.

## Example
```powershell
PS> jnote create notes W:\Documents
Enter Note Content (type 'EOF' on a new line to finish):
 - **Mitochondria** is the powerhouse of the cell.
EOF

--- Note Created ---
Title: notes.md
Filepath: W:\Documents\notes.md
Timestamp: 2025-10-26T16:56:39.822779300
UUID: b163af6a-1204-44a4-ae61-240da1a7721f
PS> jnote create file.py W:\Code
Enter Note Content (type 'EOF' on a new line to finish):
print("Hello, World!")
EOF

--- Note Created ---
Title: file.py
Filepath: W:\Code\file.py
Timestamp: 2025-10-26T16:57:42.293859900
UUID: 80df9f6b-2dba-4830-a1d3-930678d04dda
PS> python W:\Code\file.py
Hello, World!
PS> jnote list

=== All Notes (2) ===

[1] notes.md
    Created: 2025-10-26T16:56:39.822779300
    Path: W:\Documents\notes.md
    ID: b163af6a-1204-44a4-ae61-240da1a7721f

[2] file.py
    Created: 2025-10-26T16:57:42.293859900
    Path: W:\Code\file.py
    ID: 80df9f6b-2dba-4830-a1d3-930678d04dda

PS> jnote delete file.py
Note file deleted: file.py

--- Note Deleted ---
Title: file.py
ID: 80df9f6b-2dba-4830-a1d3-930678d04dda
PS> jnote list

=== All Notes (1) ===

[1] notes.md
    Created: 2025-10-26T16:56:39.822779300
    Path: W:\Documents\notes.md
    ID: b163af6a-1204-44a4-ae61-240da1a7721f
```

---
## Details
* Built with PicoCLI.
* Metadata stored as JSON in `notes-metadata.json`.
* Search functionality will be added in future versions.

## License
MIT
