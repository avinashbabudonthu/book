# Kubectl Commands
------
* Get nodes
```
kubectl get nodes
```
* Version
```
kubectl version
```
* List available commands
```
kubectl
```
* Get services
```
kubectl get services
```
* Create `nginx` deployment
```
kubectl create deployment [deployment-name] --image=[image-name]
kubectl create deployment nginx01 --image=nginx
```
* Get deployments
```
kubectl get deployment
```
* Get pods
```
kubectl get pod
```
* Get replicaset
```
kubectl get replicaset
```
* Edit deployment
```
kubectl edit deployment [deployment-name]
kubectl edit deployment nginx01
```