def call() {

    withCredentials([
        usernamePassword(
            credentialsId: 'dockerhub-creds',
            usernameVariable: 'USERNAME',
            passwordVariable: 'PASSWORD'
        )
    ]) {

        sh '''
        echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin
        '''

    }

}