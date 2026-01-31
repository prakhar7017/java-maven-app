@Library('jenkins-shared-library')
def gv
pipeline {
    agent any
    tools {
        maven  'maven'
    }
//     parameters {
//         choice(name: 'VERSION', choices: ['1.1.0','1.2.0','1.3.0'], description: 'version to deploy')
//         booleanParam(name: 'executeTests', defaultValue: true, description: 'Run tests?')
//    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    buildImage()
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    deployApp()
                }
            }
        }
    }
}