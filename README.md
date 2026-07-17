This repo is to learn & practice Jenkins Kubernetes Agents<br>
along configuring it with JCasC Plugin for hands on poc

I cloned my JCasC Project from the following repository:<br>
https://github.com/Ashir-Qayyum/jenkins-jcasc-plugin-poc.git

I configured jenkins to use kubernetes plugin to configure Jenkins kubernetes<br>
Agents to run the Job. The Jenkins Controller pod is no longer running the job<br>
except for Scheduling it. THe Jobs are run launching the separate pod with
slave containers.

For Jenkins Pipeline, I have used Jenkins Shared Library Approach

I have written implementation.md file to explain the workflow & architecture.

The Student Management System Application was cloned from the following project:
https://github.com/medinar/full-stack-spring-boot-react.git