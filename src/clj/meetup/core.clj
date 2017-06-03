(ns meetup.core
  (:require
   [meetup.schema :as schema]
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   ))

(def data-file-fpath  "data/events.edn")

(defn check-data []
  (->> data-file-fpath
       io/resource
       slurp
       edn/read-string
       println))
