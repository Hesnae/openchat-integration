pipeline {
    agent {
        label 'master'
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build bootJar'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t chatgptapi .'
            }
        }
        stage('Run chatGpt service') {
            steps {
                sh 'docker run -it -p 8085:8085 chatgptapi:latest'
            }
        }
    }
}
