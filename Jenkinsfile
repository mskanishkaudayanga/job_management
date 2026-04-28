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

//                    stage('Deploy to EC2') {
//                        steps {
//                            bat """
//                            ssh -o StrictHostKeyChecking=no -i C:\\keys\\spring-job.pem ubuntu@54.160.61.4 ^
//                            "docker pull ${IMAGE_NAME}:latest && ^
//                            docker stop app || true && ^
//                            docker rm app || true && ^
//                            docker run -d -p 8080:8080 --name app ${IMAGE_NAME}:latest"
//                            """
//                        }
//                    }
stage('Deploy to EC2') {
    steps {
        bat """
        @echo off
        echo Connecting to EC2...
        ssh -o StrictHostKeyChecking=no -i "C:\\keys\\spring-job.pem" ubuntu@54.160.61.4 "docker pull ${IMAGE_NAME}:latest && docker stop app || docker ps && docker rm app || docker ps && docker run -d -p 8080:8080 --name app ${IMAGE_NAME}:latest"
        """
    }
}

    }
}