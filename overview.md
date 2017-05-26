meetup.clj overview
===================

# for what
* easy to share event information.
* for fun on clojure!

# Overview
* easy to use, distribute.
    * no server.
* When I create new file which is named of  `2009-09-09_example.edn` and pull request, CI collect information about date associated edn files and make indexing.edn which collected monthly information abount date associated edn.
* CI push that file to `gh-pages`
* User can see rendered page.

# file overview
* project must be reuseable modular.

```tree
[sample project]
|- [meet up project] will be modular, it will be contains sample project
|- user configuration data
|- travis.xml ...


[meet up project]
|- [sample project] git submodule - it can recursive submodule?
|- src
```

## flow uml
* plantuml : http://plantuml.com/

## create edn
* edn file format?
* language config
* file location (need to nested folder for maintain)
* new event info

```edn
{
  :writer "halloman"
  {
    :eng
    {
       :title "GDG Korea WebTech meetup"
       :ur "https://gdg-korea-webtech.firebaseapp.com/events/meetup-20170223/"
       :date ["2017-02-23 19:30:00" "2017-02-23 22:00:00"]
       :address "서울특별시 강남구 논현1동 15-11 Fast Campus 별관 MH bld. 3rd floor"
       :tags: ["google, gdg, gdgkorea"]
     }
   }
}
```

* detail session

```edn
{
  :eng
  {
    :title "what we will do"
    :subtitle "do clojure"
    :date ["2017-02-23 19:30:00" ]
    :presenter "[iceman"]
    :presenter-introduce "blablabalbal"
    :organization ["clojure"]
    :category ["music"]
    :tags ["wtf"]
    :session description "asdkfjakldjf;ajkdfkl;aj"
  }
}
```

## push edn
* at first, manualy use github pull request process.
* preview and create on site.


## ci process
* collect informs and create indexing.edn
* use travis ci. circle ci which used clojure but it has some restriction.
    * circle ci - 1,500 build minutes per month
* what the form of indexing.edn .
* travis ci
    * https://github.com/boot-clj/boot/wiki/Running-Boot-on-CI-systems
    * https://docs.travis-ci.com/user/customizing-the-build
* push gh-pages


## User can see rendered page.
* domain which is easy do access
* google map
* js
    * react.js
    * fullcalendar
    * material-kit
    * hammer.js
* clojure
    * use reagent
    * template : https://github.com/yogthos/Selmer
    * schema
    * https://github.com/reagent-project/reagent-forms
    * https://github.com/madvas/cljs-react-material-ui
    * https://github.com/reagent-project/reagent-utils


## User can write infrom and preview thought on site.
* auth : https://developer.github.com/apps/building-integrations/setting-up-and-registering-oauth-apps/
* Social Buttons for Bootstrap : https://lipis.github.io/bootstrap-social/
* https://developer.github.com/v3/repos/contents/#create-a-file
* how to prevent abusing user?????
    * https://github.com/blog/2146-organizations-can-now-block-abusive-users

## share work process

# reference
* dev-meetup -https://github.com/dev-meetup/dev-meetup.github.io
* dev-meetup  dev log - https://blog.weirdx.io/post/41536
* ndc replay devlog - https://www.slideshare.net/devcatpublications/ndc-replay-static-website-static-backoffice-ndc2017


## considered
* no native app
    * It will be introduce maintain issue.
    * When It will be needed, make seperate project.
* google sheet
    * it will be caused maintain issue.
    * it will be complicated give authorize than github.
* only text support.
    * if user upload file, pull & push process will be slown.
* external resource
    * pptx
        * iSpring
        * ShareSlide **
        * Speaker Deck
    * pdf
        * pdf to image slice??
    * movie
        * mp4
        * webm
        * video.js
