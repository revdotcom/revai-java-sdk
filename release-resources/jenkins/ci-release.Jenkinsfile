def COLOR_MAP = ['SUCCESS': 'good', 'FAILURE': 'danger', 'UNSTABLE': 'danger', 'ABORTED': 'danger']

pipeline {
    agent {
        node { label 'linux-ci' }
    }
    environment {
        PGP_PASSPHRASE = credentials('revai-java-sdk/maven-pgp-signing/passphrase')
        PGP_SECRETKEY = credentials('revai-java-sdk/maven-pgp-signing/private-key')
    }
    stages {
        stage('Sign artifacts and publish') {
            steps {
                ansiColor('xterm') {
                    sh """
                    echo "hello ${PGP_PASSPHRASE} hello"
                    
                    """
                }
            }
        }
    }
}

def changeOwner() {
    ansiColor('xterm') {
        sh """
        
        """
    }
}

def getSlackMessage() {
    return """
${env.JOB_NAME} - ${env.BUILD_NUMBER} ${currentBuild.currentResult} after ${currentBuild.durationString}
"""
}