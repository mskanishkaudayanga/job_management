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
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}:${VERSION}")
                    docker.build("${IMAGE_NAME}:latest")
                }
            }
        }

//         stage('Docker Login') {
//             steps {
//                 withCredentials([usernamePassword(
//                     credentialsId: DOCKERHUB_CREDS,
//                     usernameVariable: 'USERNAME',
//                     passwordVariable: 'PASSWORD'
//                 )]) {
//                     bat 'echo %PASSWORD% | docker login -u %USERNAME% --password-stdin'
//                 }
//             }
//         }
//
//         stage('Push Image') {
//             steps {
//                 bat """
//                 docker push ${IMAGE_NAME}:${VERSION}
//                 docker push ${IMAGE_NAME}:latest
//                 """
//             }
//         }
            stage('Docker Login & Push') {
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
    }
}