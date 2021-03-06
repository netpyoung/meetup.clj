(ns meetup.core
  (:require [meetup.schema :as schema]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [schema.core :as s]
            [schema-tools.core :as st]
            [environ.core :refer [env]]))

(defn always-true [] (= 42 42))

(defn when-vs-if [] (if 42 42 nil))


(def data-file-fpath "data/events.edn")

;; > (boolean (Boolean/valueOf "TRUE"))
;; TRAVIS_PULL_REQUEST is set to the pull request number if the current job is
;; a
;; pull request build, or false if it’s not.

;; [TRAVIS_BRANCH, TRAVIS_PULL_REQUEST, TRAVIS_PULL_REQUEST_BRANCH]


(defn check-data
  []
  (println "hi" (env :travis-branch))
  (let [scheme (st/merge schema/EventObjectScheme schema/UserMeetupInfo)
        events (->> data-file-fpath
                    io/resource
                    slurp
                    edn/read-string
                    :events)]
    (doseq [event events]
      (let [result (s/check scheme event)]
        (if-not (nil? result) (println event result))))
    (System/exit 0)))

;; TODO(kep): auto generate result.edn
;; TODO(kep): apply nio2  - https://github.com/ajoberstar/ike.cljj
;; (let [matcher (.getPathMatcher
;;                (java.nio.file.FileSystems/getDefault)
;;                "glob:*.{edn}")
;;       events (->> (clojure.java.io/file "../__events")
;;                   file-seq
;;                   (filter #(.isFile %))
;;                   (filter #(.matches matcher (.getFileName (.toPath %))))
;;                   (map slurp)
;;                   (mapv edn/read-string)
;;                   (assoc {} :events))]
;;   (with-open [w (io/writer  "../docs/autogen_result.edn")]
;;     (.write w (str events))))
