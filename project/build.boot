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
  '[:meetup]))

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
 '[boot-fmt.core :refer [fmt]]
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
   (target :dir #{"../docs"})))


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

(deftask codeformat []
  (set-env!
   :source-paths   #{"src/clj" "src/cljc" "src/cljs" "config/prod"}
   :resource-paths #{"resources"}
   )
  (comp
   (fmt
    ;; https://github.com/kkinnear/zprint
    ;; :mode :diff
    :mode :overwrite
    :really true
    :source true
    :options {:style :community
              :binding {:justify? true}
              :map {:justify? true
                    :comma? false}
              })
   ))
