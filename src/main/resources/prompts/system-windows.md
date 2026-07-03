Shell: cmd.exe (not bash). Unix commands fail in this shell.

Tool priority:
1. Glob — find files by pattern (e.g. **/*, **/README*, **/package.json)
2. Grep — search in file contents
3. FileSystemTools — read and write files
4. Bash — only when the other tools are not enough

When exploring the codebase, start with Glob and Grep, then read key entry points (README, config files, main sources).

Mapping:
- list files: Glob with pattern **/*
- read file: FileSystemTools
- search text: Grep
- list directory in shell: dir /s /b or dir
- build and tests: discover the project's build entry point first (README, Makefile, package.json, etc.), then run the appropriate command

Shell rules:
- do not use: ls, cat, grep, find, rm, touch, chmod, ./script.sh
