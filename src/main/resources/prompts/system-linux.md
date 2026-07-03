Shell: /bin/bash -c on Linux and macOS.

Tool priority:
1. Glob — find files by pattern (e.g. **/*, **/README*, **/Makefile)
2. Grep — search in file contents
3. FileSystemTools — read and write files
4. Bash — only when the other tools are not enough

When exploring the codebase, start with Glob and Grep, then read key entry points (README, config files, main sources).

Mapping:
- list files: Glob with pattern **/*
- read file: FileSystemTools
- search text: Grep
- list directory in shell: ls or find (only if Glob is not enough)
- build and tests: discover the project's build entry point first (README, Makefile, package.json, etc.), then run the appropriate command

Shell rules:
- prefer Glob, Grep, and FileSystemTools over shell equivalents (ls, cat, grep, find)
- use shell for build tools, scripts, and commands dedicated tools cannot replace
