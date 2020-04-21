# GroupProject.WG_Planer

This project is done with the help of AWS Services:
* amazon cognito
* amazon lambda
* AWS appSync (APIs - GraphQL)
* AWS Amplify
* DynamoDB
* CloudFormation
* S3

Please be sure to set Frankfurt (eu-central-1) as your location in the
AWS console.

# Aws

1. install all services. Run this command in your terminal.

   ```
   npm install
   ````
2. be sure that amplify is installed running "amplify -version". If not,
   then install it

   ```
   npm install -g @aws-amplify/cli
   ```

check this link for more information
https://docs.amplify.aws/start/getting-started/installation?integration=android

3. run "amplify configure" and sign in with an IAM user (not root
   account)

   ```
   amplify configure
   ```

   * aws region: eu-central-1
   * username: <your username> as provided to you
   * Do not create any profile as you already have access keys! Press
     enter
   * Enter your access keys Specify profile name to be created in your
     local machine (you can write the same as in your username)

4. go to the development branch and run amplify

   ```
   git checkout development
   amplify init
   ```

   * choose your preferred IDE
   * type of app we're building: android
   * Res directory: press enter to accept default
   * Use an AWS existing environment!
   * Environment to choose: dev
     * Here you are ready to add/update any backend configuration using
       amplify add/update <category>. When you are ready to push do the
       following:

   ```
   amplify push
   git push origin development
   ```

   or if you haven't linked your local branch to the remote one run this
   instead

   ```
   git push -u origin development
   ```


To pull changes follow the same steps as in 4. but write "pull" instead
of "push".


here you can get used to the amplify commands
https://aws-amplify.github.io/docs/cli-toolchain/quickstart


# Git

Commit, push and work with the files normally:

* To add files to be committed (that you want to push) write the
  following command in git bash. You should write all the files that you
  want to push. You can also add all files (second command) and only the
  files with changes will be updated. Don't forget to pull before trying
  to push.

```
git add file.iso
git add .
```

* Then commit. Don't forget to add a message of what changes you are
  pushing.

```
git commit -m "your message"
```

* To push run following commands

```
git push origin <local-branch>:<remote-branch>
```

* To push from one branch to another i.e. your personal to development,
it is easier to create a "pull request" in GitHub and solve possible
merge conflicts.

It is also possible to add, commit, push and pull
within the Android Studio IDE under VCS -> git
