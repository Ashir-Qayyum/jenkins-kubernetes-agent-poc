@Library('sms-library') _

pipeline {

    agent {

        kubernetes {

            yaml '''

apiVersion: v1
kind: Pod

spec:

  serviceAccountName: jenkins

  volumes:

    - name: docker-socket
      emptyDir: {}

    - name: docker-data
      emptyDir: {}

  containers:

    - name: docker

      image: docker:29-cli

      command:
        - cat

      tty: true

      env:
        - name: DOCKER_HOST
          value: unix:///var/run/docker.sock

      volumeMounts:

        - name: docker-socket
          mountPath: /var/run

    - name: dind

      image: docker:29-dind

      securityContext:
        privileged: true

      env:
        - name: DOCKER_TLS_CERTDIR
          value: ""

      volumeMounts:

        - name: docker-socket
          mountPath: /var/run

        - name: docker-data
          mountPath: /var/lib/docker

    - name: helm

      image: alpine/helm:3.18.5

      command:
        - cat

      tty: true

'''
        }

    }

    environment {

        DOCKER_USERNAME = "ashirqayyum"

        FRONTEND_IMAGE = "ashirqayyum/sms-frontend"

        BACKEND_IMAGE = "ashirqayyum/sms-backend"

        IMAGE_TAG = "jenkins"

    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Docker Login') {
            steps {
                container('docker') {
                    dockerLogin()
                }
            }
        }

        stage('Build Backend') {
            steps {
                container('docker') {
                    buildBackend()
                }
            }
        }

        stage('Build Frontend') {
            steps {
                container('docker') {
                    buildFrontend()
                }
            }
        }

        stage('Push Backend') {
            steps {
                container('docker') {
                    pushBackend()
                }
            }
        }

        stage('Push Frontend') {
            steps {
                container('docker') {
                    pushFrontend()
                }
            }
        }

        stage('Deploy PostgreSQL') {
            steps {
                container('helm') {
                    deployPostgres()
                }
            }
        }

        stage('Deploy Backend') {
            steps {
                container('helm') {
                    deployBackend()
                }
            }
        }

        stage('Deploy Frontend') {
            steps {
                container('helm') {
                    deployFrontend()
                }
            }
        }

    }

}