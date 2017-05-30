(ns meetup.components.gitter
  (:require
   [jayq.core :as jayq]
   [goog.object]
   [reagent.core :as reagent]))

(defn component
  [src]
  (reagent/create-class
   {
    :reagent-render
    (fn [src]
      [:iframe {:src src :style {:height (.-innerHeight js/window)}}])
    }))
