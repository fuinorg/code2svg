pipeline {
    agent any 
    tools { 
        maven 'Maven (latest)' 
        jdk 'Oracle JDK 1.8 (latest)'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh "mvn -version"
                sh "java -version"
                sh "sudo /opt/jenkins/sbin/mount-webdav https://repository-fuin-org.forge.cloudbees.com/private fuin-org alert"
            }
        }
        stage('Build') { 
            steps {
                sh "mvn clean deploy -U -B -P sonatype-oss-release -s /private/fuin-org/settings.xml" 
            }
        }
    }
}
