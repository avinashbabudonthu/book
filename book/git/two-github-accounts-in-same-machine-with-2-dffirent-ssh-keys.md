# Push 2 different git accounts with different ssh keys
------
* Create ssh keys using steps in [ssh](ssh.md)
* Create `~/.ssh/config` file
```
Host avinash4216
  HostName github.com
  User git
  IdentityFile ~/.ssh/id_rsa
Host avinashbabu.donthu
  HostName github.com
  User git
  IdentityFile ~/.ssh/avinashbabu.donthu_github_rsa
```
* Replace account-one, account-two with descriptive names for your accounts
* When cloning a repository, use the appropriate Host alias from your ~/.ssh/config file in the URL
```
git clone git@account-one:your_username/your_repo.git
```
*  if using HTTPS, you can specify the key directly in the git clone command
```
git clone --ssh-key=<path_to_key> https://github.com/your_username/your_repo.git
```
* Update url of existing repo using below command
```
git remote set-url origin git@avinashbabu.donthu:avinashbabudonthu/book.git
```