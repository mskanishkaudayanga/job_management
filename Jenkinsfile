pipeline {
    agent any

    environment {
        DOCKERHUB_CREDS = 'dockerhub-creds'
        IMAGE_NAME = 'kanishkaudayanga/job-queues'
        VERSION = "${env.BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/mskanishkaudayanga/job_management.git',
                credentialsId: 'github-creds'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKERHUB_CREDS) {
                        def app = docker.build("${IMAGE_NAME}:${VERSION}")
                        app.push()
                        app.push("latest")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker pull ${IMAGE_NAME}:latest

                docker stop app || true
                docker rm app || true

                docker run -d -p 8081:8080 --name app ${IMAGE_NAME}:latest
                '''
            }
        }
    }
}