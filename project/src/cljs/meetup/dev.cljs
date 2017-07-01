(ns meetup.dev
  (:require [meetup.core :as meetup]))

(defn start [] (enable-console-print!) (meetup/start))
