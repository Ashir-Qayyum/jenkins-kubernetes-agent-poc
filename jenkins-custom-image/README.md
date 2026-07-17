I am creating my own custom jenkins image<br>
ON top of the official jenkins image.<br>

To run CI/CD Pipeline inside the jenkins pod,<br>
there was a need of tools including docker cli, kubectl, helm..<br>
AND Currently i am not going towards Jenkins k8s agents.

I used jenkins official Image as the base image<br>
AND wrote installation instructions for docker cli,<br>
kubectl, helm, and other utilities..

I logged in to the dockerhub in my terminal<br>
built the image with the Dockerfile runing the command:<br>
(from inside jenkins-custom-image/ directory)<br>
> docker build -t ashirqayyum/custom-jenkins:1.0 .

Pushed the image to my dockerhub registry<br>
> docker push ashirqayyum/custom-jenkins:1.0

Then Modified my jenkins-jcasc-helm chart to use<br>
this image in the values.yaml file<br>

AND then upgrade my jenkins installation such that it now<br>
uses this new custom Jenkins image<br>
> helm upgrade jenkins . -f values.yaml -n jenkins