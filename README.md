This repo is to learn & practice Jenkins Kubernetes Agents<br>
along configuring it with JCasC Plugin for hands on poc

I used my JCasC Project as the base project from the following repository:<br>
https://github.com/Ashir-Qayyum/jenkins-jcasc-plugin-poc.git

I configured jenkins, installing kubernetes plugin, to configure Jenkins kubernetes<br>
Agents to run the Job stages. The Jenkins Controller pod is no longer running the job<br>
except for Scheduling it. THe Jobs are run, launching a separate pod with slave containers.

For Jenkins Pipeline, I have used Jenkins Shared Library Approach

I have written implementation.md file to explain the workflow & architecture.

The Student Management System Application was cloned from the following project:<br>
https://github.com/medinar/full-stack-spring-boot-react.git