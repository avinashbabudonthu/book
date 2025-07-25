# Kubectl Commands
------
* Version
```
kubectl version
```
* List available commands
```
kubectl
```
* Create `nginx` deployment
```
kubectl create deployment NAME --image=image [--dry-run] [options]
kubectl create deployment [deployment-name] --image=[image-name]
kubectl create deployment nginx01 --image=nginx
```
* Edit deployment
```
kubectl edit deployment [deployment-name]
kubectl edit deployment nginx01
```
* Delete deployment
```
kubectl delete deployment [deployment-name]
kubectl delete deployment nginx01
```
* Get nodes
```
kubectl get nodes
```
* Get deployments
```
kubectl get deployment
kubectl get deployments
```
* Get pods
```
kubectl get pod
kubectl get pods
```
* Get services
```
kubectl get services
```
* Describe service
```
kubectl describe service [service-name]
```
* Get replicaset
```
kubectl get replicaset
kubectl get replicasets
```
* Describe pod
```
kubectl describe pod [pod-name]
kubectl describe pod nginx01-7467ddddb-n869d
```
* Get more information of pod
```
kubectl get pod -o wide
```
* Check logs
```
kubectl logs [pod-name]
kubectl logs nginx01-7467ddddb-n869d
```
* Login into pod. Interactive terminal
```
kubectl exec -it [pod-name] -- bin/bash
kubectl exec -it nginx01-7467ddddb-n869d -- bin/bash
```
* Apply configuration file. Read [Configuration file notes](config-file-notes.md)
```
kubectl apply -f config-file.yaml
```
* Delete deployment
```
kubectl delete -f config-file.yaml
```
* Get updated information of deployment. This actual stored in etcd
```
kubectl get deployment [deployment-name] -o yaml
```
* Save deployment information etcd to file
```
kubectl get deployment [deployment-name] -o yaml > deployment-name-result.yaml
```
* Deploy container to kubernetes cluster
```
kubectl run hello-minikube
```
* get cluster info
```
kubectl cluster-info
```