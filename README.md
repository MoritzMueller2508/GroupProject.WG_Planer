# GroupProject.WG_Planer
This project is done with the help of AWS Services.

1. install all services. Run "npm install" in your terminal.
2. be sure that amplify is installed running "amplify -version". If not, then install it "npm install -g @aws-amplify/cli"

check this link for more information https://docs.amplify.aws/start/getting-started/installation?integration=android

3. run "amplify configure" and sign in with an IAM user (not root account)
    aws region: eu-central-1
    username: <your username>
    Do not create any profile as you already have access key. Press enter.
    Enter your access keys
    Specify profile name to be created in your local machine.

here you can get used to the amplify commands https://aws-amplify.github.io/docs/cli-toolchain/quickstart

# Git:

Commit, push and work with the files normally:

        git add file.iso                    this adds the file that you want to push (do git add . to add everything)
        git commit -m "Add disk image"      when committing leave a message of what you are committing
        git push                            also: "git push origin <local-branch>:<remote-branch>"
        git push from one remote branch to another      here u can create in GitHub a "pull request" and solve possible merge conflicts

