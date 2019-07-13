
docker build -t book-store-sample/book-detail-service --build-arg JAR_FILE=./book-detail-service-0.0.1-SNAPSHOT.jar .
docker build -t book-store-sample/book-store-app --build-arg JAR_FILE=./book-store-app-0.0.1-SNAPSHOT.jar .

kubectl run book-detail-service --image=book-store-sample/book-detail-service --replicas=2
kubectl run book-store-app --image=book-store-sample/book-store-app --replicas=2

kubectl expose deployment book-detail-service --port=8180 --target-port=8180
kubectl expose deployment book-store-app --port=8181 --target-port=8181


---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: spring-cloud-kubernetes
rules:
  - apiGroups:
      - ""
    resources:
      - pods
      - services
      - endpoints
      - secrets
      - configmaps
    verbs:
      - get
      - list
      - watch
---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: spring-cloud-kubernetes
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: spring-cloud-kubernetes
subjects:
- kind: ServiceAccount
  name: default
  namespace: spring