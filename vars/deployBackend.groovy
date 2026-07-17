def call() {

    sh '''
    cd sms-app
    helm upgrade --install backend \
        helm/sms-backend \
        --set image.repository=ashirqayyum/sms-backend \
        --set image.tag=jenkins

    '''

}