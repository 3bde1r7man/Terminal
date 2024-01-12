
# List of Commands

| Command | Description |
| ------- | ----------- |
| `echo` | Takes 1 argument and prints it. |
| `pwd` | Takes no arguments and prints the current path. |
| `cd` | Changes the current directory. |
| `ls` | Lists contents of the current directory. |
| `ls -r` | Lists contents in reverse order. |
| `mkdir` | Creates one or more directories. |
| `rmdir` | Removes empty directories. |
| `touch` | Creates a file. |
| `cp` | Copies files or directories. |
| `rm` | Removes a file. |
| `cat` | Displays file content or concatenates files. |
| `wc` | Word count for files. |
| `>` | Redirects output to a file (creates or replaces). |
| `>>` | Appends output to a file. |
| `history` | Displays a history of commands used. |

## Details of Each Command

### `echo`
Takes 1 argument and prints it.

### `pwd`
Takes no arguments and prints the current path.

### `cd`
1. Takes no arguments and changes the current path to the home directory.
2. Takes 1 argument, "..", and changes the current directory to the previous directory.
3. Takes 1 argument (full or relative path) and changes the current path to that path.

### `ls`
Takes no arguments and lists the contents of the current directory sorted alphabetically.

### `ls -r`
Takes no arguments and lists the contents of the current directory in reverse order.

### `mkdir`
Takes 1 or more arguments and creates a directory for each argument.

### `rmdir`
1. Takes 1 argument, "*", and removes all empty directories in the current directory.
2. Takes 1 argument (full or relative path) and removes the given directory if it is empty.

### `touch`
Takes 1 argument (full or relative path ending with a file name) and creates the file.

### `cp`
Takes 2 arguments (both files) and copies the first onto the second.

### `cp -r`
Takes 2 arguments (both directories) and copies the first directory (with all content) into the second.

### `rm`
Takes 1 argument (file name) that exists in the current directory and removes the file.

### `cat`
Takes 1 argument and prints the file's content or takes 2 arguments and concatenates the content of the 2 files.

### `wc`
Displays word count for files. Default output includes lines, words, characters with spaces, and file name.

### `>`
Redirects the output of the first command to be written to a file (creates or replaces).

### `>>`
Like `>`, but appends to the file if it exists.

### `history`
Takes no parameters and displays an enumerated list with the commands used in the past.
