def call() {

    sh '''
    cd sms-app
    helm upgrade --install postgres \
        helm/sms-postgres

    '''

}