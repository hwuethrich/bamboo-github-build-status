# Bamboo build results for Github

I quickly put this plugin together so the result of each build in Bamboo is pushed to Github using their commit status API.

Although I'm not using Bamboo anymore, please feel free to submit pull requests!

**This is my first Bamboo plugin and things might break if you use it (although I used it myself for some time without any problems).**

## Here are the SDK commands you'll use immediately:

* `atlas-run`   -- installs this plugin into the product and starts it on localhost
* `atlas-debug` -- same as atlas-run, but allows a debugger to attach at port 5005
* `atlas-cli`   -- after atlas-run or atlas-debug, opens a Maven command line window:
                 - 'pi' reinstalls the plugin into the running product instance
* `atlas-help`  -- prints description for all commands in the SDK

Full documentation is always available at:

https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK
