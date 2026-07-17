# using jenkins official base Image JUST modifying it to install other tools supporting pipeline
FROM jenkins/jenkins:lts

USER root

# Updatig packages (The jenkins:lts uses debian OS) ALSO Installing basic utils
RUN apt-get update && \
    apt-get install -y \
        curl \
        wget \
        git \
        unzip \
        gnupg \
        ca-certificates \
        apt-transport-https \
        lsb-release

# Installing docker CLI for debian..
RUN install -m 0755 -d /etc/apt/keyrings && \
curl -fsSL https://download.docker.com/linux/debian/gpg | \
gpg --dearmor -o /etc/apt/keyrings/docker.gpg

RUN chmod a+r /etc/apt/keyrings/docker.gpg

RUN echo \
"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
https://download.docker.com/linux/debian \
$(. /etc/os-release && echo $VERSION_CODENAME) stable" \
> /etc/apt/sources.list.d/docker.list

RUN apt-get update && \
apt-get install -y docker-ce-cli


# Installing Kubectl..
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"

RUN install -m 0755 kubectl /usr/local/bin/kubectl

RUN rm kubectl


# Installing helm..
RUN curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash


# Cleaing up..
RUN apt-get clean

USER jenkins