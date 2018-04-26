## Instructions for pull requests

### First time only
1. Fork the target repo to your own account.
2. Clone the repo to your local machine.

### For each pull requests
1. For the task/issue that you're resolving, first create an issue in https://github.com/dynamite8/resources-android-dev/issues
2. Check out a new "topic branch" and make changes.

  Format: `git checkout -b <initial>_WIT-<issue #>_<short desc>`

  Example: `git checkout -b jc_WIT-3_readme`

3. Push your topic branch to your fork.
4. Commit your changes to your  branch.

  Format: `git commit -m "Fixes #<issue number>: <description>"`

  Example: `git commit -m "Fixes #3: Updated readme file"`

5. Use the diff viewer on GitHub to create a pull request via a discussion.
6. Make any requested changes.
7. The pull request is then merged (usually into the master branch) and the topic branch is deleted from the upstream (target) repo.

### Steps to rebasing

You'll need to rebase before working or creating a PR.

1. Checkout the master branch `git checkout master`
2. Grab the updated changes from repository `git fetch upstream`
3. Merge in updated latest changes to local master branch `git merge upstream/master`, changes should be fast-forwarded
4. Checkout your working branch `git checkout <branch name>`
5. Pull in changes from master to your branch `git rebase master`
6. Upload your changes to the repo `git push -f`
