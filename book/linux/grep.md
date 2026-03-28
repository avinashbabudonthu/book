# grep

**g/re/p** — globally search for a **re**gular expression and **p**rint.

## Syntax

```text
grep [options] [pattern] [files]
```

## Basic search

Search for a word in a file:

```bash
grep "python" notes.txt
```

## Recursive search

Search recursively in all files under a directory (e.g. word `error` under `/var/log`):

```bash
grep -r "error" /var/log
```

If you are not root, you may need `sudo` for protected paths:

```bash
sudo grep -r "error" /var/log
```

## Case insensitive

```bash
grep -i "PyThon" notes.txt
```

## Count matches

```bash
grep -c "python" notes.txt
```

Case insensitive count:

```bash
grep -ci "PyThon" notes.txt
```

## Matching filenames only

```bash
grep -l "unix" *
grep -l "unix" f1.txt f2.txt f3.xt f4.txt
```

## Whole words

```bash
grep -w "unix" geekfile.txt
```

## Only the matched text (`-o`)

By default, grep prints the whole line. Use `-o` to print only the matched substring:

```bash
grep -o "unix" geekfile.txt
```

## Line numbers (`-n`)

```bash
grep -n "unix" geekfile.txt
```

## Invert match (`-v`)

Lines that do **not** match the pattern:

```bash
grep -v "unix" geekfile.txt
```

## Start and end of line

Lines starting with a string (`^`):

```bash
grep "^unix" geekfile.txt
```

Lines ending with a string (`$`):

```bash
grep "os$" geekfile.txt
```

## Patterns from a file (`-f`)

Search for multiple patterns listed in one file inside another file.

Example `pattern.txt`:

```text
Agarwal
Aggarwal
Agrawal
```

```bash
grep -f pattern.txt geekfile.txt
```

## Context lines (`-A`, `-B`, `-C`)

- **`-A`** — matched line and *n* lines **after**
- **`-B`** — matched line and *n* lines **before**
- **`-C`** — matched line and *n* lines **before and after**

```bash
grep -A[n] [search] [file]
grep -B[n] [search] [file]
grep -C[n] [search] [file]
```

Replace `[n]` with the number of context lines.
