def call() {

    sh '''
    cd sms-app
    docker build \
        -t ashirqayyum/sms-frontend:jenkins \
        src/frontend

    '''

}