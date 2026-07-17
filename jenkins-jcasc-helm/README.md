ON top of the official Jenkins Helm chart 5.9.33<br>
I have customized the helm chart with my custom jcasc configurations

The Flow is the following:

files/jcasc/*.yaml -> Helm Chart -> Kubernetes ConfigMap (i defined under templates/) -> Mounted inside Jenkins Pod<br>
-> JCasC Plugin reads all YAML files -> Jenkins gets configured automatically 

This is the official Jenkins Helm chart builtin support for JCasC.

I use its supported mechanism to:<br>
Mount the ConfigMap<br>
Set CASC_JENKINS_CONFIG<br>
Load the YAML automatically<br>

Jenkins internally works as:<br>
Needs Configurations files at location: /var/jenkins_home/casc_configs<br>
Jenkins Uses the ENV VAR: CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs which is created during installation<br>
JCasC loads every YAML automatically using this ENV VAR & configure the Jenkins

What I am doing in jenkins kubernetes setup:<br>
Mount ConfigMap<br>
inside Jenkins Controller Pod<br>
at /var/jenkins_home/casc_configs<br>
It Sets<br>
CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs<br>
JCasC loads every YAML automatically<br>

---

First i wrote the Chart.yaml file with the official jenkins helm chart & app dependency<br>
, and downloaded the defined dependency with (running inside jenkins-jcasc-helm/ dir):<br>

>> helm dependency update<br>

This downloaded the tgz file depedency under the charts/ dir

THEN, I wrote all the jcasc config files under files/jcasc/ for appearance,<br>
credentials, security, system etc.

Then I wrote the configmap file jcasc-configmap.yaml under templates/<br>
and  referrenced the jcasc configuration files to set the files as the ConfigMap<br>
I included all the 7 file in data.<br>
AND SET jenkins-jenkins-config: "true"<br>
This is most imp. such sidecar detect this configmap and copies all the files<br>
to the loc: /var/jenkins_home/casc_configs which later sets to <br>
CASC_JENKINS_CONFIG   upon jenkins start


Then I wrote the values.yaml file including plugins and other properties, and<br>
Enabling ConfigMap that I custom created - such that the casc config files are<br>
set into the jenkins pod at loc: /var/jenkins_home/casc_configs


---FOR INSTALLATION---


I created separate namespace 'jenkins'<br>
>> kubectl create namespace jenkins<br>

Verified with: kubectl get ns

Then I installed jenkins using the values.yaml<br>
>> helm install jenkins . -f values.yaml -n jenkins

I get the jenkins service name using:<br>
>> kubectl get svc -n jenkins<br>

Then Port-forwarded the svc to Access jenkins UI at localhost:8080<br>
,as I am using Minikube on docker-driver with PortType NodePort:<br>
>> kubectl port-forward -n jenkins svc/jenkins 8080:8080 &

Logged in with username & password I provided in the values.yaml<br>
, AND later changed the password