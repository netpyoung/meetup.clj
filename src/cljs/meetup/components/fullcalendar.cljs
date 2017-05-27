(ns meetup.components.fullcalendar
  (:require
   [reagent.core :as reagent]))

(defn component
  [opt]

  (let [options (reagent/atom {})]

    (reagent/create-class
     {:component-did-mount
      (fn [this]
        (-> (reagent/dom-node this)
            (js/$)
            (.fullCalendar (clj->js @options))))

      :component-did-update
      (fn [this]
        (-> (reagent/dom-node this)
            (js/$)
            (.fullCalendar (clj->js @options))))

      :reagent-render
      (fn [x]
        (reset! options x)
        [:div])})))
