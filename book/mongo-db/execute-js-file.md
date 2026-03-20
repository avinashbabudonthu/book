## Execute a JavaScript file from `mongosh`

`mongosh` can run JavaScript files in two common ways:

1. **Run the script from the command line** (most common for automation).
2. **Connect to MongoDB interactively, then load the script** with `load(...)`.

---

## 1) Run a script directly (command line)

`mongosh` will execute the provided file(s) and then exit (unless you add `--shell`).

### Example: run your script

```powershell
mongosh "mongodb+srv://<USER>:<PASSWORD>@<CLUSTER>/<DB_NAME>" `
  "./supports/2026/Retha/skip-properties/step1-export-from-cosmos.mongosh.js"
```

### Same thing using `--file`

```powershell
mongosh "mongodb+srv://<USER>:<PASSWORD>@<CLUSTER>/<DB_NAME>" `
  --file "./supports/2026/Retha/skip-properties/step1-export-from-cosmos.mongosh.js"
```

### Keep the interactive shell open after running

```powershell
mongosh "mongodb+srv://<USER>:<PASSWORD>@<CLUSTER>/<DB_NAME>" `
  "./supports/2026/Retha/skip-properties/step1-export-from-cosmos.mongosh.js" `
  --shell
```

---

## 2) Load a script from an interactive `mongosh` session

1. Start `mongosh` (connect to the database):

```powershell
mongosh "mongodb+srv://<USER>:<PASSWORD>@<CLUSTER>/<DB_NAME>"
```

2. At the `mongosh` prompt, load your script:

```javascript
> load("./supports/2026/Retha/skip-properties/step1-export-from-cosmos.mongosh.js")
```

Tip: wrap paths in quotes. On Windows, you can generally use forward slashes (`/`) in the path.

---

## Notes / troubleshooting

- The file name must end with `.js` (and `mongosh` also recognizes `.mongodb`).
- If your script relies on `db`/collections, make sure you are connecting to MongoDB (don’t use `--nodb`).