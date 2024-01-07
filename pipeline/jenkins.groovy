pipeline {
    agent any
    environment {
        REPO = "https://github.com/rshuvalov/kbot"
        BRANCH = "main"
        REGISTRY = "rshuvalov"
    }
    parameters {
        choice(name: "OS", choices: ["linux", "darwin", "windows", "all"], description: "Pick OS")
        choice(name: "ARCH", choices: ["amd64", "arm64", "all"], description: "Pick ARCH")
    }
    stages {
        stage("Env") {
            steps {
                echo "Build for platform ${params.OS}"
                echo "Build for arch: ${params.ARCH}"
            }
        }
        stage("clone") {
            steps {
                echo "Cloning ${REPO} ${BRANCH}"
                git branch: "${BRANCH}", url: "${REPO}"
            }
        }
        stage("test") {
            steps {
                echo "Testing"
                sh "make test"
            }
        }
        stage("build") {
            steps {
                echo "Building"
                sh "make build"
            }
        }
        stage("image") {
            steps {
                echo "Building image"
                sh "make image"
            }
        }
        stage("push") {
            steps {
                echo "Pushing image"
                script {
                    docker.withRegistry("", "dockerhub") {
                        sh "make push"
                    }
                }
            }
        }
    }
}
