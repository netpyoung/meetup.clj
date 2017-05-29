(ns meetup.core
  (:require
   [meetup.components.fullcalendar :as fullcalendar]
   [cljsjs.material-ui]
   [cljsjs.material-ui-svg-icons]
   [cljsjs.moment]
   [cljsjs.react-bootstrap]

   [cljs.reader :as reader]

   [dommy.core :as dommy]

   [ajax.core :as ajax]

   [schema.core :as schema :include-macros true]
   [bidi.bidi :as bidi]
   ;; [bidi.schema]
   [accountant.core :as accountant]
   [reagent.core :as reagent]
   [reagent.session :as session]

   [cognitect.transit :as transit]
   ))

(enable-console-print!)

(def json-reader (transit/reader :json))


(def app-routes
  ["/"
   [["" :index]
    ["list" :list]
    ["chat" :chat]
    [true :four-o-four]]])

;; http://materializecss.com/icons.html
;; http://htmltohiccup.herokuapp.com/


;; https://fullcalendar.io/docs/event_data/Event_Object/
(defn month-calendar []
  [fullcalendar/component
   {
    :header {
             :left ""
             :center "title"
             :right "today prev,next"
             }
    :views {}
    :locale "en"
    :defaultView "month"
    :defaultDate (.format (js/moment))
    :timeFormat "H:mm"
    :navLinks true
    :editable false
    :eventLimit true
    :events
    (fn [start, end, timezone, callback]
      (ajax/GET "/data/events.json"
                {
                 :response-format
                 (ajax/json-response-format {:keywords? true})

                 :handler
                 (fn [resp]
                   (let [events (:events resp)]
                     ;; (=
                     ;;  (-> "2017-03-23 19:30:00"
                     ;;      (js/moment "YYYY-MM-DD HH:mm:ss")
                     ;;      (.month ))
                     ;;  2)

                     (callback (clj->js events))
                     )
                   )
                 })
      )
    :eventClick
    (fn [calEvent, jsEvent, view]
      (js/alert "hi")
      false)
    }])

(defn list-calendar []
  [fullcalendar/component
   {
    :header {
             :left ""
             :center "title"
             :right "today prev,next"
             }
    :views {}
    :locale "en"
    :defaultView "listMonth"
    :defaultDate (.format (js/moment))
    :timeFormat "H:mm"
    :navLinks true
    :editable false
    :eventLimit true
    :events
    (fn [start, end, timezone, callback]
      (ajax/GET "/data/events.json"
                {
                 :response-format
                 (ajax/json-response-format {:keywords? true})

                 :handler
                 (fn [resp]
                   (let [events (:events resp)]
                     ;; (=
                     ;;  (-> "2017-03-23 19:30:00"
                     ;;      (js/moment "YYYY-MM-DD HH:mm:ss")
                     ;;      (.month ))
                     ;;  2)

                     (callback (clj->js events))
                     )
                   )
                 })
      )
    #_(fn [start, end, timezone, callback]
        (ajax/GET "/test.edn" {:handler
                               (fn [resp]
                                 (let [x (reader/read-string resp)]
                                   (println x)
                                   (callback (clj->js [(:event x)]))
                                   ))
                               })
        )
    :eventClick
    (fn [calEvent, jsEvent, view]
      (js/alert "hi")
      false)
    }])

;; [:i.large.material-icons "add"]




;; (schema/check bidi.schema/RoutePair app-routes)
;; (schema/validate bidi.schema/RoutePair app-routes)



(defmulti page-contents identity)

(defmethod page-contents :index []
  [:span
   [month-calendar]])

(defmethod page-contents :list []
  [:span
   [list-calendar]])

(defmethod page-contents :chat []
  [:span
   [:iframe {:src "https://gitter.im/clojure_tokyo/Lobby/~embed"
             :style {:height (.-innerHeight js/window)}}]])

(defmethod page-contents :four-o-four []
  "Non-existing routes go here"
  [:span
   [:h1 "404: It dfadsis not here"]
   [:pre.verse
    "What you areng for, I do not have. How could I have, what does not exist?"]])


(defmethod page-contents :default []
  "Configured routes, missing an implementation, go here"
  [:span
   [:h1 "404: My basdfad"]
   [:pre.verse
    "This page should be here, but I never created it."]])


(def Navbar (reagent/adapt-react-class (aget js/ReactBootstrap "Navbar")))
(def Navbar-Header (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Header")))
(def Navbar-Brand (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Brand")))
(def Navbar-Toggle (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Toggle")))
(def Navbar-Collapse (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Collapse")))
(def Nav (reagent/adapt-react-class (aget js/ReactBootstrap "Nav")))
(def NavItem (reagent/adapt-react-class (aget js/ReactBootstrap "NavItem")))

(defn navigation [page]
  [Navbar
   [Navbar-Header
    [Navbar-Brand
     [:a {:href (bidi/path-for app-routes :index)} [:i.fa.fa-home {:aria-hidden true}]]]
    [Navbar-Toggle]]

   [Navbar-Collapse
    [Nav {:pullRight true :activeKey page}
     [NavItem {:eventKey :index :href (bidi/path-for app-routes :index)}
      [:i.fa.fa-calendar {:aria-hidden true}] "CALENDAR"]
     [NavItem {:eventKey :list :href (bidi/path-for app-routes :list)}
      [:i.fa.fa-list {:aria-hidden true}] "LIST"]
     [NavItem {:eventKey :chat :href (bidi/path-for app-routes :chat)}
      [:i.fa.fa-comments {:aria-hidden true}] "CHAT"]
     ]]])

(defn page
  []
  (let [page (:current-page (session/get :route))]
    [:div
     [navigation page]

     ^{:key page}
     [:div.container [page-contents page]]]))


(defn on-js-reload
  []
  (->> (. js/document (getElementById "app"))
       (reagent/render-component [page])))


(defn ^:export start
  []
  (accountant/configure-navigation!
   {:nav-handler
    (fn
      [path]
      (let [match (bidi/match-route app-routes path)
            current-page (:handler match)
            route-params (:route-params match)]
        (session/put! :route {:current-page current-page
                              :route-params route-params})))
    :path-exists?
    (fn [path]
      (boolean (bidi/match-route app-routes path)))})

  (accountant/dispatch-current!)

  (on-js-reload))
