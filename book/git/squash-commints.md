# Git Rebase and Squash commits
------
### To rebase against develop branch
* open git bash
* checkout develop
```
git checkout develop
```
* Pull
```
git pull
```
* Checkout to your branch
```
git checkout [branch-name]
```
* Rebase
```
git rebase -i develop
```
* Press `i`
* Replace `pick` with `squash`
* Press `Esc`
* Save and exit the editor
```
:wq
```
* Squash will start and show the list of commits. Type `100dd` this will delete last 100 commits. `XXdd` removed last `XX` commit
* press i
* Enter commit message
* Press `Esc`
* Save and exit the editor
```
:wq
```
* Force Push the changes
```
git push -f
```