# How to Install Minikube on Windows 11 Pro
------
## Prerequisites
- Ensure virtualization is enabled in your system BIOS.
- Install a hypervisor like Hyper-V, VirtualBox, or Docker.

## Steps to Install Minikube
* Enable Virtualization
Open PowerShell as Administrator and run:
```
* Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Hyper-V -All
```
* Restart your system after enabling Hyper-V
* Download Minikube. Visit the [Minikube releases page](https://github.com/kubernetes/minikube/releases) and download the latest installer for Windows. Alternatively, use PowerShell:
```
Invoke-WebRequest -OutFile minikube-installer.exe -Uri https://github.com/kubernetes/minikube/releases/latest/download/minikube-installer.exe
Start-Process .\minikube-installer.exe
```
* Default installation location
```
C:\Program Files\Kubernetes\Minikube
```
* Install kubectl Run the following command in PowerShell to install :
```
winget install -e --id Kubernetes.kubectl
```
* Start Minikube - Open a terminal and start Minikube with:
```
minikube start
```
* Verify the Installation. Check the status of your cluster:
```
minikube status
```
* Access the Kubernetes dashboard:
```
minikube dashboard
```