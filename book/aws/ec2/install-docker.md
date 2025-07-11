# 🐳 Install Docker
---
```bash
sudo yum install docker -y
sudo systemctl start docker
sudo systemctl enable docker
```

### Enable Docker for `ec2-user` (no `sudo` needed)

```bash
sudo usermod -aG docker ec2-user
```

Either log out and log back in, or run:

```bash
newgrp docker
```

### ✅ Verify Docker Installation

```bash
sudo docker version
sudo docker info
```

---

## 🔧 Install Docker Compose

```bash
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose version
```