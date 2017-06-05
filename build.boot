(def project 'netpyoung/meetup)
(def version "0.1.0-SNAPSHOT")

;; https://github.com/borkdude/boot-bundle
(set-env! :dependencies '[[boot-bundle "0.1.0-SNAPSHOT" :scope "test"]])
(require '[boot-bundle :refer [expand-keywords]])


(set-env!
 :source-paths   #{"src/clj" "src/cljc"}
 :resource-paths #{"resources"}

 :dependencies
 (expand-keywords
  '[
    ;; dep
    [org.clojure/clojure "1.8.0"]
    [org.clojure/clojurescript "1.9.542"]

    ;; http://cljsjs.github.io/
    [cljsjs/material-ui "0.18.1-0"]
    [cljsjs/hammer "2.0.4-5"]
    [cljsjs/moment "2.17.1-1"]
    [cljsjs/react-bootstrap "0.30.7-0"]
    [cljsjs/google-maps "3.18-1"]

    ;; reagent
    [reagent "0.6.2"
     :exclusions [cljsjs/react cljsjs/react-dom org.clojure/tools.reader]]
    [reagent-utils "0.2.1"]

    ;; route
    [bidi "2.1.1"]
    [venantius/accountant "0.2.0"]


    ;; jquery
    [jayq "2.5.4"]

    ;; ajax - https://github.com/JulianBirch/cljs-ajax
    [cljs-ajax "0.6.0"]

    ;; json
    [com.cognitect/transit-cljs "0.8.239"]

    [prismatic/dommy "1.1.0"]
    [prismatic/schema "1.1.6"]
    [metosin/schema-tools "0.9.0"]



    ;; [stencil "0.5.0" :exclusions [org.clojure/clojure]]
    [adzerk/bootlaces "0.1.13" :scope "test"]
    [adzerk/boot-test "1.2.0" :scope "test"]

    ;; for test
    [adzerk/boot-cljs          "1.7.228-2"  :scope "test"]
    [adzerk/boot-cljs-repl     "0.3.3"      :scope "test"]
    [adzerk/boot-reload        "0.4.13"     :scope "test"]
    [pandeiro/boot-http        "0.7.6"      :scope "test"]

    [com.cemerick/piggieback "0.2.1"  :scope "test"]
    [weasel                  "0.7.0"  :scope "test"]
    [org.clojure/tools.nrepl "0.2.12" :scope "test"]

    ;; https://github.com/Raynes/conch
    [me.raynes/conch "0.8.0"]

    ;; https://github.com/weavejester/environ
    [environ "1.1.0"]
    [boot-environ "1.1.0"]

    ;; https://github.com/tolitius/boot-check
    [tolitius/boot-check "0.1.4"]
    ]
  ))

(task-options!
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/netpyoung/meetup.clj"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})
(task-options!
 push {:repo "clojars"
       :ensure-clean false
       :ensure-branch nil
       :ensure-tag nil})


(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[adzerk.bootlaces      :refer :all]
 '[pandeiro.boot-http    :refer [serve]]
 '[adzerk.bootlaces :refer [bootlaces! build-jar push-snapshot push-release]]
 '[environ.boot :refer [environ]]
 '[environ.core :refer [env]]
 '[tolitius.boot-check :as check]
 )


(bootlaces! version :dont-modify-paths? true)

(deftask dev []
  (set-env!
   :source-paths   #{"src/clj" "src/cljc" "src/cljs" "config/dev"}
   :resource-paths #{"resources"}
   )

  (comp

   (serve :port 8080)
   (watch)
   (cljs-repl)
   ;; (reload :on-jsload 'meetup.core/start)
   (reload :on-jsload 'meetup.dev/start)
   (speak)
   (cljs :optimizations :none :source-map true)))


;; (deftask build
;;   "Build and install the project locally."
;;   []
;;   (comp (pom) (jar) (install)))

(deftask prod
  []
  (set-env!
   :source-paths   #{"src/clj" "src/cljc" "src/cljs" "config/prod"}
   :resource-paths #{"resources"}
   )
  (comp
   ;; https://github.com/boot-clj/boot-cljs/tree/master/docs
   (cljs :optimizations :advanced :source-map false)

   ;; https://github.com/boot-clj/boot/blob/master/doc/boot.task.built-in.md#sift
   (sift
    :include
    #{#"(^index\.html|^js\/core\.js|^data\/*|^images\/*|^style\/*|404\.md|_config\.yml)"})

   ;; https://github.com/boot-clj/boot/blob/master/doc/boot.task.built-in.md#target
   (target :dir #{"docs"})))


(deftask check-data []
  (comp
   (environ :env {:travis-branch "request-branch"})
   (check/with-eastwood)
   (check/with-kibit)
   (check/with-bikeshed)
   (with-pre-wrap fileset
     (println (env :travis-branch))
     (require '[meetup.core :as meetup])
     (apply (resolve 'meetup/check-data) [])
     fileset)
   ))
