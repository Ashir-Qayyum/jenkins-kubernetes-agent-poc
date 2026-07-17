After installing jenkins in the cluster, I<br>
applied ClusterRoleBinding to jenkins service account<br>
such that it can perform the Helm deployment<br>
By default the jenkins service account has the least<br> 
privelege with which it cannot do deploymente, so<br> 
I am provisioning cluster-admin role

I applied it in the cluster with:<br>
> kubectl apply -f jenkins-admin-crb.yaml