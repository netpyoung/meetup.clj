(ns meetup.core
  (:require
   [meetup.schema :as schema]
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [schema.core :as s]
   [schema-tools.core :as st]
   ))

(def data-file-fpath  "data/events.edn")

(defn check-data []
  (let [scheme (st/merge
                schema/EventObjectScheme
                schema/UserMeetupInfo)
        events (->> data-file-fpath
                    io/resource
                    slurp
                    edn/read-string
                    :events)]
    (doseq [event events]
      (let [result (s/check scheme event)]
        (if-not (nil? result)
          (println event result))))
    (System/exit 0)))
