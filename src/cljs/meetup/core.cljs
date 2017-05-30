(ns meetup.core
  (:require
   [meetup.pages.core :as pages]
   [bidi.bidi :as bidi]
   [accountant.core :as accountant]
   [reagent.core :as reagent]
   [reagent.session :as session]

   ;; [cljs.reader :as reader]
   ;; [dommy.core :as dommy]
   ;; [bidi.schema]
   ;; [schema.core :as schema :include-macros true]
   ;; [cognitect.transit :as transit]
   ))


(defn on-js-reload
  []
  (->> (. js/document (getElementById "app"))
       (reagent/render-component [pages/page])))

(defn ^:export start
  []
  (accountant/configure-navigation!
   {:nav-handler
    (fn
      [path]
      (let [match (bidi/match-route pages/app-routes path)
            ;; current-page (str "/meetup.clj/" (:handler match))
            current-page (:handler match)
            route-params (:route-params match)]
        (session/put! :route {:current-page current-page
                              :route-params route-params})))
    :path-exists?
    (fn [path]
      (boolean (bidi/match-route pages/app-routes path)))})

  (accountant/dispatch-current!)

  (on-js-reload))
