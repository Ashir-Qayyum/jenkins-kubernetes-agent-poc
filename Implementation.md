I cloned my JCasC Plugin + Manual DinD Sidecar Container Project from:<br>
https://github.com/Ashir-Qayyum/jenkins-jcasc-plugin-poc.git<br>
(The project uses shared-library functions for CI/CD pipeline)

I modified the values.yaml at jenkins-jcasc-helm/values.yaml to remove<br>
the sidecars section, security, and volumes section as this time I<br>
am no longer launching DinD Sidecar Container and share docker daemon<br>
for building the docker images.

Rather, I am using Jenkins Kubernetes Plugin to launch kubernetes agent pod with<br>
containers specific to the pipeline stages & with the required tools installed<br>
These ephemeral containers contain the tools required in the pipeline stage,<br>
They perform the task in the functions ,and get terminated with the pod once the task is completed.

In my Jenkinsfile, I have included all the kubernetes agents configurations i required for the<br>
pipeline including the shared volumes, and containers of DinD (contains docker daemon<br>
for image building, shared it to docker CLI), docker CLI (for login, build, & push the image to dockerhub), <br>
and Helm (for installing the sms deployment stacks) using their official public images.<br>
Later, in the pipeline stages, I wrapped the pipeline functions inside the containers<br>
required to execute the task with specified tools.

THE FLOW IS THE FOLLOWING:

The pipeline job is triggered.<br>
Jenkins controller will launch the ephemeral containers defined as the agents<br>
in the jenkins-k8s-agent-pipeline pod.<br>
Each stages will be executed inside the particular container it is defined in with.<br>
docker container with docker:29-cli image will execute the dockerhub login, image<br>
building and pushing job.<br>
During the build, the docker daemon is provided by the DinD container via shared volumes<br>
Once the image is built & pushed, the helm container will execute the deployment stage<br>
AND upgrade the helm installation of SMS App Stacks.

INSTALLATION:

First, I created the namespace for jenkins<br>
> kubectl create ns jenkins<br>

(Verfify it with: kubectl get ns)<br>

I installed jenkins using my custom jenkins helm chart (from inside jenkins-jcasc-helm/):<br>
> helm install jenkins . -f values.yaml -n jenkins<br>

This installed jenkins with the plugins & also JCasC Configurations<br>
Git, CasC, Kubernetes Agents and other plugins is installed along with the installation<br>
The Jenkins is Configured with the JCasC files I created at /jenkins-jcasc-helm/files/jcasc<br>
which most importantly configure my dockerhub credentials, shared-library source repo<br>
, and the kubernetes Agent (I am using Minikube. I've installed & configured jenkins, k8s agents,<br> 
and the app deployments in the same minikube cluster)

I created the ClusterRoleBinding earlier. The CRBs does not get deleted when<br>
we delete even the whole namespace, therefore, i didn't applied the jenkins-admin-crb.yaml<br>
file again (at /jenkins-sa-config/jenkins-admin-crb.yaml) for creating the crb for jenkins service<br>
account. So when I installed jenkins, the jenkins service accout is created, and it bound with the<br>
ClusterRoleBinding I already applied.<br>
Therefore, it is NOT present in the terminal screenshot below.<br>
If I haven't created it earlier, I would have applied it with (running at /jenkins-sa-config/):<br>
> kubectl apply -f jenkins-admin-crb.yaml

Then I get the jenkins service name with:<br>
> kubectl get svc -n jenkins<br>

And Port-forwarded it to access the UI (As I am using Minikube with NodePort):<br>
(running in the background at different terminal tab)
> kubectl port-forward svc/jenkins -n jenkins 8080:8080 --address 0.0.0.0 &<br>

And accessed the Jenkins UI in the browser at: http://localhost:8080<br>

After logging in with the admin username & Password I provided in the valuesa.yaml<br>
I first validated all the Configuration I provided using JCasC Plugin<br>
including dockerhub credentials, shared-library, and kubernetes agent configuration<br>
Finally, tested connection with the cloud kubernetes configuration<br>
at http://localhost:8080/manage/cloud/kubernetes/configure

Then I create a new job for the pipeline, configure it with my GitHub repository:<br>
https://github.com/Ashir-Qayyum/jenkins-kubernetes-agent-poc.git<br>
with manual build, Job SCM with Jenkinsfile, and other configurations

FINALLY, I tested the pipeline by triggering the Build. The following flows happened<br>
Initially there were 2 running containers in the jenkins pod including jenkins (controller)
and config-reload. 
After triggering the pipeline, the new pod was launched jenkins-k8s-agent-pipeline<br>
with 4 containers inside the jenkins-k8s-agent-pipeline pod, jnlp, dind, helm, docker<br>
The Stages were executed inside the different containers of the agent pod as defined in the<br>
pipeline. Once the pipelie execution was completed, the containers and pod were terminated<br>

Testing kubernetes cloud configurations connection:<br>
![screenshots/image.png](screenshots/image.png)<br>

Triggering the Build & Pipeline Status:<br>
![screenshots/image (2).png](<screenshots/image (2).png>)<br>
![screenshots/image (3).png](<screenshots/image (3).png>)<br>

Terminal Screenshot (installations, and pods/containers status)<br>
![screenshots/image (4).png](<screenshots/image (4).png>)<br>

FINALLY, the SMS App Stacks was deployed using helm through<br>
the Pipeline including frontend, backend, and postgres<br>
(as can be seen in the terminal Output Above)

I get the frontend service name with:<br>
> kubectl get svc -n jenkins<br>

,And port-forwarded the frontend service to access<br>
the Application UI at port 8081 & tested it<br>
> kubectl port-forward svc/frontend -n jenkins 8081:80 --address 0.0.0.0 &

Port-forwarding:
![screenshots/image (6).png](<screenshots/image (6).png>)

SMS Frontend:
![screenshots/image (5).png](<screenshots/image (5).png>)