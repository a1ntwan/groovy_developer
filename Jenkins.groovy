import groovy.transform.Field

@Field
ArrayList noTests = ["hw05-orm-gradle"]
ArrayList moduleToBranch = [
        ['hw01-gradle': 'homework-1'],
        ['hw02-gradle': 'homework-2'],
        ['hw03-gradle': 'homework-3'],
        ['hw04-json-gradle': 'homework-4'],
        ['hw05-orm-gradle': 'homework-5'],
        ['hw08-dsl-gradle': 'homework-8']
]


def PrintSysTime(){
    sh(script: "echo CURRENT BRANCH IS: \$(basename \$(git symbolic-ref HEAD)) AND LOCAL TIME IS: \$(date +%H:%M:%S)", returnStdout: true)
}

def CalculateFilesCount() {
    sh(script: "echo TOTAL NUMER OF FILES IS: \$(find . -type f 2>> /dev/null | wc -l)", returnStdout: true)
}

def checkout(name) {
    echo "Performing Git checkout"

    git branch: name, credentialsId: 'github', url: 'git@github.com:a1ntwan/groovy_developer.git'

    if (name == 'homework-4') {
        dir('bom-examples') {
            git branch: 'master', credentialsId: 'github', url: 'git@github.com:CycloneDX/bom-examples.git'
        }
    }
}

def testModule(name) {
    echo "Performing tests"

    if (!noTests.contains(name)) {
        sh(script: "./gradlew -p ${name} test", returnStdout: true)
    } else {
        echo "Testing is not supported for module ${name}!"
    }
}

def jibBuild(name) {
    echo "Performing dockerization and pushing to Dockerhub"

    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        sh(script: "./gradlew -p ${name} jib -Djib.to.auth.username=${USERNAME} -Djib.to.auth.password=${PASSWORD}", returnStdout: true)
    }
}

properties([
        parameters([
                extendedChoice(name: 'MODULES', description: 'Choose your branch', multiSelectDelimiter: ',', type: 'PT_CHECKBOX', value: 'hw01-gradle,hw02-gradle,hw03-gradle,hw04-json-gradle,hw05-orm-gradle,hw08-dsl-gradle', visibleItemCount: 6)
        ])
])

node {
    stage("CLEAN WORKSPACE") {
        cleanWs()
    }

    stage("CREATE DIRS FOR MODULES") {
        params['MODULES'].split(',').each { m ->
            String path = moduleToBranch.findResult { it[m] }
            new File("${WORKSPACE}/${path}").mkdirs()
        }
        sh "ls ${WORKSPACE}"
    }

    stage("PARELLEL GIT CHECKOUT") {
        def branchedStages = [:]

        params['MODULES'].split(',').each { m ->
            branchedStages[m] = {
                stage("PARALLEL GIT CHECKOUT: ${m}") {
                    String path = moduleToBranch.findResult { it[m] }
                    dir("${WORKSPACE}/${path}") {
                        checkout(path)
                    }
                }
            }
        }
        parallel branchedStages
    }

    stage("PARELLEL TEST") {
        def branchedStages = [:]

        params['MODULES'].split(',').each { m ->
            branchedStages[m] = {
                stage("PARALLEL TEST: ${m}") {
                    String path = moduleToBranch.findResult { it[m] }
                    dir("${WORKSPACE}/${path}") {
                        testModule(m)
                    }
                }
            }
        }
        parallel branchedStages
    }

    stage("PARELLEL BUILD AND PUSH") {
        def branchedStages = [:]

        params['MODULES'].split(',').each { m ->
            branchedStages[m] = {
                stage("PARELLEL BUILD AND PUSH: ${m}") {
                    String path = moduleToBranch.findResult { it[m] }
                    dir("${WORKSPACE}/${path}") {
                        PrintSysTime()
                        jibBuild(m)
                        CalculateFilesCount()
                    }
                }
            }
        }
        parallel branchedStages
    }
}
