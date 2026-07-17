def call() {

    sh '''
    cd sms-app
    helm upgrade --install frontend \
        helm/sms-frontend \
        --set image.repository=ashirqayyum/sms-frontend \
        --set image.tag=jenkins

    '''

}