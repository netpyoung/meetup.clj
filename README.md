meetup.clj
==========

[![Build Status](https://travis-ci.org/netpyoung/meetup.clj.svg?branch=master)](https://travis-ci.org/netpyoung/meetup.clj)

# meeup.clj

I want to website for share idea, meetup date or something.

It inspired of [dev-meetup](https://dev-meetup.github.io/). But It is written in [Clojure](https://clojure.org/).


We’ll find a way. We always have. -Cooper #Interstellar


# dev

```
$ rake -T
rake check_data  # validate data file
rake codeformat  # codeformat
rake dev         # develop mode
rake dist        # generate github-pages

boot.user=> (start-repl)
```


# flow
![flow](@document/flow.png)

# tree

```
|- @document : document
|- docs : github-pages
|- project : meetup project directory
|- script : unused.
|- README.md
|- Rakefile
```

# TODO
- [ ] nio2
  - https://github.com/ajoberstar/ike.cljj
- [ ] add log
  - https://github.com/ptaoussanis/timbre
  - https://github.com/philoskim/debux - we can contribute that add trimbre appender

- [ ] add file associated npm
  - https://docs.npmjs.com/cli/shrinkwrap
  - https://github.com/github/gitignore/blob/master/Node.gitignore

- [ ] spec is better than schema? it can derive a good code style?
  - https://github.com/plumatic/schema#schemas-in-practice
  - https://github.com/athos/spectrace
- [ ] pull request && ci process
  - [ ] [TRAVIS_BRANCH, TRAVIS_PULL_REQUEST, TRAVIS_PULL_REQUEST_BRANCH](https://docs.travis-ci.com/user/environment-variables/)
  - [ ] https://stackoverflow.com/questions/23277391/how-to-publish-to-github-pages-from-travis-ci
- [ ] presentation page
- [ ] input page
  - [ ] https://github.com/reagent-project/reagent-cookbook/tree/master/recipes/input-validation
  - [ ] https://github.com/reagent-project/reagent-cookbook/tree/master/recipes/markdown-editor
  - [ ] https://github.com/github-tools/github
- [ ] gzip scripts
- [ ] [meetup api](https://www.meetup.com/meetup_api/auth/)
- [ ] [export google calender](https://developers.google.com/google-apps/calendar/v3/reference/events/import)
- [ ] search / filter
- [ ] [JVM opts](https://dev.clojure.org/display/design/Improving+Clojure+Start+Time)
- [ ] add something good?
  - https://github.com/react-bootstrap/react-overlays

# ref
* http://d2.naver.com/helloworld/303083
